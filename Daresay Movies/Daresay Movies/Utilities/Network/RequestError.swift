//
//  RequestError.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import Foundation

enum RequestError: Error, LocalizedError {
    case unknownError
    case connectionError
    case badHTTPStatus(status: Int, message: String?)
    case authorizationError
    case invalidRequest
    case notFound
    case serverUnavailable
    case jsonParseError
}

extension RequestError {
    public var errorDescription: String? {
        switch self {
        case .connectionError:
            return "Internet Connection Error"
        case .badHTTPStatus(status: let status, message: let message):
            return "Error with status `\(status) and message `\(message ?? "nil")` was thrown"
        case .notFound:
            return "Request not found"
        case .jsonParseError:
            return "JSON parsing probelm, make sure response has a valid JSON"
        default:
            return "Network Error with` \(self)` thrown"
        }
    }
}
