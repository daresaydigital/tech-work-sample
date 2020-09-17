//
//  MovieReviewsViewModel.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import Foundation

protocol MovieReviewsViewModelDelegate: AnyObject {
    func fetchSucceded(with newIndexPaths: [IndexPath]?)
    func fetchFailed(with errorString: MovieReviewsError)
}

final class MovieReviewsViewModel {
    private weak var delegate: MovieReviewsViewModelDelegate?

    private var reviews: [Review] = []
    private var page = 1
    private var totalNumberOfReviews = 0
    private var isFetching = false

    private var id: Int!
    private var name: String!

    var totalCountReviews: Int {
        return totalNumberOfReviews
    }

    var numberOfCurrentlyLoadedReviews: Int {
        return reviews.count
    }

    var movieTitle: String {
        return name
    }

    private let networkService = NetworkService()

    init(id: Int, delegate: MovieReviewsViewModelDelegate) {
        self.id = id
        self.delegate = delegate
    }

    func review(at index: Int) -> Review {
        return reviews[index]
    }

    func fetchMovieReviews(isRefresh: Bool = false) {
        guard !isFetching else { return }

        isFetching = true

        networkService.fetchReviews(for: id, page: page) { (result) in
            switch result {
            case .success(let response):
                DispatchQueue.main.async {
                    self.isFetching = false
                    if isRefresh {
                        self.page = 1
                    } else {
                        self.page += 1
                    }
                    self.totalNumberOfReviews = response.totalResults
                    self.reviews.append(contentsOf: response.results)

                    guard self.reviews.count > 0 else {
                        self.delegate?.fetchFailed(with: .noReviews)
                        return
                    }

                    if response.page == 1 {
                        self.delegate?.fetchSucceded(with: nil)
                    } else {
                        let recentlyFetchedIndexPaths = self.indexPathsToReload(from: response.results, existingElements: self.reviews)
                        self.delegate?.fetchSucceded(with: recentlyFetchedIndexPaths)
                    }
                }
            case .failure(let error):
                DispatchQueue.main.async {
                    self.isFetching = false
                    self.delegate?.fetchFailed(with: error)
                }
            }
        }
    }

    private func indexPathsToReload(from newElements: [Review], existingElements: [Review]) -> [IndexPath] {
      let startIndex = existingElements.count - newElements.count
      let endIndex = startIndex + newElements.count
      return (startIndex..<endIndex).map { IndexPath(row: $0, section: 0) }
    }
}
