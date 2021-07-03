//
//  MovieViewModel.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import Foundation

enum movieFilter {
    case popular, topRated
}

class MovieHandler: ObservableObject {
    @Published var popularMovies: [Movie] = []
    @Published var topRatedMovies: [Movie] = []
    
    func fetchMovieData(withFilter filter: movieFilter) {
        let apiKey = TMDBapiKey //TODO: Add your TMDB key here
        var filterType: String {
            switch filter {
            case .popular:
                return "popular"
            case .topRated:
                return "top_rated"
            }
        }
        
        guard let url = URL(string: "https://api.themoviedb.org/3/movie/\(filterType)?api_key=\(apiKey)&language=en-US&page=1") else{
                print("Url broken")
                return
        }
        
        URLSession.shared.dataTask(with: url){ data, _, error in
            if let data = data {
                let decoder = JSONDecoder()
                decoder.keyDecodingStrategy = .convertFromSnakeCase
                if let decodedResponse = try? decoder.decode(MovieDataResponse.self, from: data) {
                    DispatchQueue.main.async {
                        print(decodedResponse.results)
                        switch filter{
                        case .popular:
                            self.popularMovies = decodedResponse.results
                            
                        case .topRated:
                            self.topRatedMovies = decodedResponse.results
                        }
                        
                    }

                    return
                }
            }
            print("\(error?.localizedDescription ?? "Unknown error")")
            
        }.resume()
    }
}
