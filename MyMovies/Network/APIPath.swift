//
//  APIPath.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import Foundation

struct APIPath {

    let environment: APIEnvironment
    let baseURL: String
    let baseImageURL: String
    let apiKey: String

    init() {
        #if DEBUG
        environment = APIEnvironment.development
        #else
        environment = APIEnvironment.production
        #endif

        baseURL = environment.baseURL()
        baseImageURL = environment.baseImageURL()
        apiKey = environment.apiKey()
    }

    func fetchTrending(with params: TrendingParams) -> String {
        let url = "\(baseURL)/\(apiVersion())/trending/\(params.mediaType.rawValue)/\(params.timeWindow.rawValue)"
        return applyApiKey(url)
    }

    func fetchImage(width: String, imagePath: String) -> String {
        let url = "\(baseImageURL)/\(width)\(imagePath)"
        return applyApiKey(url)
    }

    func fetchTopRated() -> String {
        let url = "\(baseURL)/\(apiVersion())/movie/top_rated"
        return applyApiKey(url)
    }

    func fetchMovieDetails(for movieId: Int64) -> String {
        let url = "\(baseURL)/\(apiVersion())/movie/\(movieId)"
        return applyApiKey(url)
    }

    private func applyApiKey(_ url: String) -> String {
        return "\(url)?api_key=\(apiKey)"
    }

    private func apiVersion() -> String {
        return "3"
    }
}



