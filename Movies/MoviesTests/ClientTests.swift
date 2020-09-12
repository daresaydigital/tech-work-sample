import Combine
import XCTest


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
          expectation.fulfill()
        }
      )
      .store(in: &cancellables)

    wait(for: [expectation], timeout: 1)
  }

  func testLiveClientImage() throws {
    // The image url, path is the last component.
    // https://image.tmdb.org/t/p/w342/bvYjhsbxOBwpm8xLE5BhdA3a8CZ.jpg
    //let path = "ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg"
    let path = "bvYjhsbxOBwpm8xLE5BhdA3a8CZ.jpg"
    let client = LiveClient()
    let expectation = self.expectation(description: "receive value")
    client
      .image(path: path, type: .backdrop, size: .thumbnail)
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
          expectation.fulfill()
        }
      )
      .store(in: &cancellables)

    wait(for: [expectation], timeout: 1)
  }

}
