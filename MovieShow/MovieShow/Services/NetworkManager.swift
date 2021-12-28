//
//  NetworkManager.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import Foundation

class NetworkManager {
    static let shared = NetworkManager()
    private init() {}
    
    private let apiKey = API.key
    private let baseURL = API.baseURL
    private let urlSession = URLSession.shared
    private let jsonDecoder = Utils.jsonDecoder
    
    func fetchMovies(from endpoint: MovieEndpoing, completion: @escaping (Result<MovieResponse, MovieError>) -> Void) {
        guard let url = URL(string: "\(baseURL)movie/\(endpoint.rawValue)") else {
            completion(.failure(.invalidEndPoint))
            return
        }
        self.loadURLAndDecode(url: url, completion: completion)
    }
    
    func fetchReviews(for movie: MovieViewModel, completion: @escaping (Result<ReviewResponse, MovieError>) -> Void) {
        guard let url = URL(string: "\(baseURL)movie/\(movie.id)/reviews") else {
            completion(.failure(.invalidEndPoint))
            return
        }
        self.loadURLAndDecode(url: url, completion: completion)
    }
    
    private func loadURLAndDecode<D: Decodable>(url: URL, parameters: [String:String]? = nil, completion: @escaping (Result<D, MovieError>) -> Void) {
        guard var urlComponents = URLComponents(url: url, resolvingAgainstBaseURL: false) else {
            completion(.failure(.invalidEndPoint))
            return
        }

        var queryItems = [URLQueryItem(name: "api_key", value: apiKey)]
        if let parameters = parameters {
            queryItems.append(contentsOf: parameters.map { URLQueryItem(name: $0.key, value: $0.value)})
        }
        urlComponents.queryItems = queryItems

        guard let finalURL = urlComponents.url else {
            completion(.failure(.invalidEndPoint))
            return
        }
        print("DEBUG: final url \(finalURL)")
        urlSession.dataTask(with: finalURL) { [weak self] data, response, err in
            guard let self = self else { return }

            if err != nil {
                self.completionHandlerOnMainThrea(with: .failure(.invalidEndPoint), completion: completion)
                return
            }

            guard let response = response as? HTTPURLResponse, (200..<300).contains(response.statusCode) else {
                self.completionHandlerOnMainThrea(with: .failure(.invalidResponse), completion: completion)
                return
            }

            guard let data = data else {
                self.completionHandlerOnMainThrea(with: .failure(.noData), completion: completion)
                return
            }

            do {
                let decodedResponse = try self.jsonDecoder.decode(D.self, from: data)
                self.completionHandlerOnMainThrea(with: .success(decodedResponse), completion: completion)
            } catch {
                self.completionHandlerOnMainThrea(with: .failure(.serializationError), completion: completion)
            }
        }.resume()
    }

    private func completionHandlerOnMainThrea<D: Decodable>(with result: Result<D, MovieError>, completion: @escaping (Result<D, MovieError>) -> Void) {
        DispatchQueue.main.async {
            completion(result)
        }
    }
}
