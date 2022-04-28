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
    
    var watchListMovies: [NSManagedObject] = []
    
    func saveNewMovie(_ movie: Movie) {
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else { return }
        
        let managedContext = appDelegate.persistentContainer.viewContext
        
        let entity = NSEntityDescription.entity(forEntityName: "FavoriteMovie", in: managedContext)!
        
        let favoriteMovie = NSManagedObject(entity: entity, insertInto: managedContext)
        
        favoriteMovie.setValue(movie.title, forKeyPath: "title")
        favoriteMovie.setValue(movie.poster, forKey: "poster")
        favoriteMovie.setValue(movie.id, forKey: "id")
        
        do {
            try managedContext.save()
            watchListMovies.append(favoriteMovie)
        } catch let error as NSError {
            print("Could not save. \(error), \(error.userInfo)")
        }
    }
    
    func getSavedMovies() -> [Movie] {
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return []
        }
        var movies = [Movie]()
        let managedContext = appDelegate.persistentContainer.viewContext
        
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "FavoriteMovie")
        
        do {
            let objects = try managedContext.fetch(fetchRequest)
            for object in objects {
                movies.append(Movie(title: object.value(forKey: "title") as! String, poster: object.value(forKey: "poster") as? String, id: object.value(forKey: "id") as! Int))
            }
        } catch let error as NSError {
            print("Could not fetch. \(error), \(error.userInfo)")
        }
        return movies
    }
    
    func deleteMovie(_ movie: Movie) {
        guard let appDelegate =
                UIApplication.shared.delegate as? AppDelegate else { return }
        
        let managedContext =
        appDelegate.persistentContainer.viewContext
        let entity =
        NSEntityDescription.entity(forEntityName: "FavoriteMovie", in: managedContext)!
        let deletedMovie = NSManagedObject(entity: entity, insertInto: managedContext)
        
        deletedMovie.setValue(movie.title, forKeyPath: "title")
        deletedMovie.setValue(movie.poster, forKey: "poster")
        deletedMovie.setValue(movie.id, forKey: "id")
        managedContext.delete(deletedMovie)
        
        do {
            try managedContext.save()
        } catch let error as NSError {
            print("Could not fetch. \(error), \(error.userInfo)")
        }
    }
    
    
}
