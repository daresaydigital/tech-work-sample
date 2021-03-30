//
//  TestsHelper.swift
//  MovieTests
//
//  Created by Adrian Sergheev on 2021-03-30.
//

import Foundation
import XCTest

enum TestFilenames: String {
    case genre
    case reviews
    case trending
}

enum FailMessage: Error {
    case modelDataShouldNotBeNil
}

func loadJson(_ fileName: String) throws -> Data {
    if let path = Bundle.main.path(forResource: fileName, ofType: "json") {
        let fileUrl = URL(fileURLWithPath: path)
        let data = try Data(contentsOf: fileUrl, options: .mappedIfSafe)
        return data
    } else {
        throw FailMessage.modelDataShouldNotBeNil
    }
}

func decodeModel<T: Decodable>(data: Data) throws -> T {
    return try JSONDecoder().decode(T.self, from: data)
}

func testModel<T: Decodable>(model: T.Type, jsonFilename: String) throws {
    let data = try loadJson(jsonFilename)
    let _: T = try decodeModel(data: data)
}
