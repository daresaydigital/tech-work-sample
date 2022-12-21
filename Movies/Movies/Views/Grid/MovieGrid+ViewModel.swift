//
//  MovieGrid+ViewModel.swift
//  Movies
//
//  Created by Richard Segerblom on 2022-12-21.
//

import Foundation
import Combine

extension MovieGrid {
    class ViewModel: ObservableObject {
        @Published var movies: [Movie] = []
        
        private let movieService: MovieServicing
        
        private var popularMoviesSubscription: AnyCancellable?
        private var topRatedMoviesSubscription: AnyCancellable?
        
        init(movieService: MovieServicing) {
            self.movieService = movieService
            
            fetchPopular()
        }
        
        func toggle(_ index: Int) {
            movies = []
            
            switch index {
            case 0:
                fetchPopular()
            default:
                fetchTopRated()
            }
        }
        
        func movePosterURL(_ movie: Movie) -> URL? {
            URL(string: "https://image.tmdb.org/t/p/w154\(movie.posterURL)")
        }
        
        private func fetchPopular() {
            popularMoviesSubscription = movieService.fetchMostPopular()
                .receive(on: DispatchQueue.main)
                .sink(receiveCompletion: { _ in }, receiveValue: {
                    self.movies = $0
                })
        }
        
        private func fetchTopRated() {
            topRatedMoviesSubscription = movieService.fetchTopRated()
                .receive(on: DispatchQueue.main)
                .sink(receiveCompletion: { _ in }, receiveValue: {
                    self.movies = $0
                })
        }
    }
}
