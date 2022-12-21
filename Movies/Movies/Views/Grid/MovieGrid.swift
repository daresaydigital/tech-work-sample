//
//  MovieGrid.swift
//  Movies
//
//  Created by Richard Segerblom on 2022-12-21.
//

import SwiftUI

struct MovieGrid: View {
    @ObservedObject var viewModel: ViewModel
    
    @State private var filterOption = 0
    
    let columns = [
        GridItem(.adaptive(minimum: 154))
    ]
    
    var body: some View {
        NavigationView {
            VStack {
                Picker("Filter", selection: $filterOption) {
                    Text("Popular").tag(0)
                    Text("Top Rated").tag(1)
                }
                .pickerStyle(.segmented)
                .onChange(of: filterOption) {
                    viewModel.toggle($0)
                }
                
                ScrollView(.vertical, showsIndicators: false) {
                    LazyVGrid(columns: columns, spacing: 10) {
                        ForEach(viewModel.movies) { movie in
                            NavigationLink(destination: DetailView(viewModel: .init(movie: movie)), label: {
                                VStack {
                                    AsyncImage(url: viewModel.movePosterURL(movie))
                                        .frame(width: 154, height: 231)
                                    VStack() {
                                        Text(movie.title)
                                            .font(.system(size: 10))
                                    }
                                    .padding(.horizontal, 10)
                                    Spacer()
                                }
                            }).foregroundColor(Color.black)
                        }
                    }
                }
                
                Spacer()
            }.padding(.horizontal)
        }
    }
}

struct MovieGrid_Previews: PreviewProvider {
    static var previews: some View {
        MovieGrid(viewModel: viewModel)
    }
    
    static var viewModel: MovieGrid.ViewModel {
        let movies: [Movie] = [
            .init(id: 0, title: "Avatar: The Way of Water", posterURL: "", releaseDate: "", description: ""),
            .init(id: 1, title: "Black Adam", posterURL: "", releaseDate: "", description: ""),
            .init(id: 2, title: "Troll", posterURL: "", releaseDate: "", description: ""),
            .init(id: 3, title: "Guillermo del Toro's Pinocchio", posterURL: "", releaseDate: "", description: ""),
            .init(id: 4, title: "Avatar", posterURL: "", releaseDate: "", description: ""),
            .init(id: 5, title: "Night at the Museum: Kahmunrah Rises Again", posterURL: "", releaseDate: "", description: ""),
        ]
        
        return .init(movieService: MockMovieService(mostPopularMoviesResult: movies))
    }
}
