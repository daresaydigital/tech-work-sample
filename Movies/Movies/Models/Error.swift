import Foundation

/// The general error type used by the `Movies` application.
enum Error: Swift.Error {
  case other(Swift.Error)
  case decoding(DecodingError)
  case url(URLError)
  case decodingImage
}

