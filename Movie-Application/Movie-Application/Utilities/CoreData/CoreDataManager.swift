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

        // prevents core data from saving repititious data
        let savedMovies = getSavedMovies()
        if savedMovies.contains(where: {$0.id == movie.id}) {
            return
        }

        let managedContext = appDelegate.persistentContainer.viewContext

        let entity = NSEntityDescription.entity(forEntityName: "FavoriteMovie", in: managedContext)!

        let favoriteMovie = NSManagedObject(entity: entity, insertInto: managedContext)

        favoriteMovie.setValue(movie.title, forKey: "title")
        favoriteMovie.setValue(movie.poster, forKey: "poster")
        favoriteMovie.setValue(movie.id, forKey: "id")
        favoriteMovie.setValue(movie.voteAverage, forKey: "voteAverage")

        do {
            try managedContext.save()
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
                movies.append(CoreDataMovie(title: object.value(forKey: "title") as? String ?? "",
                                            poster: object.value(forKey: "poster") as? Data ?? Data(),
                                            id: object.value(forKey: "id") as? Int ?? 0,
                                            date: Date.now,
                                            voteAverage: object.value(forKey: "voteAverage") as? Double ?? 0))
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
