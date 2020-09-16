//
//  PagedReviewResponse.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import Foundation

struct PagedReviewResponse: Codable {
    let id: Int
    let totalResults: Int
    let totalPages: Int
    let results: [Review]
    let page: Int

    enum CodingKeys: String, CodingKey {
        case id
        case totalResults = "total_results"
        case totalPages = "total_pages"
        case results
        case page
    }
}
