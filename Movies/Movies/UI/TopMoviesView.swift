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

struct TopMoviesView_Previews: PreviewProvider {
  static var previews: some View {
    TopMoviesView(client: LiveClient())
  }
}
