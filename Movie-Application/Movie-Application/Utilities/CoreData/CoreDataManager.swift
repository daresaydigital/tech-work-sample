//
//  CoreDataManager.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/27/22.
//

import CoreData
import Foundation
import UIKit

class CoreDataManager: CoreDataManagerProtocol {
    
    func saveNewMovie(_ movie: CoreDataMovie) {
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else { return }
        
        let managedContext = appDelegate.persistentContainer.viewContext
        
        let entity = NSEntityDescription.entity(forEntityName: "FavoriteMovie", in: managedContext)!
        
        let favoriteMovie = NSManagedObject(entity: entity, insertInto: managedContext)
        
        favoriteMovie.setValue(movie.title, forKey: "title")
        favoriteMovie.setValue(movie.poster, forKey: "poster")
        favoriteMovie.setValue(movie.id, forKey: "id")
        
        do {
            try managedContext.save()
//            watchListMovies.append(favoriteMovie)
        } catch let error as NSError {
            print("Could not save. \(error), \(error.userInfo)")
        }
    }
    
    func getSavedMovies() -> [CoreDataMovie] {
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return []
        }
        var movies = [CoreDataMovie]()
        let managedContext = appDelegate.persistentContainer.viewContext
        
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "FavoriteMovie")
        
        do {
            let objects = try managedContext.fetch(fetchRequest)
            for object in objects {
                movies.append(CoreDataMovie(title: object.value(forKey: "title") as! String, poster: object.value(forKey: "poster") as! Data, id: object.value(forKey: "id") as! Int))
            }
        } catch let error as NSError {
            print("Could not fetch. \(error), \(error.userInfo)")
        }
        return movies
    }
    
    func saveMovies(movies: [CoreDataMovie]) {
        deleteMovies()
        for movie in movies {
            saveNewMovie(movie)
        }
    }
    
    private func deleteMovies() {
        guard let appDelegate =
                UIApplication.shared.delegate as? AppDelegate else { return }
        let context =
        appDelegate.persistentContainer.viewContext
//        var objects = [NSManagedObject]()
//
//        for movie in movies {
//            let object = NSManagedObject()
//            object.setValue(movie.title, forKey: "title")
//            object.setValue(movie.poster, forKey: "poster")
//            object.setValue(movie.id, forKey: "id")
//            objects.append(object)
//        }
//
//        for object in objects {
//            context.delete(object)
//        }
//
//        do {
//            try context.save()
//        } catch let error {
//            print("could not delete items \(error.localizedDescription)")
//        }
//
        let fetchRequest: NSFetchRequest<NSFetchRequestResult>
        fetchRequest = NSFetchRequest(entityName: "FavoriteMovie")

        // Create a batch delete request for the
        // fetch request
        let deleteRequest = NSBatchDeleteRequest(
            fetchRequest: fetchRequest
        )
        deleteRequest.resultType = .resultTypeObjectIDs

        do {
            let batchDelete = try context.execute(deleteRequest)
                as? NSBatchDeleteResult

            guard let deleteResult = batchDelete?.result
                as? [NSManagedObjectID]
                else { return }
            let deletedObjects: [AnyHashable: Any] = [
                NSDeletedObjectsKey: deleteResult
            ]

            NSManagedObjectContext.mergeChanges(
                fromRemoteContextSave: deletedObjects,
                into: [context]
            )

        } catch let error as NSError {
            print("Could not delete. \(error), \(error.userInfo)")
        }
        
    }
    
    
}
