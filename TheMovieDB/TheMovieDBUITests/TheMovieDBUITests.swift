//
//  TheMovieDBUITests.swift
//  TheMovieDBUITests
//
//  Created by Ali Sani on 12/11/21.
//

import XCTest

class TheMovieDBUITests: XCTestCase {

    override func setUpWithError() throws {
        super.setUp()
        continueAfterFailure = false
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
    }
    
    func testHomeViewInitialViewsExist() {
        let app = XCUIApplication()
        app.launch()
                
        XCTAssert(app.staticTexts["The Movie DB"].waitForExistence(timeout: 1))
        XCTAssert(app.tables.buttons["Top rated"].waitForExistence(timeout: 1))
        XCTAssert(app.tables.buttons["Popular"].waitForExistence(timeout: 1))
                                                
    }
}
