//
//  StorageTest.swift
//  DarsayTechTestTests
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation
import Combine
import XCTest
@testable import DarsayTechTest

final class StorageTest: XCTestCase {
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testStorage() {
        
        let mockService = MockService()
        var cancellables = Set<AnyCancellable>()
        
        mockService.getPopularMovies().sinkToResult { result in
            
            switch result {
            case .success(let list):
                XCTAssertEqual(list.results.count, 1)

                FavoriteStorage.shared.setObject(for: "popular", object: list.results)
                
                var retreivedList = FavoriteStorage.shared.getObject(by: "popular")
                
                XCTAssertEqual(list.results, retreivedList)
                
                FavoriteStorage.shared.remove(key: "popular")
                 
                retreivedList = FavoriteStorage.shared.getObject(by: "popular")
                
                XCTAssertNil(retreivedList)
                
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        }.store(in: &cancellables)
    }
}
