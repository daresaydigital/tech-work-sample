//
//  FavoriteMoviesView.swift
//  FavoriteMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

final class FavoriteMoviesView: UIViewController, ViewInterface {
	
	var presenter: FavoriteMoviesPresenterViewInterface!
	
	// MARK: - Properties
	
	
	// MARK: - Initialize

	
	// MARK: - Lifecycle
	
	override func viewDidLoad() {
		super.viewDidLoad()
		
		self.applyTheme()
		self.presenter.viewDidLoad()
	}
	
	
	// MARK: - Theme
	
	func applyTheme() {
        view.backgroundColor = .systemBackground
	}
	
	
	// MARK: - Actions
	
	
}

extension FavoriteMoviesView: FavoriteMoviesViewInterface {
	
}
