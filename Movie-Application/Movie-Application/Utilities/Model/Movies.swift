//
//  Movies.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/26/22.
//

import Foundation

struct Movie: Codable {
    let title: String
    let poster: String?
    let id: Int
    
    enum CodingKeys: String, CodingKey {
        case poster = "poter_path"
        case title, id
    }
}
