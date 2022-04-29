//
//  MovieDetailsView.swift
//  MovieDetails
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import UIKit

final class MovieDetailsView: BottomSheetContainerViewController
<MovieInfoContentView, MovieDetailsInfoViewController>, ViewInterface {
	
	var presenter: MovieDetailsPresenterViewInterface!
	
    
    // MARK: - Lifecycle
	
	override func viewDidLoad() {
		super.viewDidLoad()
        
		self.applyTheme()
		self.presenter.viewDidLoad()
	}
	
	
	// MARK: - Theme
	
	func applyTheme() {
		
	}
	
	//MARK: - Private functions
    
    
	// MARK: - Actions
	
	
}

extension MovieDetailsView: MovieDetailsViewInterface {
	
}
