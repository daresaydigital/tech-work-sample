//
//  ResponseValidator.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import Foundation

struct DaMoviesResponseValidator: ResponseValidatableProtocol {
    
    func validation<T: Codable>(response: HTTPURLResponse?, data: Data?) -> (Result<T, RequestError>) {
        guard let response = response, let data = data else {
            return .failure(RequestError.invalidRequest)
        }
        switch response.statusCode {
        case 200:
            do {
                let model = try JSONDecoder().decode(T.self, from: data)
                return .success(model)
            } catch {
                print("JSON Parse Error")
                print(error)
                return .failure(.jsonParseError)
            }
        case 400...499:
            return .failure(RequestError.authorizationError)
        case 500...599:
            return .failure(.serverUnavailable)
        default:
            return .failure(RequestError.unknownError)
        }
    }
}
