//
//  MovieAPI.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/13/23.
//

import Foundation

struct MovieAPI: APIHandler {
    func makeRequest(from movieId: Int64) -> URLRequest? {
        let urlString = APIPath().fetchMovieDetails(for: movieId)
        if let url = URL(string: urlString) {
            var urlRequest = URLRequest(url: url)
            urlRequest.httpMethod = "GET"
            return urlRequest
        }
        return nil
    }

    func parseResponse(data: Data, response: HTTPURLResponse) throws -> Movie {
        return try defaultParseResponse(data: data, response: response)
    }
}
