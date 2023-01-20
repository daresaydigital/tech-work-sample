//
//  MoviesViewModel.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 15.01.2023.
//

import Foundation

protocol MoviesViewModelProtocol: AnyObject {
    func setMoviesData(movieModelList: [MovieModel])
    func reloadCollectionView()
}

final class MoviesViewModel {
    
    private enum SegmentName: Int {
        case topRated
        case mostPopular
        
        var value: String {
            switch self {
            case .topRated:
                return "top_rated"
            case .mostPopular:
                return "popular"
            }
        }
    }
    
    weak var delegate: MoviesViewModelProtocol?
    
    private var topRatedMoviesPage = 1
    private var mostPopularMoviesPage = 1
    private var currentSegment: SegmentName = .topRated

    private(set) var topRatedMovieModelList: [MovieModel] = [] {
        didSet {
            showMovies()
        }
    }
    
    private(set) var mostPopularMovieModelList: [MovieModel] = [] {
        didSet {
            showMovies()
        }
    }
    
    var movieModelListCount: Int {
        get {
            switch currentSegment {
            case .topRated:
                return topRatedMovieModelList.count
            case .mostPopular:
                return mostPopularMovieModelList.count
            }
        }
    }
    
    func getData() {
        fetchTopRatedMovies()
        fetchMostPopularMovies()
    }
    
    func showMovies() {
        switch currentSegment {
        case .topRated:
            self.delegate?.setMoviesData(movieModelList: topRatedMovieModelList)
        case .mostPopular:
            self.delegate?.setMoviesData(movieModelList: mostPopularMovieModelList)
        }
        self.delegate?.reloadCollectionView()
    }
}


// MARK: - MoviesViewModel new movies
extension MoviesViewModel {
    
    func appendNewMovies() {
        switch currentSegment {
        case .topRated:
            appendNewTopRatedMovies()
        case .mostPopular:
            appendNewMostPopularMovies()
        }
    }
    
    private func appendNewTopRatedMovies() {
        topRatedMoviesPage += 1
        fetchTopRatedMovies()
    }
    
    private func appendNewMostPopularMovies() {
        mostPopularMoviesPage += 1
        fetchMostPopularMovies()
    }
}


// MARK: - MoviesViewModel network functions
extension MoviesViewModel {
    
    private func fetchTopRatedMovies() {
        guard let movieTypeUrl = URL(string: [
            Constant.Api.Url.baseMovieUrl,
            Constant.Api.Url.pathMovie,
            SegmentName.topRated.value].joined(separator: "/")),
              var urlComponents = URLComponents(url: movieTypeUrl, resolvingAgainstBaseURL: false) else {
            return
        }
        var urlQueryItem = [URLQueryItem(name: Constant.Api.KeyWord.apiKey, value: Constant.Api.key)]
        urlQueryItem.append(URLQueryItem(name: Constant.Api.KeyWord.page, value: String(describing: topRatedMoviesPage)))
        urlComponents.queryItems = urlQueryItem
        guard let url = urlComponents.url else { return }
        
        url.fetchJsonData { [weak self] (result: Result<MoviesModel, APIError>) in
            switch result {
            case .success(let res):
                self?.topRatedMovieModelList.append(contentsOf: res.results)
            case .failure(let error):
                print(error)
            }
        }
    }
    
    private func fetchMostPopularMovies() {
        guard let movieTypeUrl = URL(string: [
            Constant.Api.Url.baseMovieUrl,
            Constant.Api.Url.pathMovie,
            SegmentName.mostPopular.value].joined(separator: "/")),
              var urlComponents = URLComponents(url: movieTypeUrl, resolvingAgainstBaseURL: false) else {
            return
        }
        var urlQueryItem = [URLQueryItem(name: Constant.Api.KeyWord.apiKey, value: Constant.Api.key)]
        urlQueryItem.append(URLQueryItem(name: Constant.Api.KeyWord.page, value: String(describing: mostPopularMoviesPage)))
        urlComponents.queryItems = urlQueryItem
        guard let url = urlComponents.url else { return }
        
        url.fetchJsonData { [weak self] (result: Result<MoviesModel, APIError>) in
            switch result {
            case .success(let res):
                self?.mostPopularMovieModelList.append(contentsOf: res.results)
            case .failure(let error):
                print(error)
            }
        }
    }
}


// MARK: - MoviesViewModel segment operations
extension MoviesViewModel {
    
    func setSegmentChange(_ segmentIndex: Int) {
        switch segmentIndex {
        case SegmentName.topRated.rawValue:
            currentSegment = .topRated
        case SegmentName.mostPopular.rawValue:
            currentSegment = .mostPopular
        default:
            return
        }
    }
}
