//
//  MoviesApp.swift
//  Movies
//
//  Created by Richard Segerblom on 2022-12-21.
//

import SwiftUI

@main
struct MoviesApp: App {
    var body: some Scene {
        WindowGroup {
            MovieGrid(viewModel: .init(movieService: MovieService()))
        }
    }
}
