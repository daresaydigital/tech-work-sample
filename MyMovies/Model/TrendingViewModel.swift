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
        self.trending.originalTitle ?? self.trending.name ?? ""
    }

    var posterImageURL: URL? {
        URL(string: APIPath().fetchImage(width: "w154", imagePath: trending.posterPath ?? ""))
    }

    var movieId: Int64? {
        self.trending.id
    }

    init(trending: TrendingResult) {
        self.trending = trending
    }

    func getTrendingResult() -> TrendingResult {
        return trending
    }
}
