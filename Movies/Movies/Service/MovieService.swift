//
//  MovieService.swift
//  Movies
//
//  Created by Richard Segerblom on 2022-12-21.
//

import Foundation
import Combine

struct MovieService: MovieServicing {
    func fetchMostPopular() -> AnyPublisher<[Movie], Error> {
        URLSession.shared.dataTaskPublisher(for: makeURL(path: Path.popular))
            .map { $0.data }
            .decode(type: Response.self, decoder: JSONDecoder())
            .map(\.results)
            .eraseToAnyPublisher()
    }
    
    func fetchTopRated() -> AnyPublisher<[Movie], Error> {
        URLSession.shared.dataTaskPublisher(for: makeURL(path: Path.topRated))
            .map { $0.data }
            .decode(type: Response.self, decoder: JSONDecoder())
            .map(\.results)
            .eraseToAnyPublisher()
    }
}

private extension MovieService {
    struct Path {
        static let apiKey = "1c4ac7b014ebee3e535eddadd606a23e"
        static let popular = "https://api.themoviedb.org/3/movie/popular?api_key=\(apiKey)"
        static let topRated = "https://api.themoviedb.org/3/movie/top_rated?api_key=\(apiKey)"
    }
    
    struct Response: Decodable {
        let page: Int
        let results: [Movie]
    }
    
    func makeURL(path: String) -> URL {
        guard let url = URL(string: path) else {
            preconditionFailure("Unable to construct url")
        }
        
        return url
    }
}
