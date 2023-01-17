//
//  MovieViewModel.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/13/23.
//

import Foundation

class MovieViewModel {

    // MARK: - Properties

    private var movie: Movie? = nil
    private var movieId: Int64

    private let apiLoader: APILoader<MovieAPI>

    var title: String {
        self.movie?.title ?? self.movie?.originalTitle ?? ""
    }

    var imageUrl: URL? {
        URL(string: APIPath().fetchImage(width: "w185", imagePath: movie?.posterPath ?? ""))
    }

    var genres: [String] {
        movie?.genres.map { $0.name } ?? []
    }

    var overview: String {
        movie?.overview ?? ""
    }

    var rate: String {
        guard let rate = movie?.voteAverage else { return "" }
        return String(format: "%.2f", rate)
    }

    var totalVoteCount: String {
        guard let voteCount = movie?.voteCount else { return "" }
        return String(voteCount)
    }

    var releaseDate: String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dddd"
        guard let date = dateFormatter.date(from: movie?.releaseDate ?? "") else { return "" }
        dateFormatter.dateFormat = "MM/dd/yyyy"
        return dateFormatter.string(from: date)
    }

    // MARK: - Initializer

    init(apiLoader: APILoader<MovieAPI>, movieId: Int64) {
        self.apiLoader = apiLoader
        self.movieId = movieId
    }

    // MARK: - Functions

    func fetchMovie(
        completion: @escaping (MovieViewModel?, ServiceError?) -> ()
    ) {
        apiLoader.loadAPIRequest(requestData: movieId) { [weak self] movieResponse, error in
            guard let self = self else {
                return
            }

            if let _ = error {
                DispatchQueue.main.async {
                    completion(nil, error)
                }
            } else if let movieResponse = movieResponse {
                self.movie = movieResponse

                DispatchQueue.main.async {
                    completion(self, nil)
                }
            } else {
                DispatchQueue.main.async {
                    completion(nil, ServiceError(message: "Service Error: Unknow Error"))
                }
            }
        }
    }
}
