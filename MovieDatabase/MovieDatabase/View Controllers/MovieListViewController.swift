//
//  MovieListViewController.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

class MovieListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, MovieListViewModelDelegate, UITableViewDataSourcePrefetching, EmptyStateDelegate {

    @IBOutlet private weak var tableView: UITableView!
    @IBOutlet private weak var loadingIndicator: UIActivityIndicatorView!
    @IBOutlet private weak var segmentedControl: UISegmentedControl!
    @IBOutlet private weak var emptyStateView: EmptyStateView!

    private var viewModel: MovieListViewModel!

    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.delegate = self
        tableView.dataSource = self
        tableView.prefetchDataSource = self

        tableView.register(UINib(nibName: MovieCell.reuseIdentifier, bundle: nil), forCellReuseIdentifier: MovieCell.reuseIdentifier)

        tableView.estimatedRowHeight = 96

        viewModel = MovieListViewModel(delegate: self)
        viewModel.fetchMovies()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        tableView.indexPathsForSelectedRows?.forEach {
            tableView.deselectRow(at: $0, animated: false)
        }
    }

    // MARK: - UITableViewDelegate

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.totalNumberOfMoviesForTheSelectedListType
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MovieCell", for: indexPath) as! MovieCell

        if isLoading(for: indexPath) {
            cell.configure(for: MovieCellConfigureState.loading, rank: nil)
        } else {
            let movie = viewModel.movie(at: indexPath.row)
            cell.configure(for: MovieCellConfigureState.data(movie), rank: indexPath.row+1)
        }
        return cell
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let movie = viewModel.movie(at: indexPath.row)
        performSegue(withIdentifier: "Movie Details Segue", sender: movie)
    }


    // MARK: - MoviesViewModelDelegate

    func fetchSucceded(with newIndexPaths: [IndexPath]?) {
        if let newIndexPaths = newIndexPaths {
            let newIndexPathsToReload = indexPathsToReload(indexPaths: newIndexPaths)
            if viewModel.totalNumberOfMoviesForTheSelectedListType != tableView.numberOfRows(inSection: 0) {
                tableView.reloadData()
                setupForTable()
            } else {
                tableView.reloadRows(at: newIndexPathsToReload, with: .automatic)
            }
        } else {    
            tableView.reloadData()
            setupForTable()
        }
    }

    func fetchFailed(with error: MovieDatabaseNetworkError) {
        switch error {
        case .invalidURLError:
            emptyStateView.configure(for: .networkError, delegate: self)
            setupForEmptyState()
        case .jsonDecodeError:
            emptyStateView.configure(for: .networkError, delegate: self)
            setupForEmptyState()
        case .responseError:
            emptyStateView.configure(for: .noInternet, delegate: self)
            setupForEmptyState()
        }
    }

    // MARK: - UITableViewDataSourcePrefetching

    func tableView(_ tableView: UITableView, prefetchRowsAt indexPaths: [IndexPath]) {
        if indexPaths.contains(where: { (indexPath) -> Bool in
            isLoading(for: indexPath)
        }) {
            viewModel.fetchMovies()
        }
    }


    // MARK: - Utilities

    func isLoading(for indexPath: IndexPath) -> Bool {
        return indexPath.row >= viewModel.numberOfCurrentlyLoadedMovies
    }

    func indexPathsToReload(indexPaths: [IndexPath]) -> [IndexPath] {
        let visibleRows = tableView.indexPathsForVisibleRows ?? []
        let intersectingIndexPaths = Set(visibleRows).intersection(indexPaths)
        return Array(intersectingIndexPaths)
    }

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

    // MARK: - User Actions

    @IBAction func didTapSegmentedControl(_ sender: UISegmentedControl) {
        viewModel.switchList(to: sender.selectedSegmentIndex)
    }


    // MARK: - EmptyStateDelegate

    func emptyStateButtonTapped() {
        viewModel.fetchMovies()
        tableView.alpha = 0
        emptyStateView.alpha = 0
        loadingIndicator.startAnimating()
    }


    // MARK: - Navigation

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationVC = segue.destination as? MovieDetailsViewController, let movie = sender as? Movie {
            let movieDetailsViewModel = MovieDetailsViewModel(with: movie, delegate: destinationVC)
            destinationVC.viewModel = movieDetailsViewModel
        }
    }

}
