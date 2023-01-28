//
//  BaseProtocols.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation
import Combine
import UIKit

protocol BaseSceneViewController: UIViewController {
    func bind()
    func prepareUI()
}

protocol BaseSceneBuilder {
    
    associatedtype Config
    associatedtype SceneView: BaseSceneViewController
    
    static func build(with configuration: Config) -> SceneView
}

extension BaseSceneBuilder where Config == Void {
    
    static func build() -> SceneView {
        return Self.build(with: ())
    }
}

protocol StateProtocol {
    func updated(_ handler: (inout Self) -> Void) -> Self
}

extension StateProtocol {
    func updated(_ handler: (inout Self) -> Void) -> Self {
        var result = self
        handler(&result)
        return result
    }
    
    mutating func update(_ handler: (inout Self) -> Void) {
        var result = self
        handler(&result)
        self = result
    }
    
    mutating func update<Value>(_ keyPath: WritableKeyPath<Self,Value>, to value: Value) {
        self[keyPath: keyPath] = value
    }
}

protocol ViewModel<State, Action>: AnyObject {
    associatedtype State: StateProtocol
    associatedtype Action
    var state: State { get }
    var statePublisher: AnyPublisher<State, Never> { get }
    func handle(action: Action)
}

protocol SubjectedViewModel<State, Action>: ViewModel {
    var stateSubject: CurrentValueSubject<State, Never> { get }
}

extension SubjectedViewModel {
    var state: State { stateSubject.value }
    var statePublisher: AnyPublisher<State, Never> { stateSubject.eraseToAnyPublisher() }
}
