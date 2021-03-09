//
//  SelectedMovieVMTests.swift
//  Darsey-MovieDBTests
//
//  Created by Emil Vaklinov on 09/03/2021.
//

import XCTest
@testable import Darsey_MovieDB

class SelectedMovieVMTests: XCTestCase {

    //MARK: - Properties
    
    private var selectedMovie: SelectedMovie!
    
    // MARK: Test lifecycle
    
    override func setUp() {
        let genres = [Genre(id: 1, name: "Suspense"),
                      Genre(id: 2, name: "Action"),
                      Genre(id: 3, name: "Thriller")]
        
        selectedMovie = SelectedMovie(title: "Inception",
                                      runtime: 120,
                                      release_date: "2010-10-16",
                                      vote_average: 9.0,
                                      genres: genres,
                                      overview: "Movie Overview")
    }
    
    func testSelectedMovieViewModel() {
        let viewModel = SelectedMovieViewModel(selectedMovie)
        
        XCTAssertEqual(selectedMovie.title, viewModel.title)
        XCTAssertEqual(selectedMovie.release_date, viewModel.release_date)
        XCTAssertEqual(selectedMovie.vote_average, viewModel.vote_average)
        XCTAssertEqual(selectedMovie.runtime, viewModel.runtime)
        XCTAssertEqual(selectedMovie.genres, viewModel.genres)
        XCTAssertEqual(selectedMovie.title, viewModel.title)
    }
    
    func testYearOfRelease() {
        let viewModel = SelectedMovieViewModel(selectedMovie)
        let mockYearOfRelease = "2010"
        
        XCTAssertEqual(mockYearOfRelease, viewModel.yearOfRelease)
    }
    
    func testScore() {
        let viewModel = SelectedMovieViewModel(selectedMovie)
        let mockScore = "9.0 / 10"
        
        XCTAssertEqual(mockScore, viewModel.score)
    }
    
    func testAllGenres() {
        let viewModel = SelectedMovieViewModel(selectedMovie)
        let mockAllGenres = "Suspense, Action, Thriller"
        
        XCTAssertEqual(mockAllGenres, viewModel.allGenres)
    }

}
