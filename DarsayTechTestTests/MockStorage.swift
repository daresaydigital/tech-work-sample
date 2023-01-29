//
//  MockStorage.swift
//  DarsayTechTestTests
//
//  Created by Farzaneh on 11/9/1401 AP.
//

import Foundation
import XCTest
@testable import DarsayTechTest

final class MockStorage: StorageProtocol {
    typealias StoredObject = [Movie]
    
    static let shared = MockStorage()
    
}
