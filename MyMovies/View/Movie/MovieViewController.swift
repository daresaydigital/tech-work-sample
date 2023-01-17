//
//  MovieViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/13/23.
//

import UIKit

class MovieViewController: UIViewController {

    // MARK: - Properties

    private var movieView: MovieView? = nil
    private var movieViewModel: MovieViewModel

    // MARK: - Initializer

    init(
        movieViewModel: MovieViewModel
    ) {
        self.movieViewModel = movieViewModel
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    // MARK: - View Lifecycle

    override func loadView() {
        self.movieView = MovieView()
        view = movieView
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        movieView?.renderLoadingState()

        movieViewModel.fetchMovie { [weak self] movieViewModel, error in
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
