//
//  MoviesService.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/26/22.
//

import Foundation

private enum MoviesEndpoint {
    case generes
    case topRatedMovies(Int)
    case popularMovies(Int)
    
    var path: String {
        switch self {
        
        case .generes:
            return "genre/movie/list?"
        case .topRatedMovies(let page):
            return "movie/top_rated?page=\(page)&"
        case .popularMovies(let page):
            return "movie/popular?page=\(page)&"
            
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
    
    func getTopRatedMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler) {
        self.requestManager.performRequestWith(url: MoviesEndpoint.topRatedMovies(page).path, httpMethod: .get) { (result: Result<Movies, RequestError>) in
            // Taking Data to main thread so we can update UI.
            DispatchQueue.main.async {
                completionHandler(result)
            }
        }
    }
    
    func getPopularMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler) {
        self.requestManager.performRequestWith(url: MoviesEndpoint.popularMovies(page).path, httpMethod: .get) { (result: Result<Movies, RequestError>) in
            // Taking Data to main thread so we can update UI.
            DispatchQueue.main.async {
                completionHandler(result)
            }
        }
    }
    
}
