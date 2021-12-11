//
//  HTTPRequestImpl.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import Foundation

struct MovieEndpointBuilder {
    private static let baseURL = "https://api.themoviedb.org"
    private static let apiVersion = "3"

    /// Create a movie endpoint
    /// - Parameter pathComponent: all the path components that needs to be added to the endpoint.
    /// - Returns: Movie endpoint that containts all required default path components and passed path components
    static func movieEndpoint(pathComponents: [String]? = nil) -> URL {
        var url = URL(string: baseURL)!
        url.appendPathComponent(apiVersion)
        url.appendPathComponent("movie")
        _ = pathComponents?.map { url.appendPathComponent($0)}
        return url
    }
}

protocol HTTPRequest {
    func get<T: Decodable>(from url: URL, params: [String: String]?, returnType: T.Type, completed: @escaping (Result<T, TMSError>) -> Void)
}

final class HTTPRequestImpl: HTTPRequest {
    
    let apiKey = "26f394bff527625c8ae37bcfc6fa720d"
    
    private lazy var jsonDecoder: JSONDecoder = {
        let dateFormatter: DateFormatter = {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd"
            return dateFormatter
        }()
        
        let jsonDecoder = JSONDecoder()
        jsonDecoder.keyDecodingStrategy = .convertFromSnakeCase
        jsonDecoder.dateDecodingStrategy = .formatted(dateFormatter)
        return jsonDecoder
    }()
    
    func get<T>(from url: URL, params: [String: String]?, returnType: T.Type, completed: @escaping (Result<T, TMSError>) -> Void) where T : Decodable {
        
        let urlComponents = NSURLComponents(string: url.absoluteString)
        
        // Add api key to all requests
        urlComponents?.queryItems = [
            URLQueryItem(name: "api_key", value: self.apiKey)
        ]
        
        // Add params if any
        _ = params?.map { key, value in
            urlComponents?.queryItems?.append(URLQueryItem(name: key, value: value))
        }
        
        guard let parameterizedURL = urlComponents?.url else {
            completed(.failure(.invalidURL))
            return
        }
        
        let task = URLSession.shared.dataTask(with: parameterizedURL) { data, response, error in
            
            DispatchQueue.main.async {
                
                if let error = error {
                    completed(.failure(.apiError(error)))
                    return
                }
                
                guard let data = data else {
                    completed(.failure(.noData))
                    return
                }
                
                do {
                    let decodedResponse = try self.jsonDecoder.decode(returnType, from: data)
                    completed(.success(decodedResponse))
                }
                catch {
                    completed(.failure(.invalidResponse))
                }
            }
        }
        task.resume()
    }
}
