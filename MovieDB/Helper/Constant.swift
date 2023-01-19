//
//  Constant.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 15.01.2023.
//

import Foundation

struct Constant {
    
    struct MoviesCollectionReusableView {
        static let segmentItems = [
            "Top Rated",
            "Most Popular"
        ]
    }
    
    struct MoviesViewController {
        static let title = "Movies"
    }
    
    struct Api {
        static let key = "d90d3f8fb7f2d89dd5603d1a182fd2ca"
        
        struct KeyWord {
            static let apiKey = "api_key"
            static let page = "page"
        }
        
        struct Url {
            static let baseMovieUrl = "https://api.themoviedb.org"
            static let baseImageUrl = "https://image.tmdb.org"
            static let pathMovie = "3/movie"
            static let pathImage = "t/p/w500"
        }
    }
}
