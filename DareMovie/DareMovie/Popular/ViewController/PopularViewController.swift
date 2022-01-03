//
//  PopularViewController.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import UIKit

class PopularViewController: MovieListViewController {
    required init(_ movieListType: MovieListType) {
        super.init()
        viewModel = PopularViewModel(movieListType)
        viewModel.viewController = self
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
