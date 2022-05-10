//
//  MoviesGeneres.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/26/22.
//

import Foundation

struct MoviesGeneres: Decodable {
    let genres: [Genres]
}

struct Genres: Decodable {
    let id: Int
    let name: String
}
