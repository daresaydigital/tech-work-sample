import XCTest
import Combine

class ClientTests: XCTestCase {
  
  var cancellables = Set<AnyCancellable>()

  override func tearDownWithError() throws {
    cancellables.removeAll()
  }
  
  func testLiveClientMovieDecodable() throws {
    let client = LiveClient()
    let expectation = self.expectation(description: "receive value")
    client
      .movies(sorting: .rating)
      .sink(
        receiveCompletion: {
          switch $0 {
          case .failure(let error):
            XCTFail("\(error)")
          case .finished:
            break
          }
      },
        receiveValue: { value in
          print(value.count)
          expectation.fulfill()
      })
      .store(in: &cancellables)

    wait(for: [expectation], timeout: 1)
  }
}
