import Combine
import SwiftUI

class MovieDetailViewModel: ObservableObject {
  let client: Client
  let movie: Movie
  @Published var backdrop: UIImage?
  var movieRating: String { String(format: "%.1f", movie.rating) }

  private var cancellables = Set<AnyCancellable>()

  init(client: Client, movie: Movie) {
    self.client = client
    self.movie = movie

    if let backdropPath = movie.backdropPath {
      client
        .image(path: backdropPath, type: .backdrop, size: .thumbnail)
        .map { Optional($0) }
        .replaceError(with: nil)
        .assign(to: \.backdrop, on: self)
        .store(in: &cancellables)
    }
  }
}

struct MovieDetailView: View {
  @ObservedObject var viewModel: MovieDetailViewModel
  var body: some View {
    ScrollView {
      VStack(alignment: .leading) {
        Text(viewModel.movie.title)
          .fixedSize(horizontal: false, vertical: true)
          .font(.title)
        HStack(alignment: .firstTextBaseline) {
          Text(viewModel.movie.releaseDate.year)
            .padding([.trailing])
          Image(systemName: "star.fill")
            .foregroundColor(Color.yellow)
          Text(viewModel.movieRating)
        }
        .font(.body)
        .fixedSize(horizontal: true, vertical: true)

        Image(uiImage: viewModel.backdrop ?? UIImage())
          .resizable()
          .aspectRatio(450.0 / 300.0, contentMode: .fit)
          .padding([.bottom])
        Text(viewModel.movie.overview)
          .font(.body)
        Spacer()
      }
    }
    .padding()
    .navigationBarTitle("\(viewModel.movie.title)", displayMode: .inline)
  }
}

struct MovieDetailView_Previews: PreviewProvider {
  static var previews: some View {
    NavigationView {
      MovieDetailView(
        viewModel: .init(
          client: LiveClient(),
          movie: Movie(
            id: 0,
            title: "Testing a very long title, like extremely super duper long!",
            //title: "Short",
            rating: 5.0,
            popularity: 6.0,
            releaseDate: Date(),
            posterPath: "bvYjhsbxOBwpm8xLE5BhdA3a8CZ.jpg",
            backdropPath: "bOGkgRGdhrBYJSLpXaxhXVstddV.jpg",
            overview: """
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
              """)
        )
      )
    }
  }
}
