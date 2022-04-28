//
//  PopularMoviesView.swift
//  PopularMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

final class PopularMoviesView: UIViewController, ViewInterface {
	
	var presenter: PopularMoviesPresenterViewInterface!
	
	// MARK: - Properties
	
	
	// MARK: - Initialize

	
    override func viewDidLoad() {
        super.viewDidLoad()
        configureNavigation()
        
        
        self.applyTheme()
        self.presenter.viewDidLoad()
    }
    
    
    // MARK: - Theme
    
    func applyTheme() {
        view.backgroundColor = .systemBackground
    }
    
    // MARK: - Private functions
    private func configureNavigation() {
        navigationController?.navigationBar.prefersLargeTitles = true
        self.title = "Popular"
    }
    
    private func configureMoviesCollectionView(with movies: [Movie]) {
        let collectionView = MoviesCollectionModule().build(movies: movies, viewType: .popular)
        self.add(asChildViewController: collectionView, to: self.view)
    }
	
	
	// MARK: - Actions
	
	
}

extension PopularMoviesView: PopularMoviesViewInterface {
    
    func showError(with error: RequestError) {
        let errorAlert = UIAlertController(title: "Error Occured", message: error.errorDescription, preferredStyle: .alert)
        let alertAction = UIAlertAction(title: "Retry", style: .default) { [weak self] action in
            self?.presenter.alertRetryButtonDidTap()
        }
        errorAlert.addAction(alertAction)
        self.present(errorAlert, animated: true, completion: nil)
    }
    
    func loadCollectionView(with movies: [Movie]) {
        configureMoviesCollectionView(with: movies)
    }
}
