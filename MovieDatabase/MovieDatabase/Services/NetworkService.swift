//
//  NetworkService.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import Foundation

enum MovieDatabaseNetworkError: Error {
    case invalidURLError
    case jsonDecodeError
    case responseError
}

class NetworkService {

    private var baseURL: URLComponents = {
        var urlComponents = URLComponents()
        urlComponents.scheme = "https"
        urlComponents.host = "api.themoviedb.org"
        return urlComponents
    }()

    private var session: URLSession {
        let configuration: URLSessionConfiguration = .default
        configuration.requestCachePolicy = .returnCacheDataElseLoad
        let session = URLSession(configuration: configuration)
        return session
    }

    func fetchTopRatedMovies(page: Int, completion: @escaping (Result<PagedMoviesResponse, MovieDatabaseNetworkError>) -> Void)  {
        baseURL.path = "/3/movie/top_rated"
        baseURL.queryItems = [
            URLQueryItem(name: "api_key", value: APIKeys.tmdbKey),
            URLQueryItem(name: "language", value: "en-US"),
            URLQueryItem(name: "page", value: "\(page)")
        ]

        let unsafeURL = baseURL.url

        guard let safeURL = unsafeURL else {
            return completion(.failure(.invalidURLError))
        }

        let task = session.dataTask(with: safeURL) { (data, response, error) in
            guard let urlResponse = response as? HTTPURLResponse,
                urlResponse.hasSuccessStatusCode,
                let jsonData = data else {
                    completion(Result.failure(.responseError))
                    return
            }

            do {
                let topRatedMovies = try JSONDecoder().decode(PagedMoviesResponse.self, from: jsonData)
                completion(Result.success(topRatedMovies))
            } catch {
                completion(Result.failure(.jsonDecodeError))
            }

        }
        task.resume()
    }

}

extension HTTPURLResponse {
  var hasSuccessStatusCode: Bool {
    return 200...299 ~= statusCode
  }
}
