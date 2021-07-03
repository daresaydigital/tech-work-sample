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

/*
 Handles and fecthes the data from TMDB
 */
class MovieHandler: ObservableObject {
    @Published var popularMovies: [Movie] = []
    @Published var topRatedMovies: [Movie] = []
    let apiKey = TMDBapiKey //TODO: Add your TMDB key here (string)
    
    /**
        Fetches movie data from TMDB
     
     -Parameter filter: The order type to filter the data on (popular or top rated)
     -Returns: Adds movies to the corresponing property wrapper
     */
    func fetchMovieData(withFilter filter: movieFilter) {
        var filterType: String {
            switch filter {
            case .popular:
                return "popular"
            case .topRated:
                return "top_rated"
            }
        }
        
        guard let url = URL(string: "https://api.themoviedb.org/3/movie/\(filterType)?api_key=\(apiKey)&language=en-US&page=1") else{
                print("Data url broken")
                return
        }
        
        URLSession.shared.dataTask(with: url){ data, _, error in
            if let data = data {
                let decoder = JSONDecoder()
                decoder.keyDecodingStrategy = .convertFromSnakeCase
                if let decodedResponse = try? decoder.decode(MovieDataResponse.self, from: data) {
                    DispatchQueue.main.async {
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
            print("Error while downloading data. Error: \(String(describing: error))")
            
        }.resume()
    }
}
