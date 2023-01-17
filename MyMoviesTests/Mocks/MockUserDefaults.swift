//
//  MockUserDefaults.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/16/23.
//

import Foundation
@testable import MyMovies

class MockUserDefaults: UserDefaults {

    var persisted: [String : Any] = [:]

    override func set(_ value: Any?, forKey key: String) {
        persisted[key] = value
    }

    override func value(forKey key: String) -> Any? {
        return persisted[key] as Any?
    }

    override func removeObject(forKey defaultName: String) {
        persisted[defaultName] = nil
    }
}
