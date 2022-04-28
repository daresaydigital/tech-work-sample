//
//  WatchListMoviesView.swift
//  WatchListMovies
//
//  Created by mohannazakizadeh on 4/28/22.
//

import UIKit

final class WatchListMoviesView: UIViewController, ViewInterface {
	
	var presenter: WatchListMoviesPresenterViewInterface!
	
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
		
	}
	
	
	// MARK: - Actions
	
	
}

extension WatchListMoviesView: WatchListMoviesViewInterface {
	
}
