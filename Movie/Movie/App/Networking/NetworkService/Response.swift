//
//  Response.swift
//
//
//  Created by Andrian Sergheev on 2019-02-25.
//  Copyright © 2019 Andrian Sergheev. All rights reserved.
//

import Foundation

enum NetworkResponse: String, Error {
    case authenticationError = "You need to be authenticated first."
    case badRequest = "Bad request"
    case outdated = "The url you requested is outdated."
    case failed = "Network request failed."
    case noData = "Response returned with no data to decode."
    case unableToDecode = "Could not decode the response."
}

extension NetworkResponse {
    static func handleNetworkResponse(_ response: HTTPURLResponse) -> Result<String, Error> {
        switch response.statusCode {
        case 200...299: return .success("Network request OK: 200-299")
        case 401...500: return .failure(NetworkResponse.authenticationError)
        case 501...599: return .failure(NetworkResponse.badRequest)
        case 600: return .failure(NetworkResponse.outdated)
        default: return .failure(NetworkResponse.failed)
        }
    }
}
