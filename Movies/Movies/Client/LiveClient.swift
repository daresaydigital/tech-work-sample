import UIKit
import Combine


/// The `LiveClient` that uses https://www.themoviedb.org/
///
/// Remember to set  the`key` property to your api key, see:
/// https://developers.themoviedb.org/3/getting-started/authentication
class LiveClient: Client {
  #warning ("Remove/Insert API Secret")
  let key = ""
  let session = URLSession.shared
    
  /// Fetch top movies
  ///  - Parameters:
  ///     - sorting: The movie sorting
  ///
  ///  - Returns: A `Publisher` of `[Movies]`
  ///
  /// API information:
  /// https://developers.themoviedb.org/3/movies/get-popular-movies
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
    .map { $0.results.compactMap { $0.movie } }
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
    .receive(on: DispatchQueue.main)
    .eraseToAnyPublisher()
  }
  
  /// Fetch a movie poster image
  ///  - Parameters:
  ///     - posterPath: The path to the poster
  ///     - size: the image size
  ///
  ///  - Returns: A publisher of UIImage
  ///
  /// This uses the hardcoded url discussed here:
  /// https://www.themoviedb.org/talk/5aeaaf56c3a3682ddf0010de
  ///
  /// A production release should use api-configuration instead:
  /// https://developers.themoviedb.org/3/configuration/get-api-configuration  
  func image(path: String, type: ImageType, size: ImageSize) -> AnyPublisher<UIImage,Error> {
    let url = URL(string: """
      https://image.tmdb.org/t/p/\(sizeString(type: type, size: size))/\(path)
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
    .tryMap { data -> UIImage in
      guard
        let image = UIImage(data: data)
        else { throw Error.decodingImage }
      return image
    }
    .mapError { error -> Error in
      switch error {
      case let error as Swift.DecodingError:
        return .decoding(error)
      case let error as URLError:
        return .url(error)
      case let error as Error:
        return error
      default:
        return .other(error)
      }
    }
    .receive(on: DispatchQueue.main)
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
      var posterPath: String?
      var backdropPath: String?
      var overview: String
    }
    var page: Int
    var totalResults: Int
    var totalPages: Int
    var results: [MovieResponse]
  }
}

extension LiveClient.ListResponse.MovieResponse {
  /// Maps a `MovieResponse` to a `Movie`.
  var movie: Movie? {
    let dateFormatter = DateFormatter()
    dateFormatter.dateFormat = "yyyy-MM-dd"
    
    guard
      let date = dateFormatter.date(from:releaseDate)
      else { return nil }
    
    return Movie(
      id: id,
      title: title,
      rating: voteAverage,
      popularity: popularity,
      releaseDate: date,
      posterPath: posterPath,
      backdropPath: backdropPath,
      overview: overview
    )
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

fileprivate extension LiveClient {
  /// Translates a `ImageSize` to the corresponding size string used by the endpoint.
  func sizeString(type: ImageType, size: ImageSize) -> String {
    switch (type,size) {
    case (.poster, .thumbnail):
      return "w342"
    case (.poster, .full):
      return "original"
    case (.backdrop, .thumbnail):
      return "w300"
    case (.backdrop, .full):
      return "original"
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
