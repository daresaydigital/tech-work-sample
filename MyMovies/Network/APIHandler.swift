//
//  APIHandler.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import Foundation

protocol RequestHandler {
    associatedtype RequestDataType
    func makeRequest(from data: RequestDataType) -> URLRequest?
}

protocol ResponseHandler {
    associatedtype ResponseDataType
    func parseResponse(data: Data, response: HTTPURLResponse) throws -> ResponseDataType
}

typealias APIHandler = RequestHandler & ResponseHandler
