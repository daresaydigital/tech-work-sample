//
//  WatchlistMoviesViewController.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/5/22.
//

import UIKit

final class WatchlistMoviesViewController: UIViewController, Storyboarded {
    weak var coordinator: WatchlistMoviesCoordinator?
    var viewModel = WatchlistMoviesViewModel(moviesService: MoviesService.shared)
    
    @IBOutlet weak var emptyWatchlistView: UIStackView!
    @IBOutlet weak var collectionView: UICollectionView!
    
    @IBAction func BrowseButtonDidTap(_ sender: UIButton) {
        coordinator?.changeTabBarIndex(to: TabBarPage.popular.pageOrderNumber())
    }
}
