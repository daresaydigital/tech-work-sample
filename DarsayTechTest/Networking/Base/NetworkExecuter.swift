//
//  NetworkExecuter.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation
import Combine
import UIKit

protocol HTTPRequestProtocol {
    func performRequest<T: Codable>(endpoint: EndPointTarget, responseModel: T.Type, cachedResponseOnError: Bool) -> AnyPublisher<T, Error>
}

class HTTPRequestExecuter: HTTPRequestProtocol {
 
    var urlSession: URLSession
    
    init(urlSession: URLSession = URLSession.shared) {
        self.urlSession = urlSession
    }
    
    func performRequest<T: Codable>(endpoint: EndPointTarget, responseModel: T.Type, cachedResponseOnError: Bool) -> AnyPublisher<T, Error> {
     
        guard let url = endpoint.getURL() else { return Fail(error: NetworkError.badURL).eraseToAnyPublisher() }
        
        var request = URLRequest(url: url)
        request.httpMethod = endpoint.method.rawValue
        request.allHTTPHeaderFields = endpoint.headers
        
        return urlSession.dataTaskPublisher(for: request).tryMap { [weak self] data, response in
            guard let httpResponse = response as? HTTPURLResponse else {
                throw NetworkError.noResponse
            }
            guard 200..<400 ~= httpResponse.statusCode else {
                
                guard let urlCache = self?.urlSession.configuration.urlCache, let cachedResponse = urlCache.cachedResponse(for: request) else {
                        throw NetworkError.serverError
                      }
                
               return cachedResponse.data
            }
            
            return data
        }
        .decode(type: T.self, decoder: JSONDecoder())
        .mapError { error in
            AppError(reason: error.localizedDescription)
        }
        .eraseToAnyPublisher()
    }
}
