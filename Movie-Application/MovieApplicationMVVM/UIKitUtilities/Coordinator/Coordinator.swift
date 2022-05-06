//
//  Coordinator.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/3/22.
//

import UIKit

// MARK: - Coordinator
protocol Coordinator: AnyObject {
    var parentCoordinator: Coordinator? { get set }
    func changeTabBarIndex(to index: Int)
    var navigationController: UINavigationController? { get set }
    func showMovieDetails(_ movie: MovieDetail)
}

extension Coordinator {
    func showMovieDetails(_ movie: MovieDetail) {

        let movieDetailsInfoViewController = MovieDetailsInfoViewController()
        movieDetailsInfoViewController.movie = movie
        let movieContentViewController = MovieInfoContentViewController(movie: movie)
        let movieDetailsViewController = MovieDetailsViewController(contentViewController: movieContentViewController,
                                                                    bottomSheetViewController: movieDetailsInfoViewController,
                                                                    bottomSheetConfiguration: .init(height: UIScreen.main.bounds.height*0.8,
                                                                                                    initialOffset: UIScreen.main.bounds.height / 2.2))
        movieDetailsViewController.modalPresentationStyle = .popover
        self.navigationController?.present(movieDetailsViewController, animated: true, completion: nil)
    }
}
