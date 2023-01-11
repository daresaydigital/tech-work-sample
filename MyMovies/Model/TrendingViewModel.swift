//
//  TrendingViewModel.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import Foundation

class TrendingViewModel {
    private var trending: TrendingResult

    var posterImageURL: String {
        APIPath().fetchImage(width: "w154", imagePath: trending.posterPath ?? "")
    }

    init(trending: TrendingResult) {
        self.trending = trending
    }
}
