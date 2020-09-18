//
//  MovieDetailsViewModel.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import Foundation

import UIKit

protocol MovieDetailsViewModelDelegate: AnyObject {
    func fetchMovieBackdropImageSucceded(with image: UIImage)
    func fetchMovieBackdropImageFailed(with error: ImageLoaderError)
    func fetchMoviePosterImageSucceded(with image: UIImage)
    func fetchMoviePosterImageFailed(with image: ImageLoaderError)
}

extension NSNotification.Name {
    // Notification posted when favorites have changed.
    public static let FavoritesChangedNotification = NSNotification.Name("FavoritesHaveChanged")
}

class MovieDetailsViewModel {

    var id: Int!
    var title: String!
    var genre: String!
    var releaseDate: String!
    var overview: String!
    var posterURL: URL?
    var backgroundImageURL: URL?
    var totalVotes: Int!
    var rating: Double!
    var movie: Movie
    var isFavorite = false
    var imageService: ImageService!
    private weak var delegate: MovieDetailsViewModelDelegate?

    init(with movie: Movie, delegate: MovieDetailsViewModelDelegate) {
        self.delegate = delegate
        self.movie = movie
        id = movie.id
        title = movie.title
        genre = getGenre(for: movie)
        overview = movie.overview
        releaseDate = movie.releaseDate
        posterURL = URL(string: URLStrings.baseImageURL + movie.posterPath)
        if let backgroundPath = movie.backdropPath {
            backgroundImageURL = URL(string: URLStrings.baseImageURL + backgroundPath)
        }
        totalVotes = movie.numberOfTotalVotes
        rating = movie.rating
        isFavorite = isFavoritedMovie()
        imageService = ImageService()
    }

    func editFavorites() {
        if isFavorite {
            FavoritesService.removeMovie(movie)
            NotificationCenter.default.post(name: .FavoritesChangedNotification, object: nil)
            isFavorite = false
        } else {
            FavoritesService.addMovie(movie)
            NotificationCenter.default.post(name: .FavoritesChangedNotification, object: nil)
            isFavorite = true
        }
    }

    private func isFavoritedMovie() -> Bool {
        return FavoritesService.isFavoriteMovie(movie)
    }

    private func getGenre(for movie: Movie) -> String {
        guard let genre = GenreService.shared.genre(for: movie) else { return ""}
        return genre.name
    }

    func ratings() -> NSMutableAttributedString? {
        guard let rating = rating, let totalVotes = totalVotes else {
            return NSMutableAttributedString(string: "")
        }

        let ratingAttributes: [NSAttributedString.Key: Any] = [
            NSAttributedString.Key.foregroundColor : UIColor(red: 1, green: 0.7, blue: 0, alpha: 1),
            NSAttributedString.Key.font : UIFont.boldSystemFont(ofSize: CGFloat(24))
        ]
        let textAttributes: [NSAttributedString.Key: Any] = [
            NSAttributedString.Key.foregroundColor : UIColor.darkGray,
            NSAttributedString.Key.font : UIFont.systemFont(ofSize: CGFloat(12))
        ]

        let voteAverageText = NSMutableAttributedString(string: "\(rating)", attributes: ratingAttributes)
        voteAverageText.append(NSAttributedString(string: "\n"))
        let votersText = NSAttributedString(string: "\(totalVotes) voters", attributes: textAttributes)
        voteAverageText.append(votersText)

        return voteAverageText
    }

    func fetchBackdropImage() {
        imageService.getImage(url: backgroundImageURL) { (result) in
            switch result {
            case .success(let image):
                DispatchQueue.main.async {
                    self.delegate?.fetchMovieBackdropImageSucceded(with: image)
                }
            case .failure(let error):
                self.delegate?.fetchMovieBackdropImageFailed(with: error)
                print(error.localizedDescription)
            }
        }
    }
    
    func fetchPosterImage() {
        imageService.getImage(url: posterURL) { (result) in
            switch result {
            case .success(let image):
                DispatchQueue.main.async {
                    self.delegate?.fetchMoviePosterImageSucceded(with: image)
                }
            case .failure(let error):
                self.delegate?.fetchMoviePosterImageFailed(with: error)
                print(error.localizedDescription)
            }
        }
    }
}
