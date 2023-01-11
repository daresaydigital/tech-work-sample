//
//  HomeViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class HomeViewController: UIViewController {

    private var trendingViewController: TrendingViewController
    private var topRatedViewController: TopRatedViewController

    private var homeView: HomeView? = nil

    init(trendingViewController: TrendingViewController, topRatedViewController: TopRatedViewController) {
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

    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
