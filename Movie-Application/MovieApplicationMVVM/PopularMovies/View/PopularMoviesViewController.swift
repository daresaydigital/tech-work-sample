//
//  PopularMoviesViewController.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/4/22.
//

import UIKit

class PopularMoviesViewController: UIViewController, Storyboarded {
    // MARK: - Properties
    var moviesCollectionViewDataSource: MovieCollectionViewDataSource<MovieCell>!

    weak var coordinator: PopularMoviesCoordinator?
    @IBOutlet weak var collectionView: UICollectionView!

    let popularMoviesViewModel = PopularMoviesViewModel(moviesService: MoviesService.shared)
    private let movieImagesCache = NSCache<NSNumber, UIImage>()
    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        popularMoviesViewModel.getPopularMovies()
        configureNavigation()
        setupCollectionView()
        setupBindings()

        self.applyTheme()
    }

    // MARK: - Theme

    func applyTheme() {
        view.backgroundColor = .systemBackground
    }

    // MARK: - Private functions
    
    // bind view to viewModel
    private func setupBindings() {
        popularMoviesViewModel.movies = { [weak self] movies in
            guard let self = self else { return }
            self.moviesCollectionViewDataSource.appendItemsToCollectionView(movies)
        }

        popularMoviesViewModel.errorHandler = { [weak self] error in
            guard let self = self else { return }
            let errorAlert = UIAlertController(title: "Error Occured",
                                               message: error,
                                               preferredStyle: .alert)
            let alertAction = UIAlertAction(title: "Retry", style: .default) { [weak self] (_) in
                self?.popularMoviesViewModel.alertRetryButtonDidTap()
            }
            errorAlert.addAction(alertAction)
            self.present(errorAlert, animated: true, completion: nil)
        }
    }

    // function to configure contextMenu for each collectionView cell
    func configureContextMenu(index: Int, imageData: Data) -> UIContextMenuConfiguration {

        // prevents from adding repititious movies to watch list
        if !popularMoviesViewModel.getSavedMovies().contains(where: { $0.title == popularMoviesViewModel.getMovieTitle(index: index)}) {
            let context = UIContextMenuConfiguration(identifier: nil, previewProvider: nil) { (_) -> UIMenu? in

                let viewDetails = UIAction(title: "View Details",
                                           image: UIImage(systemName: "text.below.photo.fill"),
                                           identifier: nil,
                                           discoverabilityTitle: nil, state: .off) { (_) in
                    self.coordinator?.showMovieDetails(self.popularMoviesViewModel.movieSelected(at: index))
                }

                let addToWatchList = UIAction(title: "Add to Watchlist",
                                              image: UIImage(systemName: "bookmark"),
                                              identifier: nil,
                                              discoverabilityTitle: nil, state: .off) { (_) in
                    self.popularMoviesViewModel.addToWatchList(index: index, imageData: imageData)
                }

                return UIMenu(title: self.popularMoviesViewModel.getMovieTitle(index: index),
                              image: nil, identifier: nil,
                              options: UIMenu.Options.displayInline,
                              children: [addToWatchList, viewDetails])

            }
            return context

        } else {
            let context = UIContextMenuConfiguration(identifier: nil, previewProvider: nil) { (_) -> UIMenu? in

                let viewDetails = UIAction(title: "View Details",
                                           image: UIImage(systemName: "text.below.photo.fill"),
                                           identifier: nil, discoverabilityTitle: nil,
                                           state: .off) { (_) in
                    self.coordinator?.showMovieDetails(self.popularMoviesViewModel.movieSelected(at: index))
                }

                let addToWatchList = UIAction(title: "Added to Watchlist",
                                              image: UIImage(systemName: "bookmark.fill"),
                                              identifier: nil, discoverabilityTitle: nil,
                                              state: .off) { (_) in
                    
                }

                return UIMenu(title: self.popularMoviesViewModel.getMovieTitle(index: index),
                              image: nil, identifier: nil,
                              options: UIMenu.Options.displayInline,
                              children: [addToWatchList, viewDetails])

            }
            return context
        }
    }

    // function to setup and configure navigation details
    private func configureNavigation() {
        coordinator?.navigationController.navigationBar.prefersLargeTitles = true
        self.title = "Popular"
    }

    // function to setup and configure collectionView details
    private func setupCollectionView() {
        moviesCollectionViewDataSource = MovieCollectionViewDataSource(items: [], collectionView: collectionView, delegate: self)
        collectionView.delegate = moviesCollectionViewDataSource
        collectionView.dataSource = moviesCollectionViewDataSource
        collectionView.showsHorizontalScrollIndicator = false
    }
    
    func configurePagination(_ cellRow: Int) {
        if cellRow == popularMoviesViewModel.numberOfMovies - 1 {
            popularMoviesViewModel.getPopularMovies()
        }
    }
}

extension PopularMoviesViewController: MovieCollectionViewDelegate {
    func collection(willDisplay cellIndexPath: IndexPath, cell: UICollectionViewCell) {
        configurePagination(cellIndexPath.row)

        // for caching cell movie image
        guard let cell = cell as? MovieCell else { return }
        let cellNumber = NSNumber(value: cellIndexPath.item)

        if let cachedImage = self.movieImagesCache.object(forKey: cellNumber) {
            cell.movieImageView.image = cachedImage
        } else {
            self.popularMoviesViewModel.getMovieImage(index: cellIndexPath.row, completion: { [weak self] (image) in
                cell.movieImageView.image = image
                self?.movieImagesCache.setObject(image, forKey: cellNumber)
            })
        }
    }
    
    func collection(_ collectionView: UICollectionView, didSelectItem index: IndexPath) {
        
    }
    
    func collection(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
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
    
    func collection(_ collectionView: UICollectionView, contextMenuConfigurationForItemAt indexPath: IndexPath, point: CGPoint) -> UIContextMenuConfiguration? {
        let cellNumber = NSNumber(value: indexPath.item)

        if let cachedImage = self.movieImagesCache.object(forKey: cellNumber) {
            return configureContextMenu(index: indexPath.row,
                                        imageData: cachedImage.jpegData(compressionQuality: 1.0) ?? Data())
        }

        return configureContextMenu(index: indexPath.row, imageData: Data())
    }
    
    
}
