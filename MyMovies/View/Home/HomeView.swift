//
//  HomeView.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

protocol FavoriteButtonDelegate: AnyObject {
    func favoriteMoviesButtonClicked()
}

class HomeView: UIView {

    // MARK: - SubViews

    private var trendingView: UIView
    private var topRatedView: UIView

    private lazy var favoritesButton: Button = {
        let button = Button()
        button.translatesAutoresizingMaskIntoConstraints = false
        button.setTitle("Favorite Movies", for: .normal)
        button.addTarget(self, action: #selector(favoriteMoviesButtonClicked), for: .touchUpInside)
        return button
    }()

    // MARK: - Properties

    weak var delegate: FavoriteButtonDelegate?

    // MARK: - Initializer

    init(
        trendingViewController: MovieListViewController,
        topRatedViewController: MovieListViewController
    ) {
        self.trendingView = trendingViewController.view
        self.topRatedView = topRatedViewController.view
        super.init(frame: CGRect())

        setup()
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    // MARK: - View Setup

    private func setup() {
        self.backgroundColor = .systemBackground

        setupFavoriteButton()
    }

    private func setupFavoriteButton() {
        addSubview(favoritesButton)

        NSLayoutConstraint.activate([
            favoritesButton.topAnchor.constraint(equalTo: safeAreaLayoutGuide.topAnchor, constant: 20),
            favoritesButton.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor, constant: -20),
            favoritesButton.widthAnchor.constraint(equalToConstant: 160)
        ])

        setupTrendingView()
    }

    private func setupTrendingView() {
        addSubview(trendingView)

        trendingView.translatesAutoresizingMaskIntoConstraints = false

        NSLayoutConstraint.activate([
            trendingView.topAnchor.constraint(equalTo: favoritesButton.bottomAnchor, constant: 10),
            trendingView.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor, constant: 4),
            trendingView.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor, constant: -4),
            trendingView.heightAnchor.constraint(equalToConstant: 350)
        ])

        setupTopRatedView()
    }

    private func setupTopRatedView() {
        addSubview(topRatedView)

        topRatedView.translatesAutoresizingMaskIntoConstraints = false

        NSLayoutConstraint.activate([
            topRatedView.topAnchor.constraint(equalTo: trendingView.bottomAnchor),
            topRatedView.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor, constant: 4),
            topRatedView.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor, constant: -4),
            topRatedView.heightAnchor.constraint(equalToConstant: 350)
        ])
    }

    // MARK: - Selectors

    @objc func favoriteMoviesButtonClicked() {
        delegate?.favoriteMoviesButtonClicked()
    }
}
