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
    "https://secure.closepayment.com"
}

enum URLPath {
    
    case version
    case admin
    case place
    case appID
    case pathID(String)
    case image
    
    var toString: String {
        var result = ""
        switch self {
        case .version:
            result = "1.0"
        case .pathID(let id):
            result = "\(id)"
        case .admin:
            result = "close-admin"
        case .place:
            result = "place"
        case .appID:
            result = "meappid"
        case .image:
            result = "image"
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
