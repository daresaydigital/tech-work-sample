//
//  TabBarView.swift
//  TabBar
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

final class TabBarView: UIViewController, ViewInterface {
	
	var presenter: TabBarPresenterViewInterface!
	
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

extension TabBarView: TabBarViewInterface {
	
}
