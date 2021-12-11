//
//  TMError.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import Foundation

enum TMSError: Error, CustomNSError {
    
    case apiError(Error)
    case invalidResponse
    case noData
    case invalidURL
    
    var localizedDescription: String {
        switch self {
        case .apiError(let error): return error.localizedDescription
        case .invalidResponse: return "Invalid response"
        case .noData: return "No data found"
        case .invalidURL: return "The provided URL is invalid"
        }
    }
    
    var isDisplayable: Bool {
        switch self {
        case .apiError:
            return true
        default:
            return false
        }
    }
}
