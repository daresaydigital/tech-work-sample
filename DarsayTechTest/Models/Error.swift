//
//  Error.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

struct AppError: Error, LocalizedError {
    
    let reason: String?
    
    var errorDescription: String? {
        reason ?? LocalizeHelper.shared.lookup(.unknownError)
    }
}

enum NetworkError: Error {
    case badURL
    case invalidJSON
    case serverError
    case noResponse
    case unauthorized
    case generalError
    case decodeFailed
    case notFound
}
