//
//  MovieDetailViewModel.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.

import Combine
import Foundation

final class MovieDetailViewModel: SubjectedViewModel {
    
    typealias State = MovieDetail.State
    
    typealias Action = MovieDetail.Action
    
    let stateSubject: CurrentValueSubject<State, Never>
    var cancellables = Set<AnyCancellable>()
    private var movieID: Int
    let networkManager: MovieNetworkAPI

    init(configuration: MovieDetail.Configuration) {
        stateSubject = .init(.init())
        movieID = configuration.movieID
        networkManager = configuration.movieNetworkAPIManager
    }
    
    func handle(action: Action) {
        switch action {
        case .fetchDetail:
            fetchDetail()
        case .fetchReviews:
            fetchReviews()
        }
    }
    
    private func fetchDetail() {
        networkManager.getMovieDetail(movieID: "\(self.movieID)").sinkToResult { [weak self] result in
            guard let self else { return }
            switch result {
            case .success(let movie):
                self.stateSubject.value.update({
                    $0.movie = movie
                })
            case .failure(let error):
                self.stateSubject.value.update({
                    $0.error = error
                })
            }
        }.store(in: &cancellables)
    }
    
    private func fetchReviews() {
        networkManager.getMovieReviews(movieID: "\(self.movieID)").sinkToResult { [weak self] result in
            guard let self else { return }
            switch result {
            case .success(let list):
                self.stateSubject.value.update({
                    $0.reviewList = list.results
                })
            case .failure(let error):
                self.stateSubject.value.update({
                    $0.error = error
                })
            }
        }.store(in: &cancellables)
    }
}
