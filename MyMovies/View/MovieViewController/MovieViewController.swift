//
//  MovieViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/13/23.
//

import UIKit

class MovieViewController: UIViewController {

    private var movieView: MovieView? = nil
    private var movieViewModel: MovieViewModel
    private var movieId: Int64

    init(
        movieId: Int64,
        movieViewModel: MovieViewModel
    ) {
        self.movieId = movieId
        self.movieViewModel = movieViewModel
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    override func loadView() {
        self.movieView = MovieView()
        view = movieView
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        movieView?.renderLoadingState()

        movieViewModel.fetchMovie(for: self.movieId) { [weak self] movieViewModel, error in
            guard let self = self else {
                return
            }

            if error != nil {
                self.movieView?.renderErrorState()
            } else if let movieViewModel = movieViewModel {
                self.movieViewModel = movieViewModel
                self.movieView?.renderSuccessState(with: movieViewModel)
            }
        }
    }
}
