//
//  MovieListViewModelProtocol.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import Foundation

protocol MovieListViewModelProtocol: AnyObject {
    var viewController: MovieListViewControllerProtocol? { get set }
    func loadViewInitialData()
    func moviesCount() -> Int
    func movieInfoModel(at index: Int) -> MovieInfoModel?
    func checkAndHandleIfPaginationRequired(at row: Int)
}

class DummyMovieListViewModel: MovieListViewModelProtocol {
    weak var viewController: MovieListViewControllerProtocol?
    
    init() {
        // Does nothing
    }
    
    func loadViewInitialData() {
        // Does nothing
    }
    
    func moviesCount() -> Int {
        return 0
    }
    
    func movieInfoModel(at index: Int) -> MovieInfoModel? {
        return nil
    }
    
    func checkAndHandleIfPaginationRequired(at row: Int) {
        // Does nothing
    }
}
