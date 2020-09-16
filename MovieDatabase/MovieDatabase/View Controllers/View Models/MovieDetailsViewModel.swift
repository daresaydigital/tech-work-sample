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

    var delegate: MovieDetailsViewModelDelegate?

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
            NSAttributedString.Key.foregroundColor : UIColor(red: 1, green: 0.6705882353, blue: 0, alpha: 1),
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
        ImageService.getImage(url: backgroundImageURL) { [weak self](result) in
            switch result {
            case .success(let image):
                DispatchQueue.main.async {
                    self?.delegate?.fetchMovieBackdropImageSucceded(with: image)
                }
            case .failure(let error):
                self?.delegate?.fetchMovieBackdropImageFailed(with: error)
                print(error.localizedDescription)
            }
        }
    }

    func fetchPosterImage() {
        ImageService.getImage(url: posterURL) { [weak self](result) in
            switch result {
            case .success(let image):
                DispatchQueue.main.async {
                    self?.delegate?.fetchMoviePosterImageSucceded(with: image)
                }
            case .failure(let error):
                self?.delegate?.fetchMoviePosterImageFailed(with: error)
                print(error.localizedDescription)
            }
        }
    }
}
