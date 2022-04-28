//
//  TestTopRatedMoviesView.swift
//  Movie-ApplicationTests
//
//  Created by Mohanna Zakizadeh on 4/26/22.
//

import XCTest
@testable import Movie_Application

class TestTopRatedMoviesView: XCTestCase {
    
    var view: TopRatedMoviesView!

    override func setUpWithError() throws {
       
        view = .init()
        
    }

    override func tearDownWithError() throws {
        
        view = nil
    }

    

}
