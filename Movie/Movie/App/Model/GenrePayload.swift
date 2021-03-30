//
//  GenrePayload.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-24.
//

import Foundation

// MARK: - GenrePayload
struct GenrePayload: Codable {
    let genres: [Genre]
}

// MARK: - Genre
struct Genre: Codable {
    let id: Int
    let name: String
}
