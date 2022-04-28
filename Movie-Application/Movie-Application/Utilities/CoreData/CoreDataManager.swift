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
        guard let appDelegate =
            UIApplication.shared.delegate as? AppDelegate else {
            return
          }
          
          let managedContext =
            appDelegate.persistentContainer.viewContext
          
          let entity =
            NSEntityDescription.entity(forEntityName: "FavoriteMovie",
                                       in: managedContext)!
          
          let favoriteMovie = NSManagedObject(entity: entity,
                                       insertInto: managedContext)
          
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
    
    
}
