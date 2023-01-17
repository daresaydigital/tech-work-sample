//
//  JsonHelper.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/16/23.
//

import Foundation
@testable import MyMovies

class JsonHelper {
    func getMockData(for path: String) -> Data? {
        guard let pathString = Bundle(for: type(of: self)).path(forResource: path, ofType: "json") else {
            fatalError("Mock json not found")
        }

        guard let jsonString = try? String(contentsOfFile: pathString, encoding: .utf8) else {
            fatalError("Mock json can not be converted to String")
        }

        return jsonString.data(using: .utf8)
    }

    func getMockJson<T: Decodable>(for data: Data) -> T {
        let jsonDecoder = JSONDecoder()
        jsonDecoder.keyDecodingStrategy = .convertFromSnakeCase
        do {
            let body = try jsonDecoder.decode(T.self, from: data)
            return body
        } catch {
            fatalError("Error when decoding json")
        }
    }
}
