import SwiftUI
import Combine

class MovieListRowViewModel: ObservableObject {
  let client: Client
  let movie: Movie
  @Published var poster: UIImage?
  @Published var hasFailed: Error?
  
  private var cancellables = Set<AnyCancellable>()
  
  init(client: Client, movie: Movie) {
    self.client = client
    self.movie = movie
    
    if let posterPath = movie.posterPath {
      client
        .poster(posterPath: posterPath, size: .thumbnail)
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
      VStack(alignment: .leading) {
        Text(viewModel.movie.title)
          .font(.headline)
          .lineLimit(2)
          .fixedSize(horizontal: false, vertical: true)
        detailRow(movie: viewModel.movie)
      }
    }
  }
  
  func detailRow(movie: Movie) -> some View {
    let format = DateFormatter()
    format.dateFormat = "yyyy"
    
    return HStack {
      Image(systemName: "star.fill")
        .foregroundColor(Color.yellow)
      Text(String(format: "%.1f", movie.rating))
        .fontWeight(.bold)
      Text(format.string(from: movie.releaseDate))
    }
    .fixedSize(horizontal: true, vertical: true)
    .font(.caption)
    
  }
}

struct MovieListRow_Previews: PreviewProvider {
  static var previews: some View {
    MovieListRow(viewModel: .init(
      client: LiveClient(),
      movie: .init(
        id: 0,
        //title: "Testing a very long title, like extremely super duper long!",
        title: "Short",
        rating: 5.0,
        popularity: 6.0,
        releaseDate: Date(),
        posterPath: "bvYjhsbxOBwpm8xLE5BhdA3a8CZ.jpg"))
    )
      .frame(height: 100)
  }
}
