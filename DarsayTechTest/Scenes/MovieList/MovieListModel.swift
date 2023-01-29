//
//  MovieListModel.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.

import Foundation

enum MovieList {
    
    struct Configuration {
        var movieNetworkAPIManager: MovieNetworkAPI
    }
    
    struct State: StateProtocol {
        var popularList: [Movie]?
        var topRatedList: [Movie]?
        var error: Error?
    }
    
    enum Action {
        case fetchPopularMovies
        case fetchTopRatedMovies
    }
}
