//
//  MovieDetailsViewController.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

class MovieDetailsViewController: UIViewController, MovieDetailsViewModelDelegate {

    @IBOutlet weak var backdropImage: UIImageView!
    @IBOutlet weak var posterImage: UIImageView!

    @IBOutlet weak var movieTitleLabel: UILabel!
    @IBOutlet weak var genreTitleLabel: UILabel!
    @IBOutlet weak var genreLabel: UILabel!
    @IBOutlet weak var ReleaseDateTitleLabel: UILabel!
    @IBOutlet weak var releaseDateLabel: UILabel!
    @IBOutlet weak var overviewLabel: UILabel!
    @IBOutlet weak var backgroundImageLoadingIndicator: UIActivityIndicatorView!
    @IBOutlet weak var posterImageLoadingIndicator: UIActivityIndicatorView!
    @IBOutlet weak var popularityTitleLabel: UILabel!
    @IBOutlet weak var popularityDetailLabel: UILabel!
    @IBOutlet weak var favoriteButton: UIBarButtonItem!

    var viewModel: MovieDetailsViewModel!

    override func viewDidLoad() {
        super.viewDidLoad()

        navigationItem.backBarButtonItem = UIBarButtonItem(title: nil, style: .plain, target: nil, action: nil)

        viewModel.fetchBackdropImage()
        viewModel.fetchPosterImage()
        title = viewModel.title
        setupUI()
    }


    // MARK: - Setup

    private func setupUI() {
        movieTitleLabel.text = viewModel.title
        movieTitleLabel.font = UIFont.preferredFont(for: .headline, weight: .bold)
        genreTitleLabel.text = "Genre"
        genreTitleLabel.font = UIFont.preferredFont(for: .subheadline, weight: .semibold)
        genreLabel.text = viewModel.genre
        ReleaseDateTitleLabel.text = "Release Date"
        ReleaseDateTitleLabel.font = UIFont.preferredFont(for: .subheadline, weight: .semibold)
        releaseDateLabel.text = viewModel.releaseDate
        popularityTitleLabel.font = UIFont.preferredFont(for: .subheadline, weight: .semibold)
        popularityTitleLabel.text = "Rating"
        popularityDetailLabel.attributedText = viewModel.ratings()
        overviewLabel.text = viewModel.overview
    }

    private func updateFavoritesImage() {
        favoriteButton.image = viewModel.isFavorite ? UIImage(systemName: "heart.fill") : UIImage(systemName: "heart")
    }



    // MARK: - MovieDetailsViewModelDelegate

    func fetchMovieBackdropImageSucceded(with image: UIImage) {
        DispatchQueue.main.async {
            self.backgroundImageLoadingIndicator.stopAnimating()
            self.backdropImage.image = image
        }
    }

    func fetchMovieBackdropImageFailed(with error: ImageLoaderError) {
        DispatchQueue.main.async {
            self.backgroundImageLoadingIndicator.stopAnimating()
            self.backdropImage.image = UIImage(named: "ErrorPlaceholer")!
        }
    }

    func fetchMoviePosterImageSucceded(with image: UIImage) {
        DispatchQueue.main.async {
            self.posterImageLoadingIndicator.stopAnimating()
            self.posterImage.image = image
        }
    }

    func fetchMoviePosterImageFailed(with image: ImageLoaderError) {
        DispatchQueue.main.async {
            self.posterImageLoadingIndicator.stopAnimating()
            self.posterImage.image = UIImage(named: "ErrorPlaceholer")!
        }
    }


    // MARK: - Navigation

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationVC = segue.destination as? MovieReviewsViewController, let movieId = sender as? Int {
            destinationVC.movieId = movieId
        }
    }


    // MARK: - User Action

    @IBAction private func didTapReviewsButton(_ sender: Any) {
        performSegue(withIdentifier: "Show Reviews Segue", sender: viewModel.id)
    }

    @IBAction private func didTapFavorite(_ sender: Any) {
        viewModel.editFavorites()
        updateFavoritesImage()
    }
}
