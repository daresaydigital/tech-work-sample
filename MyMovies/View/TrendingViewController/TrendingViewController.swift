//
//  TrendingViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class TrendingViewController: UIViewController {

    private var trendingView: TrendingView? = nil
//    private var trendingViewModel: TrendingViewModel

    init(/*trendingViewModel: TrendingViewModel*/) {
//        self.trendingViewModel = trendingViewModel
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    override func loadView() {
        self.trendingView = TrendingView()
//        self.trendingView?.collectionViewDelegate = trendingViewModel
//        self.trendingView?.collectionDataSource = trendingViewModel
        view = trendingView
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
