//
//  MoviesServiceTest.swift
//  DaresayChallengeTests
//
//  Created by Keihan Kamangar on 2022-06-29.
//

import XCTest
@testable import DaresayChallenge

class MoviesServiceTest: XCTestCase {

    var sut: MoviesService?
    var moviesJSON: Data?
    var configsJSON: Data?
    
    override func setUp() {
        super.setUp()
        
        let bundle = Bundle(for: type(of: self))
        do {
            let moviesPath = try XCTUnwrap(bundle.url(forResource:"movies", withExtension: "json"))
            let moviesData = try Data(contentsOf: moviesPath, options: .alwaysMapped)
            
            let configsPath = try XCTUnwrap(bundle.url(forResource:"configs", withExtension: "json"))
            let configsData = try Data(contentsOf: configsPath, options: .alwaysMapped)
            
            
            self.moviesJSON = moviesData
            self.configsJSON = configsData
        } catch {
            XCTFail("Failed to read JSON files.")
        }
    }
    
    override func tearDown() {
        sut = nil
        moviesJSON = nil
        configsJSON = nil
        super.tearDown()
    }
    
    func testGetMovies() {
        var url = RequestURL(baseURLString: "")
        url.appendPathComponents([.version, .movie, .popular])
        
        URLProtocolMock.testURLs = [url.url: moviesJSON!]
        
        let session = createURLSession()
        
        let networkMock = NetworkingMock(session: session, validResponseCodes: [200], dispatchQueue: .main)
        
        sut = MoviesService(serverManager: networkMock)
        
        let expectation = XCTestExpectation(description: "Async movies test")
        var movies: [MoviesModel]?
        
        let httpRequest = getMovies(url: url, page: 1)
        
        sut?.getMovies(httpRequest: httpRequest, completionHandler: { result in
            
            defer {
                expectation.fulfill()
            }
            
            switch result {
            case .success(let response):
                movies = response.results
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        })
        
        wait(for: [expectation], timeout: 5)
        XCTAssertTrue(!movies!.isEmpty)
    }
    
    func testGetConfigs() {
        var url = RequestURL(baseURLString: "")
        url.appendPathComponents([.version, .config])
        
        URLProtocolMock.testURLs = [url.url: configsJSON!]
        
        let session = createURLSession()
        
        let networkMock = NetworkingMock(session: session, validResponseCodes: [200], dispatchQueue: .main)
        
        sut = MoviesService(serverManager: networkMock)
        
        let expectation = XCTestExpectation(description: "Async configs test")
        var configs: ConfigurationModel?
        
        let httpRequest = getConfigs(url: url)
        
        sut?.getConfigs(httpRequest: httpRequest, completionHandler: { result in
            
            defer {
                expectation.fulfill()
            }
            
            switch result {
            case .success(let response):
                configs = response
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        })
        
        wait(for: [expectation], timeout: 5)
        XCTAssertTrue(!configs!.changeKeys!.isEmpty)
        XCTAssertTrue(configs!.images != nil)
    }
}

// MARK: - Helpers
extension MoviesServiceTest {
    func createURLSession() -> URLSession {
        let config = URLSessionConfiguration.ephemeral
        config.protocolClasses = [URLProtocolMock.self]
        
        let session = URLSession(configuration: config)
        
        return session
    }
    
    func getMovies(url: RequestURL, page: UInt) -> HTTPRequest {
        let headers: [String: String] = baseRequestHeaders
        let params: [String: Any] = ["page": "\(page)"]
        
        return HTTPRequest(method: .GET, url: url, auth: .otp, parameters: params, bodyMessage: nil, headers: headers, timeOut: .normal)
    }
    
    func getConfigs(url: RequestURL) -> HTTPRequest {
        let headers: [String: String] = baseRequestHeaders
        
        return HTTPRequest(method: .GET, url: url, auth: .otp, parameters: nil, bodyMessage: nil, headers: headers, timeOut: .normal)
    }
}
