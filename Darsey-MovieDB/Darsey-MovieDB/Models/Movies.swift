//
//  Movies.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 08/03/2021.
//

import Foundation

// Entity used in `MoviesVC` collectionView. 
struct Movies: Codable {
    var results: [Movie]
}


// Entity entity `MovieList`
struct Movie: Codable {
    var title: String
    var release_date: String
    var vote_average: Double
    var poster_path: String?
    var backdrop_path: String?
    var id: Int
}
