//
//  HTTPConstants.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import Foundation

enum HTTPMethod: String {
    case GET
    case POST
    case PUT
    case DELETE
    case HEAD
    case OPTIONS
}

enum HTTPTimeOut: Double {
    case short = 15.0
    case normal = 30.0
    case long = 120.0
    case unlimited = 3_600.0
}

enum HTTPHeaders {
    static let ContentType = "content-type"
    static let ContentLength = "Content-Length"
    static let Accept = "Accept"
    static let Authorization = "Authorization"
    static let UserAgent = "User-Agent"
    static let AcceptLanguage = "Accept-Language"
    static let AcceptVersion = "Accept-Version"
}

enum HTTPHeaderValues {
    static let UserAgent = "mobile"
    static let AcceptVersion = "1"
    static var AcceptLanguage = "en-US"
}

enum HttpContentType {
    case any
    case json
    case data
    case urlEncodedForm
    case multipartFormdata
    case text
    
    var value: String {
        switch self {
        case .any:
            return "*/*"
        case .json:
            return "application/json;charset=UTF-8"
        case .data:
            return "application/octet-stream"
        case .urlEncodedForm:
            return "application/x-www-form-urlencoded"
        case .multipartFormdata:
            return "multipart/form-data"
        case .text:
            return "text/plain"
        }
    }
}

var baseRequestHeaders: [String: String] {
    return [
        HTTPHeaders.UserAgent: HTTPHeaderValues.UserAgent,
        HTTPHeaders.AcceptLanguage: HTTPHeaderValues.AcceptLanguage,
        HTTPHeaders.AcceptVersion: HTTPHeaderValues.AcceptVersion,
        HTTPHeaders.ContentType: HttpContentType.json.value
    ]
}
