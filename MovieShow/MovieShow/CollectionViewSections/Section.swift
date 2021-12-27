//
//  Section.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import Foundation

class Section: Codable, Hashable {
   
    var title: String
    var movies: [Movie]
    
    init(title: String, movies: [Movie]) {
        self.title = title
        self.movies = movies
    }
    
    func getMovies(from endpoint: MovieEndpoing) {
        NetworkManager.shared.fetchMovies(from: endpoint) { [weak self] result  in
            guard let self = self else { return }
            switch result {
            case .success(let movieResponse):
                let movies = movieResponse.results
                print("DEBUG: \(movies)")
                self.movies = movies
            case .failure(let err):
                print("DEBUG: error \(err.localizedDescription)")
            }
        }
    }
    
    static func == (lhs: Section, rhs: Section) -> Bool {
        lhs.title == rhs.title
    }
    func hash(into hasher: inout Hasher) {
        hasher.combine(title)
    }
}

