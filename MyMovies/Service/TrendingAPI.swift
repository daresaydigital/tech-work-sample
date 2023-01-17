//
//  TrendingAPI.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import Foundation

enum MediaType: String {
    case all = "all"
    case movie = "movie"
    case tv = "tv"
    case person = "person"
}

enum TimeWindow: String {
    case day = "day"
    case week = "week"
}

struct TrendingParams {
    let mediaType: MediaType
    let timeWindow: TimeWindow
}

struct TrendingAPI: APIHandler {
    func makeRequest(from params: TrendingParams) -> URLRequest? {
        let urlString = APIPath().fetchTrending(with: params)
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
