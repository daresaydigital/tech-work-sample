//
//  MoviesGeneres.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/26/22.
//

import Foundation

struct MoviesGeneres: Codable {
    let genres: [Genres]
}

struct Genres: Codable {
    let id: Int
    let name: String
}
