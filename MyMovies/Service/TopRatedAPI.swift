//
//  TopRatedAPI.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/11/23.
//

import Foundation

struct TopRatedAPI: APIHandler {
    func makeRequest(from params: [String : Any]?) -> URLRequest? {
        let urlString = APIPath().fetchTopRated()
        if let url = URL(string: urlString) {
            var urlRequest = URLRequest(url: url)
            urlRequest.httpMethod = "GET"
            return urlRequest
        }
        return nil
    }

    func parseResponse(data: Data, response: HTTPURLResponse) throws -> TrendingDTO {
        return try defaultParseResponse(data: data, response: response)
    }
}
