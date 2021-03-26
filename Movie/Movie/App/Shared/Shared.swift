//
//  Shared.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-25.
//

import Foundation

public typealias Id = Int
public typealias Locale = String

public func extractSuccess<T>(_ result: Result<T, Error>) -> T? {
    if case let .success(value) = result {
        return value
    } else {
        return nil
    }
}

public func extractFailure<T>(_ result: Result<T, Error>) -> Error? {
    if case let .failure(error) = result {
        return error
    } else {
        return nil
    }
}
