//
//  Movie.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import Foundation

struct Movie: Identifiable {
    var id = UUID()
    var title: String
    var description: String
    var rating: String
}
