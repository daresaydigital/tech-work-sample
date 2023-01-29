//
//  FavoriteListViewModel.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/9/1401 AP.

import Combine
import Foundation

class FavoriteListViewModel: SubjectedViewModel {
    
    typealias State = FavoriteList.State
    
    typealias Action = FavoriteList.Action
    
    let stateSubject: CurrentValueSubject<State, Never>
    
    init(configuration: FavoriteList.Configuration) {
        stateSubject = .init(.init())
    }
    
    func handle(action: Action) {
        switch action {
        case .fetchFavoriteMovies:
            fetchFavoriteMovies()
        }
    }
    
    private func fetchFavoriteMovies() {
        
        self.stateSubject.value.update({
            $0.favoriteList = FavoriteStorage.shared.currentList
        })
    }
}
