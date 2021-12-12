//
//  MovieListAPIServiceImpl.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import Foundation

protocol MovieListAPIService {
    func getMoviesList(for type: MovieListType, pageNumber:Int, completed: @escaping (Result<MoviesResponse, TMSError>) -> Void)
}

final class MovieListAPIServiceImpl: MovieListAPIService {
    
    private var httpRequest: HTTPRequest
    
    init(httpRequest: HTTPRequest) {
        self.httpRequest = httpRequest
    }
    
    func getMoviesList(for type: MovieListType, pageNumber page:Int, completed: @escaping (Result<MoviesResponse, TMSError>) -> Void ) {
        let params: [String: String] = ["page": String(page)]
        
        httpRequest.get(from: MovieEndpointBuilder.movieEndpoint(pathComponents: [type.rawValue]), params: params, returnType: MoviesResponse.self) { result in
            switch result {
            case .success(let movieResponse):
                completed(.success(movieResponse))
            case .failure(let error):
                completed(.failure(error))
            }
        }
    }
}

// MARK: - MovieListType
enum MovieListType: String {
      
    case topRated = "top_rated"
    case popular

}
