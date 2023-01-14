//
//  HomeViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class HomeViewController: UIViewController {

    private var trendingViewController: TrendingViewController
    private var topRatedViewController: TrendingViewController

    private var homeView: HomeView? = nil

    init(
        trendingViewController: TrendingViewController,
        topRatedViewController: TrendingViewController
    ) {
        self.trendingViewController = trendingViewController
        self.topRatedViewController = topRatedViewController
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    override func loadView() {
        self.homeView = HomeView(
            trendingViewController: trendingViewController,
            topRatedViewController: topRatedViewController
        )
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
