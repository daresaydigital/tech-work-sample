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
        
        let mockStorage = MockStorage.shared
        
        mockService.getPopularMovies().sinkToResult { result in
            
            switch result {
            case .success(let list):
                XCTAssertEqual(list.results.count, 1)

                mockStorage.setObject(for: "popular", object: list.results)
                
                var retreivedList = mockStorage.getObject(by: "popular")
                
                XCTAssertEqual(list.results, retreivedList)
                
                mockStorage.remove(key: "popular")
                 
                retreivedList = mockStorage.getObject(by: "popular")
                
                XCTAssertNil(retreivedList)
                
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        }.store(in: &cancellables)
    }
    
    func testFavoriteStorage() {
        
        let mockService = MockService()
        var cancellables = Set<AnyCancellable>()
        
        // Store previous data
        var previousList = FavoriteStorage.currentList
        FavoriteStorage.removeAll()
        
        XCTAssertEqual(FavoriteStorage.currentList.count, 0)
        
        mockService.getPopularMovies().sinkToResult { result in

            switch result {
            case .success(let list):

                XCTAssertEqual(list.results.count, 1)

                if let firstMovie = list.results.first {
                    FavoriteStorage.append(movie: firstMovie)

                    XCTAssertEqual(FavoriteStorage.currentList.count, 1)
                    
                    let retreivedList = FavoriteStorage.currentList
                    
                    XCTAssertEqual(retreivedList.count, 1)

                    if let firstMovie = retreivedList.first {
                        FavoriteStorage.remove(movie: firstMovie)
                        XCTAssertEqual(FavoriteStorage.currentList.count, 0)
                       
                    }
                    // set previous data
                    FavoriteStorage.currentList = previousList
                }

            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        }.store(in: &cancellables)
    }
}
