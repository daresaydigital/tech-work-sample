import SwiftUI


struct TopMoviesView: View {
  let client: Client
  var body: some View {
    NavigationView {
      MovieListView(viewModel: .init(client: client))
        .navigationBarTitle("Top Movies", displayMode: .inline)
    }
    .accentColor(Color.yellow)
  }
}
