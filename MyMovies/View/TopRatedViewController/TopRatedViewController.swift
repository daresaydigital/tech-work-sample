//
//  TopRatedViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class TopRatedViewController: UIViewController {

//    private var topRatedView: TopRatedView? = nil
//    private var topRatedViewModel: TopRatedViewModel

    init(/*topRatedViewModel: TopRatedViewModel*/) {
//        self.topRatedViewModel = topRatedViewModel
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    override func loadView() {
//        self.topRatedView = TopRatedView()
//        self.topRatedView?.delegate = self
//        view = topRatedView
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
