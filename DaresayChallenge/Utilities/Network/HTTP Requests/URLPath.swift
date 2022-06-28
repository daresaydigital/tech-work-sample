//
//  URLPath.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import Foundation

protocol RequestURLProtocol {
    var baseURLString: String { get set }
}

private var movieSampleBaseURL: String {
    "https://api.themoviedb.org"
}

enum URLPath {
    
    case version
    case pathID(String)
    case movie
    case popular
    case config
    
    var toString: String {
        var result = ""
        switch self {
        case .version:
            result = "3"
        case .pathID(let id):
            result = "\(id)"
        case .movie:
            result = "movie"
        case .popular:
            result = "popular"
        case .config:
            result = "configuration"
        }
        
        return result
    }
}

struct RequestURL {
    
    init(baseURLString: String = movieSampleBaseURL) {
        self.baseURLString = baseURLString
    }
    
    var baseURLString: String
    
    var url: URL? {
        return URL(string: self.baseURLString)
    }
    
    @discardableResult
    mutating func appendPathComponent(_ path: URLPath) -> RequestURL {
        self.baseURLString += "/" +  path.toString
        return self
    }
    
    @discardableResult
    mutating func appendPathComponents(_ pathComponents: [URLPath]) -> RequestURL {
        for pathComponent in pathComponents {
            self.appendPathComponent(pathComponent)
        }
        return self
    }
}

extension RequestURL: RequestURLProtocol {
    
}
