//
//  MovieViewModel.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import Foundation

class MovieHandler: ObservableObject {
    @Published var movies: [Movie] = []
    
    init() {
        fetchMovieData()
    }
    
    func fetchMovieData() {
        let apiKey = TMDBapiKey //TODO: Add your TMDB key here
        guard let url = URL(string: "https://api.themoviedb.org/3/movie/popular?api_key=\(apiKey)&language=en-US&page=1") else{
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
                        self.movies = decodedResponse.results
                    }

                    return
                }
            }
            print("\(error?.localizedDescription ?? "Unknown error")")
            
        }.resume()
    }
}
