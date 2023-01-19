//
//  MoviesModel.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 15.01.2023.
//

import Foundation

// MARK: - MoviesResponse
struct MoviesModel: Decodable {
    let total_pages: Int
    let results: [MovieModel]
}


// MARK: - Movie
struct MovieModel: Decodable {
    let id: Int?
    let title: String?
    let overview: String?
    let release_date: String?
    let vote_average: Double?
    let poster_path: String?
    let backdrop_path: String?
}
