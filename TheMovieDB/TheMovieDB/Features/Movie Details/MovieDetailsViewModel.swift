//
//  MovieDetailsViewModel.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import Foundation
import UIKit

class MovieDetailsViewModel {
    
    var onShouldReloadTableView: (() -> Void)?
    var onLoadingStateShouldChange: ((LoadingState) -> Void)?

    var movieId: Int
    
    var movieDetails: MovieDetails? {
        didSet {
            onShouldReloadTableView?()
        }
    }
    
    var numberOfRows: Int {
        guard self.movieDetails != nil else {
            return 0
        }
        return 1
    }
    
    private var MovieDetailsService: MovieDetailsAPIService
    
    init(MovieDetailsService: MovieDetailsAPIService, movieId: Int) {
        self.MovieDetailsService = MovieDetailsService
        self.movieId = movieId
    }
    
    private func fetchMovieDetails(withID id: Int){
        onLoadingStateShouldChange?(.loading)
        MovieDetailsService.getMovieDetails(for: id) { [weak self] result in
            guard let self = self else { return }
            switch result {
            case .success(let response):
                self.movieDetails = response
                self.onLoadingStateShouldChange?(.loaded)
            case .failure(let error):
                self.onLoadingStateShouldChange?(.failed(error))
            }
        }
    }
    
    func getCellData(for _: IndexPath) -> MovieDetails? {
        // We have only one cell at the moment
        return self.movieDetails
    }
}

extension MovieDetailsViewModel {
    
    func viewDidLoad() {
        fetchMovieDetails(withID: movieId)
    }
}
