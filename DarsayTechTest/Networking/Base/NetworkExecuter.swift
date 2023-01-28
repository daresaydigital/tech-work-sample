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
    var timeoutInterval: Double
    
    init(urlSession: URLSession = URLSession.shared, timeoutInterval: Double) {
        self.urlSession = urlSession
        self.timeoutInterval = timeoutInterval
    }
    
    func performRequest<T: Codable>(endpoint: EndPointTarget, responseModel: T.Type, cachedResponseOnError: Bool) -> AnyPublisher<T, Error> {
     
        guard let url = endpoint.getURL() else { return Fail(error: NetworkError.badURL).eraseToAnyPublisher() }
        
        var request = URLRequest(url: url, timeoutInterval: timeoutInterval)
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
               LoggerHelper.shared.log(data: data)
               return cachedResponse.data
            }
            LoggerHelper.shared.log(httpResponse, data: data)
            return data
        }
        .decode(type: T.self, decoder: JSONDecoder())
        .mapError { error in
            LoggerHelper.shared.log(error: error)
            return AppError(reason: error.localizedDescription)
        }
        .eraseToAnyPublisher()
    }
}
