//
//  CoreDataProtocol.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/27/22.
//

import Foundation

protocol CoreDataManagerProtocol {
    func saveNewMovie(_ movie: Movie)
    func getSavedMovies() -> [Movie]
    func deleteMovie(_ movie: Movie)
}
