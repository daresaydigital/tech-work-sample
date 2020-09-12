import UIKit
import Combine

/// The interface for the movie API.
protocol Client {
  func movies(sorting: MovieSorting) -> AnyPublisher<[Movie],Error>
  func poster(posterPath: String, size: ImageSize) -> AnyPublisher<UIImage,Error>
}
