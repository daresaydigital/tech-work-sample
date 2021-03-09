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
    
}
