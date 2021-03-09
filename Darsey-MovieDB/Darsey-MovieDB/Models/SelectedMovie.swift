//
//  SelectedMovie.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 08/03/2021.
//

import Foundation

struct SelectedMovie: Codable {
    var title: String
    var runtime: Int
    var release_date: String
    var vote_average: Double
    var genres: [Genre]
    var overview: String
}

struct Genre: Codable, Equatable {
    var id: Int
    var name: String
}
