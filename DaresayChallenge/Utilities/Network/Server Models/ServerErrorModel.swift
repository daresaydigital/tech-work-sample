//
//  ServerErrorModel.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import Foundation

/// This model can be customised based on different
/// server error models for any project.
struct ServerErrorModel {
    
    struct ServerErrorMessage: Codable {
        let path: String
        let message: String
        let code: String?
    }
    
    var statusCode: Int?
    var url: String?
    let messages: [ServerErrorMessage]?
    
    var serverMessage: String? {
        return self.messages?.compactMap { $0.message }.joined(separator: "\n")
    }
    
    func hasError(code: String) -> Bool {
        guard let codes = (self.messages?.compactMap { $0.code }) else {
            return false
        }
        return codes.contains(code)
    }
}
