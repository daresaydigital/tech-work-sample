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
    private var refreshControl: UIRefreshControl!

    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.delegate = self
        tableView.dataSource = self
        tableView.prefetchDataSource = self

        tableView.register(UINib(nibName: MovieCell.reuseIdentifier, bundle: nil), forCellReuseIdentifier: MovieCell.reuseIdentifier)
        tableView.estimatedRowHeight = 96

        refreshControl = UIRefreshControl()
        refreshControl.addTarget(self, action: #selector(refreshTable), for: .valueChanged)
        tableView.addSubview(refreshControl)

        viewModel = MovieListViewModel(delegate: self)
        loadingIndicator.startAnimating()
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
        return viewModel.totalNumberOfMoviesForTheSelectedList
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: MovieCell.reuseIdentifier, for: indexPath) as! MovieCell

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
        loadingIndicator.stopAnimating()
        if refreshControl.isRefreshing {
            refreshControl.endRefreshing()
        }
        if let newIndexPaths = newIndexPaths {
            let newIndexPathsToReload = indexPathsToReload(indexPaths: newIndexPaths)
            // Sometimes the movie database API has returned different values for total_results value with different paged responses, in order to crash when the API returns a different value than the
            if viewModel.totalNumberOfMoviesForTheSelectedList != tableView.numberOfRows(inSection: 0) {
                tableView.reloadData()
                setupForTable()
            } else {
                tableView.reloadRows(at: newIndexPathsToReload, with: .automatic)
                if refreshControl.isRefreshing {
                    refreshControl.endRefreshing()
                }
            }
        } else {    
            tableView.reloadData()
            if tableView.alpha == 0 {
                setupForTable()
            }
        }
    }

    func fetchFailed(with error: MovieDatabaseNetworkError) {
        loadingIndicator.stopAnimating()
        if refreshControl.isRefreshing {
            refreshControl.endRefreshing()
        }
        switch error {
        case .invalidURLError:
            emptyStateView.configure(for: .networkError, delegate: self, showButton: true)
            setupForEmptyState()
        case .jsonDecodeError:
            emptyStateView.configure(for: .networkError, delegate: self, showButton: true)
            setupForEmptyState()
        case .responseError:
            emptyStateView.configure(for: .noInternet, delegate: self, showButton: true)
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
            self.tableView.alpha = 0
            self.emptyStateView.alpha = 1
        })
    }

    private func setupForTable() {
        UIView.animate(withDuration: 0.3, animations: {
            self.tableView.alpha = 1
            self.emptyStateView.alpha = 0
        })
    }


    // MARK: - User Actions

    @IBAction  private func didTapSegmentedControl(_ sender: UISegmentedControl) {
        viewModel.switchList(to: sender.selectedSegmentIndex)
        switch segmentedControl.selectedSegmentIndex {
        case 0:
            tableView.setContentOffset(CGPoint(x: 0, y: viewModel.topRatedContentOffset), animated: false)
        case 1:
            tableView.setContentOffset(CGPoint(x: 0, y: viewModel.mostPopularContentOffset), animated: false)
        default:
            break
        }
    }

    @objc func refreshTable() {
        loadingIndicator.startAnimating()
        viewModel.fetchMovies(isRefresh: true)
    }


    // MARK: - EmptyStateDelegate

    func emptyStateButtonTapped() {
        loadingIndicator.startAnimating()
        viewModel.fetchMovies()
        tableView.alpha = 0
        emptyStateView.alpha = 0

    }


    // MARK: - Navigation

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationVC = segue.destination as? MovieDetailsViewController, let movie = sender as? Movie {
            let movieDetailsViewModel = MovieDetailsViewModel(with: movie, delegate: destinationVC)
            destinationVC.viewModel = movieDetailsViewModel
        }
    }


    // MARK: - UIScrollViewDelegate

    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        switch segmentedControl.selectedSegmentIndex {
        case 0:
            viewModel.topRatedContentOffset = scrollView.contentOffset.y
        case 1:
            viewModel.mostPopularContentOffset = scrollView.contentOffset.y
        default:
            break
        }
    }

}
