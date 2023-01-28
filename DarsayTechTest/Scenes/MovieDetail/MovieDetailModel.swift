//
//  MovieDetailModel.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.

import Foundation

enum MovieDetail {
    
    struct Configuration {
        var movieID: Int
        var movieNetworkAPIManager: MovieNetworkAPI
    }
    
    struct State: StateProtocol {
        var movie: Movie?
        var error: Error?
        var reviewList: [Review]?
    }
    
    enum Action {
        case fetchDetail
        case fetchReviews
    }
}
