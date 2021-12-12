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
    
    // TODO: We should use mocked data here instead.
    func testMovieDetailGenresIsDisplayedCorrectly() {
        let app = XCUIApplication()
        app.launch()
        let tablesQuery = app.tables
        tablesQuery.cells.containing(.staticText, identifier:"Sep 30, 2021")
            .staticTexts["Venom: Let There Be Carnage"]
            .tap()
        XCTAssert(tablesQuery/*@START_MENU_TOKEN@*/.cells.staticTexts["Science Fiction, Action, Adventure"]/*[[".cells.staticTexts[\"Science Fiction, Action, Adventure\"]",".staticTexts[\"Science Fiction, Action, Adventure\"]"],[[[-1,1],[-1,0]]],[1]]@END_MENU_TOKEN@*/
                    .waitForExistence(timeout: 3))
    }
}
