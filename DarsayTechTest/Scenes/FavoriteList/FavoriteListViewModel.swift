//
//  FavoriteListViewModel.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/9/1401 AP.
//  Copyright (c) 1401 AP ___ORGANIZATIONNAME___. All rights reserved.
//
//  This file was generated based on the Clean Swift and MVVM Architecture
//

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
            $0.favoriteList = FavoriteStorage.currentList
        })
    }
}
