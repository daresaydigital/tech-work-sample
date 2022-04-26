//
//  URLRequestLoggableProtocol.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/26/22.
//

import Foundation

protocol URLRequestLoggableProtocol {
    func logResponse(_ response: HTTPURLResponse?, data: Data?, error: Error?, HTTPMethod: String?)
}
