//
//  TopRatedMoviesView.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

final class TopRatedMoviesView: UIViewController, ViewInterface {
	
	var presenter: TopRatedMoviesPresenterViewInterface!
	
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
        view.backgroundColor = .yellow
	}
	
	
	// MARK: - Actions
	
	
}

extension TopRatedMoviesView: TopRatedMoviesViewInterface {
	
}
