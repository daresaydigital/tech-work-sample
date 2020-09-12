import Foundation

enum ClientError: Error {
  case other(Error)
  case decoding(DecodingError)
  case url(URLError)
}

