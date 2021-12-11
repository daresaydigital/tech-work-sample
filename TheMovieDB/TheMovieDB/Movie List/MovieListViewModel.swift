//
//  MovieListViewModel.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import Foundation
import UIKit

enum LoadingState {
    case loading
    case loaded
    case failed(TMSError)
}

class MoviesListViewModel {
 
    var onShouldReloadTableView: (() -> Void)?
    var onLoadingStateShouldChange: ((LoadingState) -> Void)?

    typealias PopularInfo = (nextPage: Int, totalPage: Int)
    typealias TopRatedInfo = (nextPage: Int, totalPage: Int)
    private var listConfig: (popular: PopularInfo, topRated: TopRatedInfo)
    
    var listType: MovieListType

    var popularMoviesArray: [Movie] = [] {
        didSet {
            onShouldReloadTableView?()
        }
    }
    var topRatedMoviesArray: [Movie] = [] {
        didSet {
            onShouldReloadTableView?()
        }
    }

    private var movieListService : MovieListAPIService!
    
    init(movieListServices: MovieListAPIService) {
        self.movieListService = movieListServices
        // Defaults
        listConfig = ((1,1), (1,1))
        self.listType = .popular
    }
    
    private func didSuccessfullyReceiveResponse(response: MoviesResponse) {
        
        switch listType {
        case .popular:
            self.listConfig.popular.nextPage = self.listConfig.popular.nextPage + 1
            self.listConfig.popular.totalPage = response.totalPages
            self.popularMoviesArray.append(contentsOf: response.results)
        case .topRated:
            self.listConfig.topRated.nextPage = self.listConfig.topRated.nextPage + 1
            self.listConfig.topRated.totalPage = response.totalPages
            self.topRatedMoviesArray.append(contentsOf: response.results)
        }
    }

    private func fetchMovieList() {
        
        onLoadingStateShouldChange?(.loading)

        let pageNumber = (listType == .popular) ? listConfig.popular.nextPage : listConfig.topRated.nextPage
        movieListService.getMoviesList(for: listType, pageNumber: pageNumber ) { [weak self] result in
            
            switch result {
            case .success(let response):
                self?.didSuccessfullyReceiveResponse(response: response)
                self?.onLoadingStateShouldChange?(.loaded)
            case .failure(let error):
                self?.onLoadingStateShouldChange?(.failed(error))
            }
        }
    }
    
    private func shouldLoadMorePages() -> Bool {
        
        return (listType == .popular) ?
            listConfig.popular.nextPage <= listConfig.popular.totalPage:
            listConfig.topRated.nextPage <= listConfig.topRated.totalPage
    }
}

// MARK: - View Actions

extension MoviesListViewModel {
    
    func viewDidLoad(){
        fetchMovieList()
    }
    
    func movieSegmentDidChange(selectedSegmentIndex: Int) {
        
        switch selectedSegmentIndex
        {
        case 0:
            self.listType = .popular
            guard self.popularMoviesArray.count == 0 else {
                onShouldReloadTableView?()
                return
            }
        case 1:
            self.listType = .topRated
            guard self.topRatedMoviesArray.count == 0 else {
                onShouldReloadTableView?()
                return
            }
        default: ()
        }
        
        fetchMovieList()
    }
    
    func getCellData(for indexPath: IndexPath) -> Movie? {
        
        switch self.listType
        {
        case .popular:
            return self.popularMoviesArray[indexPath.row]
        case .topRated:
            return self.topRatedMoviesArray[indexPath.row]
        }
    }
    
    var numberOfRows: Int {
        
        switch self.listType
        {
        case .popular:
            return self.popularMoviesArray.count
        case .topRated:
            return self.topRatedMoviesArray.count
        }
    }
    
    func listReachedBottom(){
        guard shouldLoadMorePages() else { return }
    
        fetchMovieList()
    }

}
