//
//  ReviewAPI.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-25.
//

import Foundation

struct ReviewAPI {
    let router = NetworkRouter<ReviewApiProvider>()
}

extension ReviewAPI {
    func fetchReviews(
        for movie: Id,
        page: Int,
        locale: Locale = Current.locale,
        completion: @escaping (Result<ReviewPayload, Error>
        ) -> Void) {
        router.request(.fetchReviews(for: movie, page: page, locale: locale)) { data, response, error in
            let result: Result<ReviewPayload, Error> = decode(data: data, response: response, error: error)
            completion(result)
        }
    }
}
