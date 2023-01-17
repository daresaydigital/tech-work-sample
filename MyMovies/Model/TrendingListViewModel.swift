//
//  TrendingListViewModel.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class TrendingListViewModel {

    // MARK: - Properties

    private var trendings: [TrendingViewModel] = []

    private let favoriteRepository: FavoriteRepository
    private let apiLoader: APILoader<TrendingAPI>

    var numberOfRowsInSection: Int {
        self.trendings.count
    }

    var titlePage: String = "Trending"

    // MARK: - Initializer

    init(apiLoader: APILoader<TrendingAPI>, favoriteRepository: FavoriteRepository = FavoriteRepository()) {
        self.apiLoader = apiLoader
        self.favoriteRepository = favoriteRepository
    }

    // MARK: - Functions

    func fetchTrendings(
        of param: TrendingParams,
        completion: @escaping (TrendingListViewModel?, ServiceError?) -> ()
    ) {
        apiLoader.loadAPIRequest(requestData: param) { [weak self] trendingResponse, error in
            guard let self = self else {
                return
            }

            if let _ = error {
                DispatchQueue.main.async {
                    completion(nil, error)
                }
            } else if let trendingResponse = trendingResponse {
                self.trendings = trendingResponse.results?.map({ trending in
                    TrendingViewModel(trending: trending)
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
        return self.trendings[index]
    }

    func insertFavorite(for movieId: Int64) {
        guard let trending = self.trendings.first(where: { $0.movieId == movieId }) else { return }
        favoriteRepository.insetOrUpdateMovie(for: movieId, trending)
    }
}
