import Combine
import UIKit


/// The interface for the movie API.
protocol Client {
  
  /// Fetches movies sorted by `MovieSorting`.
  ///
  /// - Parameter sorting: The sorting of the returned movies.
  /// - Returns: A publisher of movies.
  func movies(sorting: MovieSorting) -> AnyPublisher<[Movie], Error>

  /// Fetch an image movie related image.
  ///
  /// - Parameters:
  ///   - path: The path to image.
  ///   - type: The image type.
  ///   - size: The image size.
  /// - Returns: A publisher of an image.
  func image(path: String, type: ImageType, size: ImageSize) -> AnyPublisher<UIImage, Error>
}
