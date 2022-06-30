//
//  MoviesViewModel.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import Foundation

protocol ListViewModelable {
    var totalCount: Int { get set }
    var itemsCount: Int { get }
    var isFinished: Bool { get set }
    
    func item(at index: Int) -> MoviesModel
    func isLoadingCell(for indexPath: IndexPath) -> Bool
    func prefetchData()
}

protocol MoviesViewModelDelegate: AnyObject {
    func populate(displayState: DisplayState<[MoviesModel]>)
    func displayMovies(displayState: DisplayState<[MoviesModel]>)
}

final class MoviesViewModel {
    
    // MARK: - Variables
    private var moviesService: MoviesServiceProtocol
    
    public weak var delegate: MoviesViewModelDelegate?
    
    private var currentPage: UInt = 1
    private var allMovies: [MoviesModel] = []
    private var configCache: ConfigurationModel?
    
    var isFinished = false
    var totalCount: Int = 0
    var itemsCount: Int {
        return allMovies.count
    }
    
    // MARK: - Init
    init(moviesService: MoviesServiceProtocol) {
        self.moviesService = moviesService
    }
    
    // MARK: - Public methods
    public func populate() {
        
        let dispatchGroup = DispatchGroup()
        
        delegate?.populate(displayState: .loading)
        
        dispatchGroup.enter()
        getPopularMovies(dispatchGroup: dispatchGroup)
        
        dispatchGroup.enter()
        getConfigs(dispatchGroup: dispatchGroup)
        
        dispatchGroup.notify(queue: .main) { [weak self] in
            guard let self = self else { return }
            
            if let _ = self.configCache, !self.allMovies.isEmpty {
                self.delegate?.populate(displayState: .success(self.allMovies))
            } else {
                self.delegate?.populate(displayState: .failure("Couldn't fetch data."))
            }
        }
    }
    
    public func getPopularMovies(dispatchGroup: DispatchGroup? = nil) {
        
        let httpRequest = ServerRequest.Movies.getMovies(page: currentPage)
        moviesService.getMovies(httpRequest: httpRequest) { [weak self]  result in
            defer {
                if let dispatchGroup = dispatchGroup {
                    dispatchGroup.leave()
                }
            }
            
            guard let self = self else { return }
            
            switch result {
            case .success(let response):
                guard let movies = response.results else { return }
                
                if movies.isEmpty {
                    self.isFinished = true
                }
                
                self.allMovies.append(contentsOf: movies)
                
                // This delegate should only be called when
                // aren't using a dispatchGroup
                if dispatchGroup == nil {
                    self.delegate?.displayMovies(displayState: .success(self.allMovies))
                }
                
                self.currentPage += 1
                
            case .failure(let error):
                print(error)
            }
        }
    }
    
    // MARK: - Helpers
    private func getConfigs(dispatchGroup: DispatchGroup? = nil) {
        
        let httpRequest = ServerRequest.Configuration.getConfigs()
        moviesService.getConfigs(httpRequest: httpRequest) { [weak self] result in
            defer { dispatchGroup?.leave() }
            
            guard let self = self else { return }
            
            switch result {
            case .success(let response):
                self.configCache = response
                UserDefaultsData.configModel = response
            case .failure(let error):
                print(error)
            }
        }
    }
}

// MARK: - ListViewModelable
extension MoviesViewModel: ListViewModelable {
    func item(at index: Int) -> MoviesModel {
        return allMovies[index]
    }
    
    func isLoadingCell(for indexPath: IndexPath) -> Bool {
        return indexPath.row == itemsCount - 1
    }
    
    func prefetchData() {
        getPopularMovies()
    }
}
