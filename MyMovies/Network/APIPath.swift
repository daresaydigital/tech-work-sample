//
//  APIPath.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import Foundation

#if DEBUG
let environment = APIEnvironment.development
#else
let environment = APIEnvironment.production
#endif

let baseURL = environment.baseURL()

struct APIPath {
    func fetchTrending(mediaType: String, timeWindow: String) -> String {
        let url = "\(baseURL)/\(apiVersion())/trending/\(mediaType)/\(timeWindow)"
        return applyApiKey(url)
    }

    private func applyApiKey(_ url: String) -> String {
        return "\(url)?api_key=\(environment.apiKey())"
    }

    private func apiVersion() -> String {
        return "3"
    }
}



