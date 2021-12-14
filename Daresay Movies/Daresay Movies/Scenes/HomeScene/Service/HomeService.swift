//
//  HomeService.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import Foundation

/*

 This is Home Service, responsible for making api calls of getting Movies.
 
 */

typealias MoviesCompletionHandler = (Result<RequestCallback<MovieArrayModel>, RequestError>) -> Void
typealias ConfigCompletionHandler = (Result<ConfigModel, RequestError>) -> Void

protocol HomeServiceProtocol {
    func getConfiguration(completionHandler: @escaping ConfigCompletionHandler)
    func getMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler)
}

/*
 HomeEndPoints is URLPath of home Movies Api calls
 */

private enum HomeEndPoints {
    
    case popular(Int)
    case config
    
    var path: String {
        switch self {
        case .popular(let page):
            return "movie/popular?page=\(page)"
        case .config:
            return "configuration"
        }
    }
}

class HomeService: HomeServiceProtocol {
    
    private let requestManager: RequestManagerProtocol
    
    public static let shared: HomeServiceProtocol = HomeService(requestManager: RequestManager.shared)
    
    // We can also inject requestManager for testing purposes.
    init(requestManager: RequestManagerProtocol) {
        self.requestManager = requestManager
    }
    
    func getMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler) {
        self.requestManager.performRequestWith(url: HomeEndPoints.popular(page).path, httpMethod: .get) { (result: Result<RequestCallback<MovieArrayModel>, RequestError>) in
            // Taking Data to main thread so we can update UI.
            DispatchQueue.main.async {
                completionHandler(result)
            }
        }
    }
    
    func getConfiguration(completionHandler: @escaping ConfigCompletionHandler) {
        self.requestManager.performRequestWith(url: HomeEndPoints.config.path, httpMethod: .get) { (result: Result<ConfigModel, RequestError>) in
            // Taking Data to main thread so we can update UI.
            DispatchQueue.main.async {
                completionHandler(result)
            }
        }
    }
}
