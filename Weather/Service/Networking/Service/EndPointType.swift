//
//  EndPoint.swift
//  NetworkLayer
//
//

import Foundation

enum NetworkEnvironment {
    case qa
    case production
    case staging
}

protocol EndPointType {
    var baseURL: URL { get }
    var path: String { get }
    var httpMethod: HTTPMethod { get }
    var task: HTTPTask { get }
    var headers: HTTPHeaders? { get }
}

