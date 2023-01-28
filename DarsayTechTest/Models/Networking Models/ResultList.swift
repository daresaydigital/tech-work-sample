//
//  ResultList.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

struct ResultList<T: Codable>: Codable {
    let page: Int
    let totalPages: Int
    let totalResults: Int
    let results: [T]

    enum CodingKeys: String, CodingKey {
        case page
        case results
        case totalPages = "total_pages"
        case totalResults = "total_results"
    }
}
