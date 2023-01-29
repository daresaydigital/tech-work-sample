//
//  MovieServiceEndPoint.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

enum MovieServiceEndPoint {
    case getTopRated
    case getMostPopular
    case getDetail(String)
    case getReviews(String)
}

extension MovieServiceEndPoint: EndPointTarget {
    var path: String {
        
        switch self {
        case .getTopRated:
            return "/3/movie/top_rated"
        case .getMostPopular:
            return "/3/movie/popular"
        case .getDetail(let id):
            return "/3/movie/\(id)"
        case .getReviews(let id):
            return "/3/movie/\(id)/reviews"
        }
    }
    
    var parameter: [URLQueryItem] {
        []
    }
    
    var method: HTTPMethod {
        .get
    }
}
