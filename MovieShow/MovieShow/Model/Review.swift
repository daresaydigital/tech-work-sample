//
//  Review.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-28.
//

import Foundation

struct ReviewResponse: Codable, Hashable {
    var id: String?
    var results: [Review]?
    
    static func == (lhs: ReviewResponse, rhs: ReviewResponse) -> Bool {
        lhs.id  == rhs.id
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
    }
}

struct Review: Codable {
    var author: String?
    var content: String?
}
