//
//  MovieDetails.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import Foundation

struct MovieDetails: Decodable, Equatable {
    
    let id: Int
    let genres: [Genre]
    let overview: String?
    let title: String
    let posterPath: String?
    let backdropPath: String?
    
    private var baseImageUrl: String {
        return "https://image.tmdb.org/t/p/original"
    }
    
    var posterURL: URL? {
        guard let path = posterPath else { return nil }
        return URL(string: "\(baseImageUrl)\(path)")
    }
    var backdropURL: URL? {
        guard let path = backdropPath else { return nil }
        return URL(string: "\(baseImageUrl)\(path)")
    }
}

struct Genre: Decodable, Equatable {
    let id: Int
    let name: String
}
