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
        switch self {
            case .development, .production:
                return Bundle.main.infoDictionary?["BASE_URL"] as? String ?? ""
        }
    }

    func baseImageURL() -> String {
        switch self {
            case .development, .production:
                return Bundle.main.infoDictionary?["BASE_IMAGE_URL"] as? String ?? ""
        }
    }

    func apiKey() -> String {
        switch self {
            case .development, .production:
                return Bundle.main.infoDictionary?["BASE_API_KEY"] as? String ?? ""
        }
    }
}
