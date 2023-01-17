//
//  TopRatedListViewModel.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/11/23.
//

import UIKit

class TopRatedListViewModel {

    // MARK: - Properties

    private var topRated: [TrendingViewModel] = []

    private let favoriteRepository: FavoriteRepository
    private let apiLoader: APILoader<TopRatedAPI>

    var numberOfRowsInSection: Int {
        self.topRated.count
    }

    var titlePage: String = "Top Rated"

    // MARK: - Initializer

    init(apiLoader: APILoader<TopRatedAPI>, favoriteRepository: FavoriteRepository = FavoriteRepository()) {
        self.apiLoader = apiLoader
        self.favoriteRepository = favoriteRepository
    }

    // MARK: - Functions

    func fetchTopRated(
        completion: @escaping (TopRatedListViewModel?, ServiceError?) -> ()
    ) {
        apiLoader.loadAPIRequest(requestData: nil) { [weak self] topRatedResponse, error in
            guard let self = self else {
                return
            }

            if let _ = error {
                DispatchQueue.main.async {
                    completion(nil, error)
                }
            } else if let topRatedResponse = topRatedResponse {
                self.topRated = topRatedResponse.results?.map({ topRated in
                    TrendingViewModel(trending: topRated)
                }) ?? []

                DispatchQueue.main.async {
                    completion(self, nil)
                }
            } else {
                DispatchQueue.main.async {
                    completion(nil, ServiceError(message: "Service Error: Unknow Error"))
                }
            }
        }
    }

    func getTrending(_ index: Int) -> TrendingViewModel {
        return self.topRated[index]
    }

    func insertFavorite(for movieId: Int64) {
        guard let trending = self.topRated.first(where: { $0.movieId == movieId }) else { return }
        favoriteRepository.insetOrUpdateMovie(for: movieId, trending)
    }
}
