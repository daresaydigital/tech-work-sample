//
//  MovieDetailBuilder.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.

import UIKit

enum MovieDetailBuilder: BaseSceneBuilder {
	
	typealias Config = MovieDetail.Configuration
	
	typealias SceneView = MovieDetailViewController
	
	static func build(with configuration: Config) -> SceneView {
		
		let viewModel = viewModelBuilder(configuration: configuration)
		let router = MovieDetailRouter()
		let viewController = SceneView(viewModel: viewModel, router: router)
		
		router.viewController = viewController
		
		return viewController
	}
	
	private static func viewModelBuilder(configuration: Config) -> some ViewModel<MovieDetail.State, MovieDetail.Action> {
		MovieDetailViewModel(configuration: configuration)
	}
	
}
