//
//  SelectedMovieViewModel.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 09/03/2021.
//

import Foundation

struct SelectedMovieViewModel {
    private let selectedMovie: SelectedMovie
    
    init(_ selectedMovie: SelectedMovie) {
        self.selectedMovie = selectedMovie
    }
    
    var title: String {
        return self.selectedMovie.title
    }
    
    var runtime: Int {
        return self.selectedMovie.runtime
    }
    
    var release_date: String {
        return self.selectedMovie.release_date
    }
    
    var vote_average: Double {
        return self.selectedMovie.vote_average
    }
    
    var genres: [Genre] {
        return self.selectedMovie.genres
    }
    
    var overview: String {
        return self.selectedMovie.overview
    }
    
    var runtimeInMinutes: String {
        return "\(self.selectedMovie.runtime) min"
    }
    
    var yearOfRelease: String {
        let originalDateFormat = DateFormatter()
        originalDateFormat.dateFormat = "yyyy-MM-dd"
        
        guard let date = originalDateFormat.date(from: self.selectedMovie.release_date) else { return "" }

        let newDateFormat = DateFormatter()
        newDateFormat.dateFormat = "yyyy"
        
        return newDateFormat.string(from: date)
    }
    
    var score: String {
        return "\(self.selectedMovie.vote_average) / 10"
    }
    
    var allGenres: String {
        let genres = selectedMovie.genres
        
        var allGenres = ""
        for (index,genre) in genres.enumerated(){
            if index == 0 {
                allGenres = genre.name
            } else {
                allGenres += ", \(genre.name)"
            }
        }
        
        return allGenres
    }
}
