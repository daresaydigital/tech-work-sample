//
//  FavoritesViewController.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

class FavoritesViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, FavoritesViewModelDelegate, EmptyStateDelegate {

    @IBOutlet private weak var tableView: UITableView!
    @IBOutlet private weak var loadingIndicator: UIActivityIndicatorView!
    @IBOutlet private weak var emptyStateView: EmptyStateView!

    private var viewModel: FavoritesViewModel!

    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.register(UINib(nibName: MovieCell.reuseIdentifier, bundle: nil), forCellReuseIdentifier: MovieCell.reuseIdentifier)

        tableView.delegate = self
        tableView.dataSource = self

        viewModel = FavoritesViewModel(delegate: self)
        viewModel.fetchFavorites()

        title = "My Favorites"

        NotificationCenter.default.addObserver(self, selector: #selector(favoritesChanged), name: .FavoritesChangedNotification, object: nil)
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        tableView.indexPathsForSelectedRows?.forEach {
            tableView.deselectRow(at: $0, animated: false)
        }
    }


    // MARK: - UITableViewDelegate

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.totalNumberOfFavoriteMovies
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MovieCell", for: indexPath) as! MovieCell

        let movie = viewModel.favoriteMovie(at: indexPath.row)
        cell.configure(for: MovieCellConfigureState.data(movie), rank: indexPath.row+1)

        return cell
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let movie = viewModel.favoriteMovie(at: indexPath.row)
        performSegue(withIdentifier: "Show Movie Details Segue", sender: movie)
    }


    // MARK: - FavoritesViewModelDelegate

    func fetchFavoriteMovies() {
        if viewModel.totalNumberOfFavoriteMovies == 0 {
            emptyStateView.configure(for: .noFavorites, delegate: self)
            setupForEmptyState()
        } else {
            setupForTable()
            loadingIndicator.stopAnimating()
            tableView.isHidden = false
            tableView.reloadData()
        }
    }


    // MARK: - EmptyStateViewDelegate

    func emptyStateButtonTapped() {
        tableView.reloadData()
    }


    // MARK: - Notifications
    
    @objc private func favoritesChanged() {
        tableView.reloadData()
    }


    // MARK: - Utilities

    private func setupForEmptyState() {
        emptyStateView.alpha = 0
        UIView.animate(withDuration: 0.3, animations: {
            self.loadingIndicator.stopAnimating()
            self.tableView.alpha = 0
            self.emptyStateView.alpha = 1
        })
    }

    private func setupForTable() {
        tableView.alpha = 0
        UIView.animate(withDuration: 0.3, animations: {
            self.loadingIndicator.stopAnimating()
            self.tableView.alpha = 1
            self.emptyStateView.alpha = 0
        })
    }


    // MARK: - Navigation

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationVC = segue.destination as? MovieDetailsViewController, let movie = sender as? Movie {
            let movieDetailsViewModel = MovieDetailsViewModel(with: movie, delegate: destinationVC)
            destinationVC.viewModel = movieDetailsViewModel
        }
    }

}
