//
//  HomeView.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class HomeView: UIView {

    private var trendingView: UIView
    private var topRatedView: UIView

    init(
        trendingViewController: TrendingViewController,
        topRatedViewController: TrendingViewController
    ) {
        self.trendingView = trendingViewController.view
        self.topRatedView = topRatedViewController.view
        super.init(frame: CGRect())

        setup()
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    private func setup() {
        self.backgroundColor = .systemBackground

        setupTrendingView()
    }

    private func setupTrendingView() {
        addSubview(trendingView)

        trendingView.translatesAutoresizingMaskIntoConstraints = false

        NSLayoutConstraint.activate([
            trendingView.topAnchor.constraint(equalTo: safeAreaLayoutGuide.topAnchor, constant: 20),
            trendingView.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor, constant: 4),
            trendingView.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor, constant: -4),
            trendingView.heightAnchor.constraint(equalToConstant: 300)
        ])

        setupTopRatedView()
    }

    private func setupTopRatedView() {
        addSubview(topRatedView)

        topRatedView.translatesAutoresizingMaskIntoConstraints = false

        NSLayoutConstraint.activate([
            topRatedView.topAnchor.constraint(equalTo: trendingView.bottomAnchor, constant: 20),
            topRatedView.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor, constant: 4),
            topRatedView.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor, constant: -4),
            topRatedView.heightAnchor.constraint(equalToConstant: 300)
        ])
    }
}
