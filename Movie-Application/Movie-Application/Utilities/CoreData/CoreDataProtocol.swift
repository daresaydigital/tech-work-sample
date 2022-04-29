//
//  CoreDataProtocol.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/27/22.
//

import Foundation

protocol CoreDataManagerProtocol {
    func saveNewMovie(_ movie: CoreDataMovie)
    func getSavedMovies() -> [CoreDataMovie]
    func saveMovies( movies: [CoreDataMovie])
}
