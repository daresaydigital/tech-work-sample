import UIKit
import Combine


/// The `LiveClient` that uses https://www.themoviedb.org/
///
/// Remember to set  the`key` property to your api key, see: https://developers.themoviedb.org/3/getting-started/authentication
class LiveClient: Client {
  #warning ("Remove/Insert API Secret")
  let key = ""
  let session = URLSession.shared
    
  func movies(sorting: MovieSorting) -> AnyPublisher<[Movie],Error> {
    // https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1
    let url = URL(string: """
      https://api.themoviedb.org/3/movie/\
      \(sorting.queryString)?\
      api_key=\(key)\
      &language=en-US\
      &page=1
      """)!
    return session
    .dataTaskPublisher(for: url)
      .tryMap() { element -> Data in
        guard
          let httpResponse = element.response as? HTTPURLResponse,
          httpResponse.statusCode == 200
          else { throw URLError(.badServerResponse) }
        return element.data
    }
    .decode(type: ListResponse.self, decoder: JSONDecoder.convertFromSnakeCase)
    .map { $0.results.map { $0.movie } }
    .mapError { error -> Error in
      switch error {
      case let error as Swift.DecodingError:
        return .decoding(error)
      case let error as URLError:
        return .url(error)
      default:
        return .other(error)
      }
    }
    .receive(on: RunLoop.main)
    .eraseToAnyPublisher()
  }
}

extension LiveClient {
  /// An intermediate struct for converting a API JSON response into  `Movie`.
  struct ListResponse: Codable {
    struct MovieResponse: Codable {
      var id: Int
      var title: String
      var voteAverage: Double
      var popularity: Double
      var releaseDate: String
      
      /// Maps a `MovieResponse` to a `Movie`.
      var movie: Movie {
        .init(
          id: id,
          title: title,
          rating: voteAverage,
          popularity: popularity,
          releaseDate: releaseDate
        )
      }
    }
    var page: Int
    var totalResults: Int
    var totalPages: Int
    var results: [MovieResponse]
  }
}

fileprivate extension MovieSorting {
  /// Translates a `MovieSorting` to the corresponding query string used by the endpoint.
  var queryString: String {
    switch self {
    case .popularity:
      return "popular"
    case .rating:
      return "top_rated"
    }
  }
}

fileprivate extension JSONDecoder {
  /// A `JSONDecoder` configured to automatically converts from snake_case to camelCase.
  static var convertFromSnakeCase: JSONDecoder {
    let decoder = JSONDecoder()
    decoder.keyDecodingStrategy = .convertFromSnakeCase
    return decoder
  }
}
