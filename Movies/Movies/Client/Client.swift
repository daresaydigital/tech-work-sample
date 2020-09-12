import UIKit
import Combine

/// The interface for the movie API.
protocol Client {
  func movies(sorting: MovieSorting) -> AnyPublisher<[Movie],Error>
  func image(path: String, type: ImageType, size: ImageSize) -> AnyPublisher<UIImage,Error>
}
