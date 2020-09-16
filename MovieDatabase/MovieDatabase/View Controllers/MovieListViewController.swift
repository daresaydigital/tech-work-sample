//
//  MovieListViewController.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

class MovieListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, MovieListViewModelDelegate, UITableViewDataSourcePrefetching {

    @IBOutlet private weak var tableView: UITableView!
    @IBOutlet private weak var loadingIndicator: UIActivityIndicatorView!
    @IBOutlet private weak var segmentedControl: UISegmentedControl!

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

    // MARK: - UITableViewDelegate

       func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.totalNumberOfMoviesForTheSelectedListType
       }

       func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
           let cell = tableView.dequeueReusableCell(withIdentifier: "MovieCell", for: indexPath) as! MovieCell

           if isLoading(for: indexPath) {
               cell.configure(for: MovieCellConfigureState.loading)
           } else {
            let movie = viewModel.movie(at: indexPath.row)
               cell.configure(for: MovieCellConfigureState.data(movie))
           }
           return cell
       }

       func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
           let movie = viewModel.movie(at: indexPath.row)
        #warning("Add Segue")
       }


       // MARK: - MoviesViewModelDelegate

    func fetchSucceded(with newIndexPaths: [IndexPath]?) {
        if let newIndexPaths = newIndexPaths {
            let newIndexPathsToReload = indexPathsToReload(indexPaths: newIndexPaths)
            if viewModel.totalNumberOfMoviesForTheSelectedListType != tableView.numberOfRows(inSection: 0) {
                tableView.reloadData()
            } else {
                tableView.reloadRows(at: newIndexPathsToReload, with: .automatic)
            }
        } else {
            loadingIndicator.stopAnimating()
            tableView.isHidden = false
            tableView.reloadData()
        }
    }

    func fetchFailed(with errorString: String) {
        print(errorString)
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

        // MARK: - User Actions

    @IBAction func didTapSegmentedControl(_ sender: UISegmentedControl) {
        viewModel.switchList(to: sender.selectedSegmentIndex)
    }

}
