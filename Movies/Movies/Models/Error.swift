import Foundation

enum Error: Swift.Error {
  case other(Swift.Error)
  case decoding(DecodingError)
  case url(URLError)
  case decodingImage
}
