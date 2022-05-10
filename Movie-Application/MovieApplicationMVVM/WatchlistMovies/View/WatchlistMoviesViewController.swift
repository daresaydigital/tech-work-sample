//
//  WatchlistMoviesViewController.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/5/22.
//

import UIKit

final class WatchlistMoviesViewController: UIViewController, Storyboarded {
    // MARK: - Properties
    var moviesCollectionViewDataSource: MovieCollectionViewDataSource<MovieCell>!

    weak var coordinator: WatchlistMoviesCoordinator?
    var viewModel: WatchlistMoviesViewModel!

    @IBOutlet var emptyWatchlistView: UIStackView!
    @IBOutlet var collectionView: UICollectionView!

    @IBAction func browseButtonDidTap(_ sender: UIButton) {
        coordinator?.changeTabBarIndex(to: 0)
    }

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        viewModel.getWatchlistMovies()
        setupView()
    }

    override func viewWillAppear(_ animated: Bool) {
        viewModel.getWatchlistMovies()
    }

    override func viewWillDisappear(_ animated: Bool) {
        viewModel.deleteMovies()
    }
    // MARK: - Theme

    func applyTheme() {
        view.backgroundColor = .systemBackground
    }

    // MARK: - Private functions

    private func setupView() {
        configureNavigation()
        setupCollectionView()
        setupBindings()
        self.applyTheme()
    }

    // bind view to viewModel
    private func setupBindings() {
        viewModel.movies = { [weak self] coreDataMovies in
            guard let self = self else { return }
            if coreDataMovies.isEmpty {
                self.emptyWatchlistView.isHidden = false
                self.collectionView.isHidden = true
            } else {
                self.emptyWatchlistView.isHidden = true
                self.collectionView.isHidden = false
                let movies = coreDataMovies.map({  Movie(title: $0.title,
                                                         poster: nil, id: $0.id,
                                                         voteAverage: $0.voteAverage)})
                self.moviesCollectionViewDataSource.refreshWithNewItems(movies)
            }
        }

        viewModel.movieDetails = { [weak self] movieDetail in
            guard let self = self else { return }
            self.coordinator?.showMovieDetails(movieDetail)
        }

        viewModel.errorHandler = { [weak self] error in
            guard let self = self else { return }
            let errorAlert = UIAlertController(title: "Error Occured",
                                               message: error,
                                               preferredStyle: .alert)
            let errorAction = UIAlertAction(title: "Ok", style: .default, handler: nil)
            errorAlert.addAction(errorAction)
            self.present(errorAlert, animated: true, completion: nil)
        }
    }

    // function to setup and configure navigation details
    private func configureNavigation() {
        self.title = "Watchlist"
        let dateAddedAction = UIAction(title: "Date Added",
                                       image: nil, identifier: nil,
                                       discoverabilityTitle: nil,
                                       state: .off) { (_) in
            self.viewModel.sortByDate()
        }

        let nameSortAction = UIAction(title: "Name",
                                      image: nil, identifier: nil,
                                      discoverabilityTitle: nil,
                                      state: .off) { (_) in
            self.viewModel.sortByName()
        }

        let userScoreSortAction = UIAction(title: "User Score",
                                           image: nil, identifier: nil,
                                           discoverabilityTitle: nil,
                                           state: .off) { (_) in
            self.viewModel.sortByUserScore()
        }

    let sortMenu = UIMenu(title: "",
                          image: nil, identifier: nil,
                          options: .singleSelection,
                          children: [dateAddedAction, nameSortAction, userScoreSortAction])

        navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Sort",
                                                            image: nil,
                                                            primaryAction: nil,
                                                            menu: sortMenu)
    }

    // function to setup and configure collectionView details
    private func setupCollectionView() {
        moviesCollectionViewDataSource = MovieCollectionViewDataSource(items: [],
                                                                       collectionView: collectionView,
                                                                       delegate: self)
        collectionView.delegate = moviesCollectionViewDataSource
        collectionView.dataSource = moviesCollectionViewDataSource
        collectionView.showsHorizontalScrollIndicator = false
    }

    private func configureContextMenu(_ index: Int) -> UIContextMenuConfiguration {
        viewModel.configureContextMenu(index: index)
    }
}

extension WatchlistMoviesViewController: MovieCollectionViewDelegate {

    func collection(_ collectionView: UICollectionView, didSelectItem index: IndexPath) {
        self.viewModel.movieSelected(at: index.row)
    }

    func collection(_ collectionView: UICollectionView,
                    layout collectionViewLayout: UICollectionViewLayout,
                    sizeForItemAt indexPath: IndexPath) -> CGSize {
        let noOfCellsInRow = 2

        guard let flowLayout = collectionViewLayout as? UICollectionViewFlowLayout else {
            return CGSize(width: 0, height: 0)
        }
        flowLayout.minimumInteritemSpacing = 10
        flowLayout.minimumLineSpacing = 10
        flowLayout.sectionInset = UIEdgeInsets(top: 10, left: 10, bottom: 10, right: 10)

        let totalSpace = flowLayout.sectionInset.left
        + flowLayout.sectionInset.right
        + (flowLayout.minimumInteritemSpacing * CGFloat(noOfCellsInRow - 1))

        let size = Int((view.bounds.width - totalSpace) / CGFloat(noOfCellsInRow))
        return CGSize(width: size, height: size + 50)
    }

    func collection(_ collectionView: UICollectionView,
                    contextMenuConfigurationForItemAt indexPath: IndexPath,
                    point: CGPoint) -> UIContextMenuConfiguration? {
        configureContextMenu(indexPath.row)
    }

    func collection(willDisplay cellIndexPath: IndexPath, cell: UICollectionViewCell) {
        guard let cell = cell as? MovieCell else { return }
        cell.movieImageView.image = viewModel.getMovieImage(index: cellIndexPath.row)
    }

}
