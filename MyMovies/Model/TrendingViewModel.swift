//
//  TrendingViewModel.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import Foundation

class TrendingViewModel {
    private var trending: TrendingResult

    var title: String {
        if let title = trending.originalTitle {
            return title
        } else if let name = trending.name {
            return name
        } else {
            return ""
        }
    }

    var posterImageURL: URL? {
        URL(string: APIPath().fetchImage(width: "w154", imagePath: trending.posterPath ?? ""))
    }

    init(trending: TrendingResult) {
        self.trending = trending
    }
}
