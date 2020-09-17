//
//  FavoritesViewController.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

class FavoritesViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, FavoritesViewModelDelegate {

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var loadingIndicator: UIActivityIndicatorView!

    private var viewModel: FavoritesViewModel!

    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.register(UINib(nibName: MovieCell.reuseIdentifier, bundle: nil), forCellReuseIdentifier: MovieCell.reuseIdentifier)

        tableView.delegate = self
        tableView.dataSource = self

        viewModel = FavoritesViewModel(delegate: self)
        viewModel.fetchTopRatedMovies()

        NotificationCenter.default.addObserver(self, selector: #selector(favoritesChanged), name: .FavoritesChangedNotification, object: nil)
    }


    // MARK: - UITableViewDelegate

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.totalNumberOfFavoriteMovies
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MovieCell", for: indexPath) as! MovieCell

        let movie = viewModel.favoriteMovie(at: indexPath.row)
        cell.configure(for: MovieCellConfigureState.data(movie))

        return cell
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let movie = viewModel.favoriteMovie(at: indexPath.row)
        performSegue(withIdentifier: "Show Movie Details Segue", sender: movie)
    }


    // MARK: - FavoritesViewModelDelegate

    func fetchMovies(_ movies: [Movie]) {
        loadingIndicator.stopAnimating()
        tableView.isHidden = false
        tableView.reloadData()
    }


    // MARK: - Notifications
    
    @objc private func favoritesChanged() {
        tableView.reloadData()
    }


    // MARK: - Navigation

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationVC = segue.destination as? MovieDetailsViewController, let movie = sender as? Movie {
            let movieDetailsViewModel = MovieDetailsViewModel(with: movie, delegate: destinationVC)
            destinationVC.viewModel = movieDetailsViewModel
        }
    }

}
