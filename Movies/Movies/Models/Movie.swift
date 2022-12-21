//
//  Movie.swift
//  Movies
//
//  Created by Richard Segerblom on 2022-12-21.
//

import Foundation

struct Movie: Decodable, Identifiable {
    let id: Int
    let title: String
    let posterURL: String
    let releaseDate: String
    let description: String 
    
    enum CodingKeys: String, CodingKey {
        case id
        case title
        case posterURL = "poster_path"
        case releaseDate = "release_date"
        case description = "overview"
    }
    
    init(id: Int, title: String, posterURL: String, releaseDate: String, description: String) {
        self.id = id
        self.title = title
        self.posterURL = posterURL
        self.releaseDate = releaseDate
        self.description = description
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        
        id = try container.decode(Int.self, forKey: .id)
        title = try container.decode(String.self, forKey: .title)
        posterURL = try container.decode(String.self, forKey: .posterURL)
        releaseDate = try container.decode(String.self, forKey: .releaseDate)
        description = try container.decode(String.self, forKey: .description)
    }
}
