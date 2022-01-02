//
//  PopularViewModel.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import Foundation

// MARK:- DataModel
class PopularViewDataModel {
    var movieList: [MovieInfoModel]
    var currentPageNumber: Int
    var totalPages: Int
    
    init() {
        movieList = []
        currentPageNumber = 0
        totalPages = 100 // default upper limit
    }
}

// MARK:- ViewModel
public class PopularViewModel: MovieListViewModelProtocol {
    weak var viewController: MovieListViewControllerProtocol?
    private var movieListType: MovieListType
    private var isLoading: Bool
    private let dataModel: PopularViewDataModel
    private lazy var networkManager: NetworkManager = {
        return NetworkManager()
    }()
    
    init(_ movieListType: MovieListType) {
        self.movieListType = movieListType
        dataModel = PopularViewDataModel()
        isLoading = true
    }

    func fetchPopularData() {
        var urlRouter: URLRouter {
            switch movieListType {
            case .pupuar:
                return .popular(1)
            case .topRated:
                return .topRated(1)
            }
        }
        networkManager.fetchPopular(urlRouter: urlRouter) { (popularResponseModel) in
            DispatchQueue.main.async { [weak self] in
                guard let self = self else { return }
                guard let popularModel = popularResponseModel else {
                    return
                }
                self.handlePopularResult(popularModel: popularModel)
            }
        }
    }

    func fetchNextPagePopularData() {
        var urlRouter: URLRouter {
            switch movieListType {
            case .pupuar:
                return .popular(dataModel.currentPageNumber + 1)
            case .topRated:
                return .topRated(dataModel.currentPageNumber + 1)
            }
        }
        networkManager.fetchPopular(urlRouter: urlRouter) { (popularResponseModel) in
            DispatchQueue.main.async { [weak self] in
                guard let self = self else { return }
                guard let popularModel = popularResponseModel else {
                    self.isLoading = false
                    return
                }
                self.handlePopularResult(popularModel: popularModel)
            }
        }
    }

    func handlePopularResult(popularModel: PopularResponseModel) {
        handlePageDetails(popularModel: popularModel)
        addMovieInfoModelToMovieList(popularModel.results)
        
        updateView()
    }
    
    func handlePageDetails(popularModel: PopularResponseModel) {
        updateLastFetchedPageNumber(popularModel)
    }
    
    func addMovieInfoModelToMovieList(_ modelList: [MovieInfoModel]) {
        for movieInfoModel in modelList {
            dataModel.movieList.append(movieInfoModel)
        }
    }
    
    func updateLastFetchedPageNumber(_ popularModel: PopularResponseModel) {
        dataModel.currentPageNumber = popularModel.page
        dataModel.totalPages = popularModel.totalPages
        print("\(dataModel.currentPageNumber) out of \(dataModel.totalPages)")
    }
    
    func updateView() {
        isLoading = false
        viewController?.updateView()
    }
    
    // MARK: MovieListViewModelProtocol
    func didTap() {
        // Does nothing
    }
    
    func loadViewInitialData() {
        fetchPopularData()
    }
    
    func moviesCount() -> Int {
        return dataModel.movieList.count
    }
    
    func movieInfoModel(at index: Int) -> MovieInfoModel? {
        return dataModel.movieList[index]
    }
}

// MARK:- Pagination
extension PopularViewModel {
    func checkAndHandleIfPaginationRequired(at row: Int) {
        if (row + 1 == dataModel.movieList.count) && (dataModel.currentPageNumber != dataModel.totalPages) {
            handlePaginationRequired()
        }
    }
    
    func handlePaginationRequired() {
        if !isLoading && dataModel.currentPageNumber != 0 {
            isLoading = true
            fetchNextPagePopularData()
        }
    }
}
