//
//  MovieReviewsViewController.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

class MovieReviewsViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UITableViewDataSourcePrefetching, MovieReviewsViewModelDelegate {

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var loadingIndicator: UIActivityIndicatorView!

    var movieId: Int!
    private var viewModel: MovieReviewsViewModel!

    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.dataSource = self
        tableView.delegate = self
        tableView.prefetchDataSource = self

        tableView.register(UINib(nibName: ReviewCell.reuseIdentifier, bundle: nil), forCellReuseIdentifier: ReviewCell.reuseIdentifier)

        tableView.estimatedRowHeight = 124

        title = "Reviews"

        viewModel = MovieReviewsViewModel(id: movieId, delegate: self)
        viewModel.fetchMovieReviews()
    }


    // MARK: - UITableViewDelegate

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.totalCountReviews
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: ReviewCell.reuseIdentifier, for: indexPath) as! ReviewCell

        if isLoading(for: indexPath) {
            cell.configure(for: ReviewCellConfigureState.loading)
        } else {
            let review = viewModel.review(at: indexPath.row)
            cell.configure(for: ReviewCellConfigureState.data(review))
        }
        return cell
    }


    // MARK: - UITableViewDataSourcePrefetching

    func tableView(_ tableView: UITableView, prefetchRowsAt indexPaths: [IndexPath]) {
        if indexPaths.contains(where: { (indexPath) -> Bool in
            isLoading(for: indexPath)
        }) {
            viewModel.fetchMovieReviews()
        }
    }


    // MARK: - MovieReviewsViewModelDelegate

    func fetchSucceded(with newIndexPaths: [IndexPath]?) {
        if let newIndexPaths = newIndexPaths {
            let newIndexPathsToReload = indexPathsToReload(indexPaths: newIndexPaths)
            tableView.reloadRows(at: newIndexPathsToReload, with: .automatic)
        } else {
            loadingIndicator.stopAnimating()
            tableView.isHidden = false
            tableView.reloadData()
        }
    }

    func fetchFailed(with error: MovieReviewsError) {
        switch error {
        case .invalidURLError, .jsonDecodeError, .networkError:
            break
        case .noReviews:
            showAlert()
        }
    }
    

    // MARK: - Utilities

    private func showAlert() {
        let alert = UIAlertController(title: "No Reviews", message: "No Reviews Yet!", preferredStyle: UIAlertController.Style.alert)

        alert.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: { _ in
            //Cancel Action
        }))
        self.present(alert, animated: true, completion: nil)
    }

    private func isLoading(for indexPath: IndexPath) -> Bool {
        return indexPath.row >= viewModel.numberOfCurrentlyLoadedReviews
    }

    private func indexPathsToReload(indexPaths: [IndexPath]) -> [IndexPath] {
        let visibleRows = tableView.indexPathsForVisibleRows ?? []
        let intersectingIndexPaths = Set(visibleRows).intersection(indexPaths)
        return Array(intersectingIndexPaths)
    }

}
