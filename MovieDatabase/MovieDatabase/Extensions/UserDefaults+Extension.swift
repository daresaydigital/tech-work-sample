//
//  UserDefaults+Extension.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import Foundation

extension UserDefaults {
    #warning("Might change to json persistence if there is time left")
    var movies: [Movie] {
        get {
            guard let data = value(forKey:"savedMovies") as? Data else { return [] }
            guard let movies = try? PropertyListDecoder().decode([Movie].self, from: data) else { return [] }
            return movies
        }
        set {
            let movies = try? PropertyListEncoder().encode(newValue.self)
            set(movies, forKey: "savedMovies")
        }
    }

    var images: [String:Image] {
        get {
            guard let data = value(forKey:"savedImages") as? Data else { return [:] }
            guard let images = try? PropertyListDecoder().decode([String:Image].self, from: data) else { return [:] }
            return images
        }
        set {
            let images = try? PropertyListEncoder().encode(newValue.self)
            set(images, forKey: "savedImages")
        }
    }
}
