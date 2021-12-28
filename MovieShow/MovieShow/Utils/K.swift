//
//  K.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import UIKit

enum API {
    static let key = "decadab6493ca32f5f05379931cdee00"
    static let baseURL = "https://api.themoviedb.org/3/"
}

enum SFSymbols {
    static let film = UIImage(systemName: "film")
    static let filmSelected = UIImage(systemName: "film.fill")
    static let downloaded = UIImage(systemName: "arrow.down.circle")
    static let downloadedSelected = UIImage(systemName: "arrow.down.circle.fill")
    static let dot = UIImage(systemName: "smallcircle.filled.circle.fill")
}

enum MovieError: Error, CustomNSError {
    case apiError
    case invalidEndPoint
    case invalidResponse
    case noData
    case serializationError
    
    var localizedDescription: String {
        switch self {
        case .apiError: return "Failed to fetch data"
        case .invalidEndPoint: return "Invalid endpoing"
        case .invalidResponse: return "Invalid https response"
        case .noData: return "No Data found"
        case .serializationError: return "Unable to decode data"
        }
    }
    
    var errorUserInfo: [String : Any] {
        [NSLocalizedDescriptionKey: localizedDescription]
    }
}

enum MovieEndpoing: String {
    case popular
    case topRated = "top_rated"
    
    var description: String {
        switch self {
        case .popular: return "Popular"
        case .topRated: return "Top Rated"
        }
    }
}
