//
//  APIError.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 09/03/2021.
//

import Foundation

enum APIError: Error {
    case unknownError
    case invalidURL
    case invalidResponse
    case invalidData
    case decodeError
    
    var localizedDescription : String{
        switch self {
        case .unknownError:
            return "error.unknown".localized
        case .invalidURL:
            return "error.invalidURL".localized
        case .invalidResponse:
            return "error.response".localized
        case .invalidData:
            return "error.data".localized
        case .decodeError:
            return "error.decode".localized
        }
    }
}
