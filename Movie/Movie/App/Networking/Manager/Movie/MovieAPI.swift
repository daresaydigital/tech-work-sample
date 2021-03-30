//
//  MovieAPI.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-25.
//

import Foundation

struct MovieAPI {
    let router: NetworkRouter<MovieApiProvider>

    init(_ router: NetworkRouter<MovieApiProvider> = NetworkRouter<MovieApiProvider>()) {
        self.router = router
    }
}

extension MovieAPI {
    func fetchGenres(
        locale: Locale,
        completion: @escaping (Result<GenrePayload, Error>) -> Void
    ) {
        router.request(.genre(locale: locale)) { (data, response, error) in
            let result: Result<GenrePayload, Error> = decode(data: data, response: response, error: error)
            completion(result)
        }
    }
}

extension MovieAPI {
    func fetchTrending(
        period: Trending,
        page: Int,
        locale: Locale,
        completion: @escaping (Result<MoviePayload, Error>) -> Void
    ) {
        router.request(.trending(period: period, page: page, locale: locale)) { data, response, error in
            let result: Result<MoviePayload, Error> = decode(data: data, response: response, error: error)
            completion(result)
        }
    }
}

extension MovieAPI {
    func fetchPopular(
        page: Int,
        locale: Locale,
        completion: @escaping (Result<MoviePayload, Error>) -> Void
    ) {
        router.request(.popular(page: page, locale: locale)) { (data, response, error) in
            let result: Result<MoviePayload, Error> = decode(data: data, response: response, error: error)
            completion(result)
        }
    }
}

extension MovieAPI {
    func fetchTopRated(
        page: Int,
        locale: Locale,
        completion: @escaping (Result<MoviePayload, Error>) -> Void
    ) {
        router.request(.topRated(page: page, locale: locale)) { (data, response, error) in
            let result: Result<MoviePayload, Error> = decode(data: data, response: response, error: error)
            completion(result)
        }
    }
}
