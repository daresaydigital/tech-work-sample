//
//  ResponseHandler+Extension.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import Foundation

extension ResponseHandler {
    func defaultParseResponse<T: Decodable>(data: Data, response: HTTPURLResponse) throws -> T {
        let jsonDecoder = JSONDecoder()
        jsonDecoder.keyDecodingStrategy = .convertFromSnakeCase
        if response.statusCode == 200 {
            do {
                let body = try jsonDecoder.decode(T.self, from: data)
                return body
            } catch {
                throw ServiceError(message: "Error when decoding json: \(error.localizedDescription)")
            }
        } else {
            throw ServiceError(message: "Error when communicating with API")
        }
    }
}
