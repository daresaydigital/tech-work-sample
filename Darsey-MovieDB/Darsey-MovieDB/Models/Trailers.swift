//
//  Trailers.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 08/03/2021.
//

import Foundation

struct Trailers: Codable {
    var results: [Trailer]
}

struct Trailer: Codable {
    var id: String
    var key: String
}
