//
//  MovieListBuilder.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.

import UIKit

enum MovieListBuilder: BaseSceneBuilder {
	
	typealias Config = MovieList.Configuration
	
	typealias SceneView = MovieListViewController
	
	static func build(with configuration: Config) -> SceneView {
		
		let viewModel = viewModelBuilder(configuration: configuration)
		let router = MovieListRouter()
		let viewController = SceneView(viewModel: viewModel, router: router)
		
		router.viewController = viewController
		
		return viewController
	}
	
	private static func viewModelBuilder(configuration: Config) -> some ViewModel<MovieList.State, MovieList.Action> {
		MovieListViewModel(configuration: configuration)
	}
	
}
