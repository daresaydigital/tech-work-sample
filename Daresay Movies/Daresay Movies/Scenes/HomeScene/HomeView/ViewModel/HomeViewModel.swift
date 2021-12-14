//
//  HomeViewModel.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import Foundation
import UIKit

class HomeViewModel {
    
    // MARK: - initializer
    private var homeService: HomeServiceProtocol
    init(homeService: HomeServiceProtocol) {
        self.homeService = homeService
    }
    
    // MARK: - Publishers
    var loading: DataCompletion<Bool>?
    
    var list: DataCompletion<MovieArrayModel>?
    var errorHandler: DataCompletion<String>?
    
    // MARK: - Properties
    private var isFinished = false
    var isPaging = false
    private var currentPage = 1
    private var totalPages = 1
    
    private var moviesCache: MovieArrayModel?
    private var allMovies: MovieArrayModel = []
    private var configCache: ConfigModel?
    
    var dispatchApiGroup: DispatchGroup = DispatchGroup()
    
    // MARK: - Popular Movies
    private func getPopularMovies() {
        homeService.getMovies(page: currentPage) { [weak self] result in
            guard let self = self else { return }
            if self.moviesCache == nil {
                self.dispatchApiGroup.leave()
            }
            switch result {
            case .success(let data):
                self.totalPages = data.totalPages ?? 1
                guard var movies = data.results else {
                    assertionFailure("result not found")
                    return
                }
                movies = self.manipulateData(model: movies)
                self.moviesCache = movies
                self.allMovies.append(contentsOf: movies)
                self.list?(self.allMovies)
            case .failure(let error):
                self.errorHandler?(error.localizedDescription)
            }
        }
    }
    
    // MARK: - Configuration
    private func getConfig() {
        
        homeService.getConfiguration { [weak self] result in
            guard let self = self else { return }
            if self.configCache == nil {
                self.dispatchApiGroup.leave()
            }
            switch result {
            case .success(let config):
                self.configCache = config
                UserDefaultData.configModel = config
            case .failure(let error):
                self.errorHandler?(error.localizedDescription)
            }
        }
    }
    
    // MARK: - Initialize Request
    func getMovies() {
        dispatchApiGroup.enter()
        getPopularMovies()
        
        dispatchApiGroup.enter()
        getConfig()
        
        self.loading?(true)
        // Both Apis are called and has response so show data to user
        dispatchApiGroup.notify(queue: .main) { [weak self] in
            guard let self = self else { return }
            self.loading?(false)
            
            guard let _ = self.configCache, let movies = self.moviesCache else {
                self.errorHandler?(RequestError.serverUnavailable.localizedDescription)
                return
            }
            
            self.list?(movies)
        }
    }
    
    // MARK: - Paginate Logic Validator
    func isValidForPaging(scrollView: UIScrollView) -> Bool {
        let currentOffset = scrollView.contentOffset.y
        let maximumOffset = scrollView.contentSize.height - scrollView.frame.size.height
        return (maximumOffset - currentOffset <= 20.0) && !isFinished && !isPaging
    }
    
    func getNextPageMovies() {
        isPaging = true
        self.currentPage += 1
        if self.currentPage == self.totalPages {
            self.isFinished = true
        } else {
            getPopularMovies()
        }
    }
    
    // MARK: - Favourites
    // MARK: Data Manipulation
    // for Offline/Online Favourite List
    private func manipulateData(model: MovieArrayModel) -> MovieArrayModel {
        return model.fetchFavorites(from: UserDefaultData.favoriteList)
    }
    
    func isFaved(_ isFaved: Bool, model: MovieModel) {
        isFaved ? FavoriteMoviesHandler.shared.fave(model) : FavoriteMoviesHandler.shared.unfave(model)
    }
}
