//
//  FavoriteListBuilder.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/9/1401 AP.

import UIKit

enum FavoriteListBuilder: BaseSceneBuilder {
	
	typealias Config = FavoriteList.Configuration
	
	typealias SceneView = FavoriteListViewController
	
	static func build(with configuration: Config) -> SceneView {
		
		let viewModel = viewModelBuilder(configuration: configuration)
		let router = FavoriteListRouter()
		let viewController = SceneView(viewModel: viewModel, router: router)
		
		router.viewController = viewController
		
		return viewController
	}
	
	private static func viewModelBuilder(configuration: Config) -> some ViewModel<FavoriteList.State, FavoriteList.Action> {
		FavoriteListViewModel(configuration: configuration)
	}
	
}
