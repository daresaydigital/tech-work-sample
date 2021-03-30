//
//  MovieTests.swift
//  MovieTests
//
//  Created by Adrian Sergheev on 2021-03-30.
//

import XCTest
import RxTest
import RxSwift

@testable import Movie

class MovieTests: XCTestCase {
    
    var viewModel: HomeContainerViewModelType!
    var scheduler: TestScheduler!
    var disposeBag: DisposeBag!
    
    var movieAPI: MovieAPI!
    var router: NetworkRouterMock<MovieApiProvider>!
    
    override func setUp() {
        super.setUp()
        self.scheduler = TestScheduler(initialClock: 0)
        self.disposeBag = DisposeBag()
        
        self.router = NetworkRouterMock<MovieApiProvider>()
        self.movieAPI = MovieAPI(router)
        
        self.viewModel = HomeContainerViewModel(movieApi: movieAPI)
    }
    
    override func tearDown() {
        self.viewModel = nil
        self.movieAPI = nil
        self.router = nil
    }
    
    
    // MARK: -  HomeContainerViewModel
    
    func testFetchWithError() throws {
        
        let injectedError = NetworkError.parametersNil
        router.setErrorForRequest(injectedError)
        
        let error = scheduler.createObserver(NetworkError.self)
        
        scheduler.createHotObservable([.next(10, Void() )])
            .subscribe(onNext: { [weak self] _ in self?.viewModel.input.fetchMovies(filter: .popular)})
            .disposed(by: disposeBag)
        
        viewModel.output
            .error
            .compactMap { $0 as? NetworkError }
            .bind(to: error)
            .disposed(by: disposeBag)
        
        scheduler.start()
        
        XCTAssertEqual(error.events, [.next(10, injectedError)])
    }
    
    func testFetchWithSuccess() throws {
        
        let movies = scheduler.createObserver([Movie].self)
        
        let response = try loadJson(TestFilenames.trending.rawValue)
        router.setResponseForRequest(response)
        
        scheduler.createHotObservable([.next(10, Void() )])
            .subscribe(onNext: { [weak self] _ in self?.viewModel.input.fetchMovies(filter: .popular)})
            .disposed(by: disposeBag)
        
        viewModel.output
            .movies
            //            .skip(3) // one for inital init of the state, two and three for zeroing the movies by fetchMovies(filter:) call.
            //            .debug("🙂")
            .bind(to: movies)
            .disposed(by: disposeBag)
        
        scheduler.start()
        
        //get all the events, flatten them in one array , make sure that there are eventually movies received
        let events = movies.events
            .compactMap { $0.value }
            .compactMap { $0.element }
            .flatMap { $0 }
        
        XCTAssertNotEqual(events.isEmpty, true)
    }
    
    func testInputIsPreserved() throws {
        
        let response = try loadJson(TestFilenames.trending.rawValue)
        router.setResponseForRequest(response)
        
        let category = scheduler.createObserver(MovieCategory.self)
        
        scheduler.createHotObservable(
            [.next(10, MovieCategory.popular),
             .next(11, .topRated),
             .next(12, .trending)])
            .subscribe(onNext: { [weak self] element in self?.viewModel.input.fetchMovies(filter: element)})
            .disposed(by: disposeBag)
        
        
        viewModel.output
            .pickedFilter
            .skip(1) //skip first, intialized in the vm state machine
            .distinctUntilChanged()
            .bind(to: category)
            .disposed(by: disposeBag)
        
        scheduler.start()
        
        XCTAssertEqual(category.events, [.next(10, MovieCategory.popular),
                                         .next(11, MovieCategory.topRated),
                                         .next(12, MovieCategory.trending)])
    }
    
    func testIsLoading() throws {
        
        let response = try loadJson(TestFilenames.trending.rawValue)
        router.setResponseForRequest(response)
        let isLoading = scheduler.createObserver(Bool.self)
        
        scheduler.createHotObservable([.next(10, MovieCategory.popular)])
            .subscribe(onNext: { [weak self] element in self?.viewModel.input.fetchMovies(filter: element)})
            .disposed(by: disposeBag)
        
        viewModel.output
            .isLoading
            .skip(1)
            .bind(to: isLoading)
            .disposed(by: disposeBag)
        
        scheduler.start()
        
        XCTAssertEqual(isLoading.events, [.next(10, true), .next(10, false)])
    }
    
}
