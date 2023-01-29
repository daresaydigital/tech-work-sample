//
//  XCTest+Extension.swift
//  DarsayTechTestTests
//
//  Created by Farzaneh on 11/9/1401 AP.
//

import Foundation
import Combine
import XCTest
@testable import DarsayTechTest

extension XCTestCase {
    func awaitPublisher<T: Publisher>(
        _ publisher: T,
        timeout: TimeInterval = 10,
        message: String? = nil,
        function: StaticString = #function,
        file: StaticString = #file,
        line: UInt = #line
    ) throws -> T.Output {
        let message = message ?? "Awaited publisher did not produce any output for \(function)"
        
        // This time, we use Swift's Result type to keep track
        // of the result of our Combine pipeline:
        var result: Result<T.Output, Error>?
        let expectation = self.expectation(description: "Awaiting publisher \(function)")

        let cancellable = publisher.sink(
            receiveCompletion: { completion in
                switch completion {
                case .failure(let error):
                    result = .failure(error)
                case .finished:
                    break
                }

                expectation.fulfill()
            },
            receiveValue: { value in
                result = .success(value)
            }
        )

        // Just like before, we await the expectation that we
        // created at the top of our test, and once done, we
        // also cancel our cancellable to avoid getting any
        // unused variable warnings:
        waitForExpectations(timeout: timeout)
        cancellable.cancel()

        // Here we pass the original file and line number that
        // our utility was called at, to tell XCTest to report
        // any encountered errors at that original call site:
        let unwrappedResult = try XCTUnwrap(
            result,
            message,
            file: file,
            line: line
        )

        return try unwrappedResult.get()
    }
}


