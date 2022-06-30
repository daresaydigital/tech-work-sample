//
//  MovieDetailUITest.swift
//  DaresayChallengeUITests
//
//  Created by Keihan Kamangar on 2022-06-30.
//

import XCTest

class MovieDetailUITest: XCTestCase {

    var app: XCUIApplication!
    
    override func setUp() {
        super.setUp()
        
        continueAfterFailure = false
        app = XCUIApplication()
        app.launchArguments = ["enable-testing"]
        app.launch()
    }
    
    func testTitleLabelExists() {
        goToMovieDetailVC()
        let label = findElement(in: app.staticTexts, with: .movieDetailTitleLabel)
        
        XCTAssert(label.waitForExistence(timeout: 2))
    }
    
    func testRatingLabelExists() {
        goToMovieDetailVC()
        let label = findElement(in: app.staticTexts, with: .movieDetailRatingLabel)
        
        XCTAssert(label.waitForExistence(timeout: 2))
    }
    
    func testContainerViewExists() {
        goToMovieDetailVC()
        let view = findElement(in: app.otherElements, with: .movieDetailContainerView)
        
        XCTAssert(view.waitForExistence(timeout: 2))
    }
    
    func testFavoriteButtonExists() {
        goToMovieDetailVC()
        let button = findElement(in: app.buttons, with: .movieDetailFavoriteButton)
        
        XCTAssert(button.waitForExistence(timeout: 2))
    }
    
    func testDescriptionLabelExists() {
        goToMovieDetailVC()
        let label = findElement(in: app.staticTexts, with: .movieDetailDescLabel)
        
        XCTAssert(label.waitForExistence(timeout: 2))
    }
    
    func testImageViewExists() {
        goToMovieDetailVC()
        let imageView = findElement(in: app.images, with: .movieDetailBackgroundImageView)
        
        XCTAssert(imageView.waitForExistence(timeout: 2))
    }
}

extension MovieDetailUITest {
    func goToMovieDetailVC() {
        let tableViewCell = findElement(in: app.cells, with: .moviesTableViewCell).firstMatch
        tableViewCell.tap()
    }
}
