//
//  TrendingDTO.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import Foundation

struct TrendingDTO: Decodable {
    let page: Int?
    let results: [TrendingResult]?
    let totalPages: Int?
    let totalResults: Int?
}
