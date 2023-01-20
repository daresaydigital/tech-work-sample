//
//  MovieDBTests.swift
//  MovieDBTests
//
//  Created by Sinan Ulusoy on 19.01.2023.
//

import XCTest
@testable import MovieDB

final class MovieViewModelTests: XCTestCase {

    let vm = MoviesViewModel()
    
    func test() {
        vm.getData()
//        XCTAssertEqual("The Godfather", vm.topRatedMovieModelList[0].title)
    }

}
