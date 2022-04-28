//
//  Movies.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/26/22.
//

import Foundation

struct Movies: Codable {
    let results: [Movie]
}

struct Movie: Codable {
    let title: String
    let poster: String?
    let id: Int
    
    enum CodingKeys: String, CodingKey {
        case poster = "poster_path"
        case title, id
    }
}
