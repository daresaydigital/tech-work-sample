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

    func fetchTrending(mediaType: String, timeWindow: String) -> String {
        let url = "\(baseURL)/\(apiVersion())/trending/\(mediaType)/\(timeWindow)"
        return applyApiKey(url)
    }

    func fetchImage(width: String, imagePath: String) -> String {
        let url = "\(baseImageURL)/\(width)/\(imagePath)"
        return applyApiKey(url)
    }

    private func applyApiKey(_ url: String) -> String {
        return "\(url)?api_key=\(apiKey)"
    }

    private func apiVersion() -> String {
        return "3"
    }
}



