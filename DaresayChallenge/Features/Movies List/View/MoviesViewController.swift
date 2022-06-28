//
//  MoviesViewController.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import UIKit

class MoviesViewController: UIViewController {

    // MARK: - Variables
    private let viewModel: MoviesViewModel = MoviesViewModel(moviesService: MoviesService.shared)
    
    // MARK: - View Lifecycle
    override func viewDidLoad() {
        super.viewDidLoad()

        setupUI()
        setupBindings()
        
        populate()
    }
}

// MARK: - Helpers
private extension MoviesViewController {
    func setupUI() {
        view.backgroundColor = .red
    }
    
    func populate() {
        viewModel.populate()
    }
    
    func setupBindings() {
        viewModel.delegate = self
    }
}

// MARK: -
extension MoviesViewController: MoviesViewModelDelegate {
    func populate(displayState: DisplayState<[MoviesModel]>) {
        switch displayState {
        case .loading:
            view.animateActivityIndicator()
        case .success(let results):
            print(results)
            view.removeActivityIndicator()
        case .failure:
            view.removeActivityIndicator()
        }
    }
}
