//
//  ReviewEndPoint.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-25.
//

import Foundation

public enum ReviewApiProvider {
    case fetchReviews(for: Id, page: Int, locale: Locale)
}

extension ReviewApiProvider: EndPointType {
    var baseURL: URL {
        MBaseURL.url
    }

    var path: String {
        switch self {
        case .fetchReviews(for: let id, _, _):
            return "movie/\(id)/reviews"
        }
    }

    var httpMethod: HTTPMethod {
        .get
    }

    var task: HTTPTask {
        switch self {
        case .fetchReviews(for: _, let page, let locale):
            return .requestParameters(bodyParameters: nil, bodyEncoding: .urlEncoding, urlParameters: [
                "api_key": "\(Current.token)",
                "language": "\(locale)",
                "page": "\(page)"
            ])
        }
    }

    var headers: HTTPHeaders? {
        nil
    }

}
