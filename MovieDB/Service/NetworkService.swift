//
//  NetworkManager.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 15.01.2023.
//

import Foundation

enum APIError: Error {
    case responseFailed
    case invalidURL
    case invalidData
    case decodeError
}

final class NetworkService {
    
    static let shared = NetworkService()
    private init() {}
    
    func fetchData(url: URL, completion: @escaping (Result<Data, APIError>) -> Void) {
        URLSession.shared.dataTask(with: url, completionHandler: {(data, response, error) in
            if let _ = error {
                completion(.failure(.responseFailed))
                return
            }
            guard let _ = response else {
                completion(.failure(.responseFailed))
                return
            }
            guard let data = data else {
                completion(.failure(.invalidData))
                return
            }
            completion(.success(data))
        }).resume()
    }
}
