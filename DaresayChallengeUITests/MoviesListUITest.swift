//
//  MoviesListUITest.swift
//  DaresayChallengeUITests
//
//  Created by Keihan Kamangar on 2022-06-30.
//

import XCTest

class MoviesListUITest: XCTestCase {

    var app: XCUIApplication!
    
    override func setUp() {
        super.setUp()
        
        continueAfterFailure = false
        app = XCUIApplication()
        app.launchArguments = ["enable-testing"]
        app.launch()
    }
    
    func testTableViewExists() {
        let tableView = findElement(in: app.tables, with: .moviesTableView)
        
        XCTAssert(tableView.waitForExistence(timeout: 2))
    }
    
    func testTableViewCellExists() {
        let tableViewCell = findElement(in: app.cells, with: .moviesTableViewCell)
        
        XCTAssert(tableViewCell.waitForExistence(timeout: 2))
    }
    
    func testTitleLabelExists() {
        let label = findElement(in: app.staticTexts, with: .movieTitleLabel)
        
        XCTAssert(label.waitForExistence(timeout: 2))
    }
    
    func testDescriptionLabelExists() {
        let label = findElement(in: app.staticTexts, with: .movieDescriptionLabel)
        
        XCTAssert(label.waitForExistence(timeout: 2))
    }
    
    func testImageViewExists() {
        let imageView = findElement(in: app.images, with: .movieImageView)
        
        XCTAssert(imageView.waitForExistence(timeout: 2))
    }
    
    func testFavoriteImageViewExists() {
        let imageView = findElement(in: app.images, with: .movieFavoriteImageView)
        
        XCTAssert(imageView.waitForExistence(timeout: 2))
    }
    
    func testBarButtonItemExists() {
        let button = findElement(in: app.buttons, with: .favoriteBarButton)
        
        XCTAssert(button.waitForExistence(timeout: 2))
    }
}
