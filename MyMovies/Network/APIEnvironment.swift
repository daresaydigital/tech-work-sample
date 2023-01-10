//
//  APIEnvironment.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import Foundation

enum APIEnvironment {
    case development
    case production

    func baseURL() -> String {
        return "https://\(domain())"
    }

    func domain() -> String {
        switch self {
            case .development, .production:
                return "api.themoviedb.org"
        }
    }

    func apiKey() -> String {
        switch self {
            case .development, .production:
                return "16094d8ca19f9c0407db3d0b5203bd21"
        }
    }
}
