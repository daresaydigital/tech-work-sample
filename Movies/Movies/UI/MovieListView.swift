import SwiftUI
import Combine

class MovieListViewModel: ObservableObject {
  let client: Client
  @Published var movieSorting: MovieSorting = .rating
  @Published var movies: [Movie] = []
  @Published var hasFailed: Error?
  
  private var cancellables = Set<AnyCancellable>()
  
  init(client: Client) {
    self.client = client
    
    $movieSorting
      .setFailureType(to: Error.self)
      .flatMap { self.client.movies(sorting: $0) }
      .sink(
        receiveCompletion: {
          switch $0 {
          case .failure(let error):
            self.hasFailed = error
          case .finished:
            break
          }
      },
        receiveValue: { self.movies = $0 } )
      .store(in: &cancellables)
  }
}

struct MovieListView: View {
  @ObservedObject var viewModel: MovieListViewModel
  var body: some View {
    List {
      Section(header:
        Picker("", selection: self.$viewModel.movieSorting) {
          ForEach.init(MovieSorting.allCases) { sorting in
            Text(sorting.rawValue.capitalized).tag(sorting)
          }
        }
        .pickerStyle(SegmentedPickerStyle())
        .padding())
      {
        ForEach(viewModel.movies) { movie in
          NavigationLink(destination: Text("Detail \(movie.id)")) {
            MovieListRow(viewModel: .init(client: self.viewModel.client, movie: movie))
              .frame(height: 120)
          }
        }
      }
    }
    .listStyle(GroupedListStyle())
  }
}

struct MovieListView_Previews: PreviewProvider {
  static var previews: some View {
    NavigationView {
      MovieListView(viewModel: .init(client: LiveClient()))
        .navigationBarTitle("Top Movies", displayMode: .inline)
    }
  }
}
