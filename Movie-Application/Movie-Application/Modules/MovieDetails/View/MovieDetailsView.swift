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
	}
	
	
	// MARK: - Theme
	
	func applyTheme() {
        view.backgroundColor = .systemBackground
	}
	
}

extension MovieDetailsView: MovieDetailsViewInterface {
	
}
