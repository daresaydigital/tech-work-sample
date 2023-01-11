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
let baseImageURL = environment.baseImageURL()
let apiKey = environment.apiKey()

struct APIPath {
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



