//
//  Store.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-25.
//

import Foundation
import RxSwift
/*
 Something like this https://github.com/NoTests/RxFeedback.swift could be used, however kept this implementation for simplicity,
 */

protocol Redux {
    associatedtype State: Equatable
    associatedtype Action
    static func reduce(state: State, action: Action) -> State
}

class Store<State, Action> {

    let state: Observable<State>

    private let actions = PublishSubject<Action>()

    init(initialState: State, reducer: @escaping (State, Action) -> State) {
        state = actions
            .scan(initialState, accumulator: reducer)
            .startWith(initialState)
            .share(replay: 1)
    }
}

extension Store: ObserverType {

    // swiftlint:disable:next type_name
    typealias E = Action

    func on(_ event: Event<E>) {
        if let element = event.element {
            actions.onNext(element)
        }
    }
}
