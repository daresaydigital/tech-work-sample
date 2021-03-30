//
//  JSONDecoderHelper.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-25.
//

import Foundation

func decode<T: Decodable>(data: Data?, response: URLResponse?, error: Error?) -> Result<T, Error> {

    if let error = error {
        return Result.failure(error)
    }

    if let response = response as? HTTPURLResponse {
        let result = NetworkResponse.handleNetworkResponse(response)

        switch result {
        case .success:
            guard let data = data else {
                return Result.failure(NetworkResponse.noData)
            }

            do {
              return Result.success(try JSONDecoder().decode(T.self, from: data))

            } catch let error {
                return Result.failure(error)
            }
        case .failure(let networkError):
            return Result.failure(networkError)
        }
    }
    return .failure(NetworkResponse.failed)
}
