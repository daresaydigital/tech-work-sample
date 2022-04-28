//
//  TopRatedMoviesView.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

final class TopRatedMoviesView: UICollectionViewController, ViewInterface {
	
	var presenter: TopRatedMoviesPresenterViewInterface!
	
	// MARK: - Properties
	
	
	// MARK: - Initialize

	
	// MARK: - Lifecycle
	
	override func viewDidLoad() {
		super.viewDidLoad()
        configureNavigation()
        
		
		self.applyTheme()
		self.presenter.viewDidLoad()
        
        configureMoviesCollectionView()
	}
	
	
	// MARK: - Theme
	
	func applyTheme() {
        view.backgroundColor = .yellow
	}
    
    // MARK: - Private functions
    private func configureNavigation() {
        navigationController?.navigationBar.prefersLargeTitles = true
        navigationController?.title = "Top Rated"
    }
	
    private func configureMoviesCollectionView() {
        let collectionView = MoviesCollectionModule().build(movies: presenter.topRatedMovies, viewType: .topRated)
        collectionView.view.layoutMargins = self.view.layoutMargins
        view.addSubview(collectionView.view)
        collectionView.didMove(toParent: self)
    }
	
	// MARK: - Actions
	
	
}

extension TopRatedMoviesView: TopRatedMoviesViewInterface {
    func showError(with error: RequestError) {
        let errorAlert = UIAlertController(title: "Error Occured", message: error.errorDescription, preferredStyle: .alert)
        let alertAction = UIAlertAction(title: "Retry", style: .default) { [weak self] action in
            self?.presenter.alertRetryButtonDidTap()
        }
        errorAlert.addAction(alertAction)
        self.present(errorAlert, animated: true, completion: nil)
    }
    
	
}
