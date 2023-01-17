//
//  HomeViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class HomeViewController: UIViewController, Coordinating {

    // MARK: - Properties

    var coordinator: Coordinator?

    private var trendingViewController: MovieListViewController
    private var topRatedViewController: MovieListViewController

    private var homeView: HomeView? = nil

    // MARK: - Initializer

    init(
        trendingViewController: MovieListViewController,
        topRatedViewController: MovieListViewController,
        coordinator: Coordinator
    ) {
        self.trendingViewController = trendingViewController
        self.topRatedViewController = topRatedViewController
        self.coordinator = coordinator
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    // MARK: - View Lifecycle

    override func loadView() {
        self.homeView = HomeView(
            trendingViewController: trendingViewController,
            topRatedViewController: topRatedViewController
        )
        self.homeView?.delegate = self
        view = homeView
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.isNavigationBarHidden = true
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.isNavigationBarHidden = false
    }
}

// MARK: - FavoriteButtonDelegate implementation

extension HomeViewController: FavoriteButtonDelegate {
    func favoriteMoviesButtonClicked() {
        self.coordinator?.eventOccurred(with: .favoriteClicked, parameters: nil)
    }
}
