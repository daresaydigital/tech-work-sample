//
//  MovieEndPoint.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-25.
//

import Foundation

enum Trending: String {
    case daily = "day"
    case weekly = "week"
}

enum MovieApiProvider {
    case trending(period: Trending, page: Int, locale: Locale)
    case popular(page: Int, locale: Locale)
    case topRated(page: Int, locale: Locale)
    case genre(locale: Locale)
}

extension MovieApiProvider: EndPointType {
    var baseURL: URL {
        MBaseURL.url
    }

    var path: String {
        switch self {
        case .genre:
            return "genre/movie/list"
        case .popular:
            return "movie/popular"
        case .topRated:
            return "movie/top_rated"
        case .trending(period: let trending, _, _):
            return "trending/movie/\(trending.rawValue)"
        }
    }

    var httpMethod: HTTPMethod {
        .get
    }

    var task: HTTPTask {

        func params(page: Int?, locale: Locale) -> Parameters {
            [
                "api_key": "\(Current.token)",
                "language": "\(locale)",
                "page": page
            ]
            .compactMapValues { $0 }
        }

        switch self {
        case .genre(locale: let locale):
            return .requestParameters(bodyParameters: nil,
                                      bodyEncoding: .urlEncoding,
                                      urlParameters: params(page: nil, locale: locale))
        case .popular(page: let page, locale: let locale):
            return .requestParameters(bodyParameters: nil,
                                      bodyEncoding: .urlEncoding,
                                      urlParameters: params(page: page, locale: locale))
        case .topRated(page: let page, locale: let locale):
            return .requestParameters(bodyParameters: nil,
                                      bodyEncoding: .urlEncoding,
                                      urlParameters: params(page: page, locale: locale))
        case .trending(period: _, page: let page, locale: let locale):
            return .requestParameters(bodyParameters: nil,
                                      bodyEncoding: .urlEncoding,
                                      urlParameters: params(page: page, locale: locale))
        }
    }

    var headers: HTTPHeaders? {
        nil
    }

}
