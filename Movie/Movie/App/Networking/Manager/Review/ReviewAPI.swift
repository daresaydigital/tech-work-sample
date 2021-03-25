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
            if let error = error {
                completion(Result.failure(error))
                return
            }

            if let response = response as? HTTPURLResponse {
                let result = NetworkResponse.handleNetworkResponse(response)

                switch result {
                case .success:
                    guard let data = data else {
                        completion(Result.failure(NetworkResponse.noData))
                        return
                    }

                    do {
                        completion(Result.success(try JSONDecoder().decode(ReviewPayload.self, from: data)))
                        return
                    } catch let error {
                        completion(Result.failure(error))
                        return
                    }
                case .failure(let networkError):
                    completion(Result.failure(networkError))
                    return
                }
            }
        }
    }
}
