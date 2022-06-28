//
//  MoviesViewModel.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import Foundation

protocol MoviesViewModelDelegate: AnyObject {
    func populate(displayState: DisplayState<[MoviesModel]>)
}

final class MoviesViewModel {
    
    // MARK: - Variables
    private var moviesService: MoviesServiceProtocol
    
    public weak var delegate: MoviesViewModelDelegate?
    
    public var isFinished = false
    
    private var currentPage: UInt = 1
    private var allMovies: [MoviesModel] = []
    private var configCache: ConfigurationModel?
    
    public var totalCount: Int = 0
    
    public var itemsCount: Int {
        return allMovies.count
    }
    
    private let dispatchGroup: DispatchGroup = DispatchGroup()
    
    // MARK: - Init
    init(moviesService: MoviesServiceProtocol) {
        self.moviesService = moviesService
    }
    
    // MARK: - Public methods
    public func populate() {
        
        delegate?.populate(displayState: .loading)
        
        dispatchGroup.enter()
        getPopularMovies()
        
        dispatchGroup.enter()
        getConfigs()
        
        dispatchGroup.notify(queue: .main) { [weak self] in
            guard let self = self else { return }
            
            if let _ = self.configCache, !self.allMovies.isEmpty {
                self.delegate?.populate(displayState: .success(self.allMovies))
            } else {
                self.delegate?.populate(displayState: .failure("Couldn't fetch data."))
            }
        }
    }
    
    public func item(at index: Int) -> MoviesModel {
        return allMovies[index]
    }
    
    public func isLoadingCell(for indexPath: IndexPath) -> Bool {
        return indexPath.row == itemsCount - 1
    }
    
    // MARK: - Helpers
    private func getPopularMovies() {
        
        let httpRequest = ServerRequest.Movies.getMovies(page: currentPage)
        moviesService.getMovies(httpRequest: httpRequest) { [weak self]  result in
            defer { self?.dispatchGroup.leave() }
            
            guard let self = self else { return }
            
            switch result {
            case .success(let response):
                self.currentPage += 1
                
                guard let movies = response.results else { return }
                
                if movies.isEmpty {
                    self.isFinished = true
                }
                
                self.allMovies.append(contentsOf: movies)
            case .failure(let error):
                print(error)
            }
        }
    }
    
    private func getConfigs() {
        
        let httpRequest = ServerRequest.Configuration.getConfigs()
        moviesService.getConfigs(httpRequest: httpRequest) { [weak self] result in
            defer { self?.dispatchGroup.leave() }
            
            guard let self = self else { return }
            
            switch result {
            case .success(let response):
                print(response)
                self.configCache = response
                UserDefaultsData.configModel = response
            case .failure(let error):
                print(error)
            }
        }
    }
}
