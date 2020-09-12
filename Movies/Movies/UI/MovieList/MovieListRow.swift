import Combine
import SwiftUI


class MovieListRowViewModel: ObservableObject {
  let client: Client
  let movie: Movie
  @Published var poster: UIImage?
  @Published var hasFailed: Error?
  var movieRating: String { String(format: "%.1f", movie.rating) }

  private var cancellables = Set<AnyCancellable>()

  init(client: Client, movie: Movie) {
    self.client = client
    self.movie = movie

    if let posterPath = movie.posterPath {
      client
        .image(path: posterPath, type: .poster, size: .thumbnail)
        .map { Optional($0) }
        .replaceError(with: nil)
        .assign(to: \.poster, on: self)
        .store(in: &cancellables)
    }
  }
}

struct MovieListRow: View {
  @ObservedObject var viewModel: MovieListRowViewModel
  var body: some View {
    HStack {
      Image(uiImage: viewModel.poster ?? UIImage())
        .resizable()
        .aspectRatio(342.0 / 513.0, contentMode: .fit)
        .frame(height: 120)
      VStack(alignment: .leading) {
        Text(viewModel.movie.title)
          .font(.headline)
          .lineLimit(2)
          .fixedSize(horizontal: false, vertical: true)
        HStack {
          Image(systemName: "star.fill")
            .foregroundColor(Color.yellow)
          Text(viewModel.movieRating)
            .fontWeight(.bold)
          Text(viewModel.movie.releaseDate.year)
        }
        .fixedSize(horizontal: true, vertical: true)
        .font(.caption)
      }
      Spacer()
    }
  }
}

struct MovieListRow_Previews: PreviewProvider {
  static var previews: some View {
    MovieListRow(
      viewModel: .init(
        client: LiveClient(),
        movie: .init(
          id: 0,
          title: "Testing a very long title, like extremely super duper long!",
          //title: "Short",
          rating: 5.0,
          popularity: 6.0,
          releaseDate: Date(),
          posterPath: "bvYjhsbxOBwpm8xLE5BhdA3a8CZ.jpg",
          overview: "overview"))
    )
  }
}
