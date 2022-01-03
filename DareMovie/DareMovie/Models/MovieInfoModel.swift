//
//  MovieInfoModel.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import Foundation

class MovieInfoModel: Codable {
    let posterPath: String?
    let releaseDate: String
    let voteAverage: Float
    let popularity: Float
    let id: Int
    let title: String
    let overview: String
}
