//
//  NetworkingError.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import Foundation

enum APIError: Error {
    case invalidRequest
    case networkError(Error)
    case invalidResponse
    case invalidResponseCode(ServerErrorModel)
    case noResponseData
    case parserFailed(Error)
    case invalidFile
    case invalidUploadFile
    case invalidParameters(String)
    case accountDeactivated(String)
    case operationError(String)
    case noConnection
    case invalidURL
    
    var errorDescription: String? {
        switch self {
        case .invalidRequest:
            return "Could not make URLRequest from HTTPRequest."
        case .networkError:
            return "There was a network Error"
        case .invalidResponse:
            return "Could not convert url response."
        case .invalidResponseCode:
            return "Response status code is invalid"
        case .noResponseData:
            return "Server response has no data."
        case .parserFailed(let error):
            return "Could not parse server's response. \n\(error.localizedDescription)"
        case .invalidFile:
            return "Could not convert file for upload."
        case .invalidUploadFile:
            return "Document has no file to upload."
        case .invalidParameters(let parameter):
            return "Parameter missing: \(parameter)"
        case .accountDeactivated(let message):
            return "Account has been deactivated with message: \(message)"
        case .operationError(let error):
            return "Error in operation.\n\(error)"
        case .noConnection:
            return "Check your internet connection"
        case .invalidURL:
            return "URL is invalid"
        }
    }
}
