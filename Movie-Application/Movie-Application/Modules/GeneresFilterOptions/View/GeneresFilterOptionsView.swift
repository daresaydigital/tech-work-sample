//
//  GeneresFilterOptionsView.swift
//  GeneresFilterOptions
//
//  Created by mohannazakizadeh on 4/26/22.
//

import UIKit

final class GeneresFilterOptionsView: UIViewController, ViewInterface {
	
	var presenter: GeneresFilterOptionsPresenterViewInterface!
	
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

extension GeneresFilterOptionsView: GeneresFilterOptionsViewInterface {
	
}
