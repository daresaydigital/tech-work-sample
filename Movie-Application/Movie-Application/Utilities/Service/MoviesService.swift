//
//  MoviesService.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/26/22.
//

import Foundation

typealias GeneresCompletionHandler = (Result<MoviesGeneres, RequestError>) -> Void
typealias MoviesCompletionHandler = (Result<Movies, RequestError>) -> Void

protocol MoviesServiceProtocol {
    func getMoviesGeneres(completionHandler: @escaping GeneresCompletionHandler)
    func getTopRatedMovies(completionHandler: @escaping MoviesCompletionHandler)
    func getPopularMovies(completionHandler: @escaping MoviesCompletionHandler)
}

private enum MoviesEndpoint {
    case generes
    case topRatedMovies
    case popularMovies
    
    var path: String {
        switch self {
        
        case .generes:
            return "genre/movie/list"
        case .topRatedMovies:
            return "movie/top_rated"
        case .popularMovies:
            return "movie/popular"
            
        }
    }
}

class MoviesService: MoviesServiceProtocol {
    
    private let requestManager: RequestManagerProtocol
    
    public static let shared: MoviesServiceProtocol = MoviesService(requestManager: RequestManager.shared)
    
    init(requestManager: RequestManagerProtocol) {
        self.requestManager = requestManager
    }
    
    func getMoviesGeneres(completionHandler: @escaping GeneresCompletionHandler) {
        self.requestManager.performRequestWith(url: MoviesEndpoint.generes.path, httpMethod: .get) { (result: Result<MoviesGeneres, RequestError>) in
            // Taking Data to main thread so we can update UI.
            DispatchQueue.main.async {
                completionHandler(result)
            }
        }
    }
    
    func getTopRatedMovies(completionHandler: @escaping MoviesCompletionHandler) {
        self.requestManager.performRequestWith(url: MoviesEndpoint.topRatedMovies.path, httpMethod: .get) { (result: Result<Movies, RequestError>) in
            // Taking Data to main thread so we can update UI.
            DispatchQueue.main.async {
                completionHandler(result)
            }
        }
    }
    
    func getPopularMovies(completionHandler: @escaping MoviesCompletionHandler) {
        self.requestManager.performRequestWith(url: MoviesEndpoint.popularMovies.path, httpMethod: .get) { (result: Result<Movies, RequestError>) in
            // Taking Data to main thread so we can update UI.
            DispatchQueue.main.async {
                completionHandler(result)
            }
        }
    }
    
}
