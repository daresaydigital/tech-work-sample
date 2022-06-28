//
//  MoviesService.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import Foundation

// Using Decorator pattern for Service and ViewModel

typealias MoviesCompletionHandler = (Result<ServerModels.Movies.Response, Error>) -> Void
typealias ConfigCompletionHandler = (Result<ServerModels.Configuration.Response, Error>) -> Void

protocol MoviesServiceProtocol {
    func getMovies(httpRequest: HTTPRequest, completionHandler: @escaping MoviesCompletionHandler)
    func getConfigs(httpRequest: HTTPRequest, completionHandler: @escaping ConfigCompletionHandler)
}

// MARK: - Server Request
extension ServerRequest {
    enum Movies {
        static func getMovies(page: UInt) -> HTTPRequest {
            var url = RequestURL()
            url.appendPathComponents([.version, .movie, .popular])
            let headers: [String: String] = baseRequestHeaders
            let params: [String: Any] = ["page": "\(page)"]
            
            return HTTPRequest(method: .GET, url: url, auth: .otp, parameters: params, bodyMessage: nil, headers: headers, timeOut: .normal)
        }
    }
    
    enum Configuration {
        static func getConfigs() -> HTTPRequest {
            var url = RequestURL()
            url.appendPathComponents([.version, .config])
            let headers: [String: String] = baseRequestHeaders
            
            return HTTPRequest(method: .GET, url: url, auth: .otp, parameters: nil, bodyMessage: nil, headers: headers, timeOut: .normal)
        }
    }
}

// MARK: - Movies Service
final class MoviesService {
    private let serverManager: ServerProtocol
    
    public static let shared: MoviesServiceProtocol = MoviesService(serverManager: MovieServer.shared)
    
    // MARK: - Init
    init(serverManager: ServerProtocol) {
        self.serverManager = serverManager
    }
}

// MARK: - MoviesService Protocol
extension MoviesService: MoviesServiceProtocol {
    func getMovies(httpRequest: HTTPRequest, completionHandler: @escaping MoviesCompletionHandler) {
        serverManager.perform(request: httpRequest)
        
            .done { (result: ServerData<ServerModels.Movies.Response>) in
                completionHandler(.success(result.model))
            }
        
            .catch { error in
                completionHandler(.failure(error))
            }
    }
    
    func getConfigs(httpRequest: HTTPRequest, completionHandler: @escaping ConfigCompletionHandler) {
        serverManager.perform(request: httpRequest)
        
            .done { (result: ServerData<ServerModels.Configuration.Response>) in
                completionHandler(.success(result.model))
            }
        
            .catch { error in
                completionHandler(.failure(error))
            }
    }
}
