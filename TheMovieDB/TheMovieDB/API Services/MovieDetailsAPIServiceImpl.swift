//
//  MovieDetailsAPIServiceImpl.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import Foundation

protocol MovieDetailsAPIService {
    func getMovieDetails(for movieId: Int, completed: @escaping (Result<MovieDetails, TMSError>) -> Void )
}

class MovieDetailsAPIServiceImpl: MovieDetailsAPIService {
    private var httpRequest: HTTPRequest
 
    init(httpRequest: HTTPRequest) {
        self.httpRequest = httpRequest
    }
    
    func getMovieDetails(for movieId: Int, completed: @escaping (Result<MovieDetails, TMSError>) -> Void ) {
            
        httpRequest.get(from: MovieEndpointBuilder.movieEndpoint(pathComponents: [String(movieId)]), params: nil, returnType: MovieDetails.self) { result in
        
            switch result {
            case .success(let movieDetails):
                completed(.success(movieDetails))
            case .failure(let error):
                completed(.failure(error))
            }
        }
    }
}
