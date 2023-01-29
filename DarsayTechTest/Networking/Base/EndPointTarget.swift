//
//  EndPointTarget.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

protocol EndPointTarget {
    var baseURL: String { get }
    var path: String { get }
    var parameter: [URLQueryItem] { get }
    var headers: [String : String] { get }
    var method: HTTPMethod { get }
    func getURL() -> URL?
}

extension EndPointTarget {
    var baseURL: String {
        return "api.themoviedb.org"
    }
    
    var headers: [String : String] {
        let accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4YTdhOWFhMjYyODZhOGMyZDE0OTBhNTIyNzljNzQwOSIsInN1YiI6IjYzY2ZiOGZjOGRiYzMzMDA5ZDdhNjBlZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.g3Fkog48ELAPsq80yL65wFfiNw-ZWcsGY74_NdzWz0o"
        
        return [
            "Authorization": "Bearer \(accessToken)",
            "Content-Type": "application/json;charset=utf-8"
        ]
    }
    
    func getURL() -> URL? {
        var component = URLComponents()
        component.scheme = "https"
        component.host = baseURL
        component.path = path
        component.queryItems = parameter
        return component.url
    }
}

enum HTTPMethod: String {
    case get = "GET"
    case post = "POST"
    case put = "PUT"
    case delete = "DELETE"
}
