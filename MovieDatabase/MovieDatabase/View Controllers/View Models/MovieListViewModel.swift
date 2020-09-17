//
//  MovieListViewModel.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

protocol MovieListViewModelDelegate: AnyObject {
    func fetchSucceded(with newIndexPaths: [IndexPath]?)
    func fetchFailed(with error: MovieDatabaseNetworkError)
}

final class MovieListViewModel {
    private weak var delegate: MovieListViewModelDelegate?

    private var topRatedMovies: [Movie] = []
    private var currentTopRatedMoviesPage = 1
    private var mostPopularMovies: [Movie] = []
    private var currentMostPopularMoviesPage = 1
    private var totalNumberOfTopRatedMovies = 0
    private var totalNumberOfMostPopularMovies = 0
    private var isFetching = false
    private var switchedLists = false

    var topRatedContentOffset: CGFloat = 0
    var mostPopularContentOffset: CGFloat = 0

    private var currentlyDisplayedListType: ListType = .topRated

    private enum ListType: Int {
        case topRated
        case mostPopular
    }

    var totalNumberOfMoviesForTheSelectedList: Int {
        switch currentlyDisplayedListType {
        case .topRated:
            return totalNumberOfTopRatedMovies
        case .mostPopular:
            return totalNumberOfMostPopularMovies
        }
    }

    var numberOfCurrentlyLoadedMovies: Int {
        switch currentlyDisplayedListType {
        case .topRated:
            return topRatedMovies.count
        case .mostPopular:
            return mostPopularMovies.count
        }
    }

    private let networkService = NetworkService()

    init(delegate: MovieListViewModelDelegate) {
        self.delegate = delegate
    }

    func movie(at index: Int) -> Movie {
        switch currentlyDisplayedListType {
        case .topRated:
            return topRatedMovies[index]
        case .mostPopular:
            return mostPopularMovies[index]
        }
    }

    func switchList(to index: Int) {
        //Check if there's a valid list type for the selected index and the selected list is different than the one being displayed to avoid redundant fetching
        guard let selectedListType = ListType(rawValue: index), selectedListType.rawValue != currentlyDisplayedListType.rawValue else { return }

        switch selectedListType {
        case .topRated:
            fetchTopRatedMovies()
            currentlyDisplayedListType = .topRated
            switchedLists = true
        case .mostPopular:
            fetchMostPopularMovies()
            currentlyDisplayedListType = .mostPopular
            switchedLists = true
        }
    }

    func fetchMovies(isRefresh: Bool = false) {
        switch currentlyDisplayedListType {
        case .topRated:
            fetchTopRatedMovies(isRefresh: isRefresh)
        case .mostPopular:
            fetchMostPopularMovies(isRefresh: isRefresh)
        }
    }

    private func fetchTopRatedMovies(isRefresh: Bool = false) {
        guard !isFetching else { return }

        isFetching = true

        networkService.fetchTopRatedMovies(page: currentTopRatedMoviesPage) { (result) in
            switch result {
            case .failure(let error):
                DispatchQueue.main.async {
                    self.isFetching = false
                    self.delegate?.fetchFailed(with: error)
                }
            case .success(let response):
                DispatchQueue.main.async {
                    self.isFetching = false
                    if isRefresh {
                        self.currentTopRatedMoviesPage = 1
                    } else {
                         self.currentTopRatedMoviesPage += 1
                    }
                    self.totalNumberOfTopRatedMovies = response.totalResults
                    self.topRatedMovies.append(contentsOf: response.results)

                    if response.page == 1 {
                        self.delegate?.fetchSucceded(with: nil)
                    } else {
                        let recentlyFetchedIndexPaths = self.indexPathsToReload(from: response.results, existingElements: self.topRatedMovies)
                        self.delegate?.fetchSucceded(with: recentlyFetchedIndexPaths)
                    }
                }
            }
        }
    }

    private func fetchMostPopularMovies(isRefresh: Bool = false) {
        guard !isFetching else { return }

        isFetching = true

        networkService.fetchMostPopularMovies(page: currentMostPopularMoviesPage) { (result) in
            switch result {
            case .failure(let error):
                DispatchQueue.main.async {
                    self.isFetching = false
                    self.delegate?.fetchFailed(with: error)
                }
            case .success(let response):
                DispatchQueue.main.async {
                    self.isFetching = false
                    if isRefresh {
                        self.currentMostPopularMoviesPage = 1
                    } else {
                        self.currentMostPopularMoviesPage += 1
                    }
                    self.totalNumberOfMostPopularMovies = response.totalResults
                    self.mostPopularMovies.append(contentsOf: response.results)

                    if response.page == 1 {
                        self.delegate?.fetchSucceded(with: nil)
                    } else {
                        let recentlyFetchedIndexPaths = self.indexPathsToReload(from: response.results, existingElements: self.mostPopularMovies)
                        self.delegate?.fetchSucceded(with: recentlyFetchedIndexPaths)
                    }
                }
            }
        }
    }

    private func indexPathsToReload(from newElements: [Movie], existingElements: [Movie]) -> [IndexPath] {
        let startIndex = existingElements.count - newElements.count
        let endIndex = startIndex + newElements.count
        return (startIndex..<endIndex).map { IndexPath(row: $0, section: 0) }
    }
}
