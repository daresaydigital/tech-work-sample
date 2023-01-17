//
//  MovieListViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class MovieListViewController: UIViewController, Coordinating {

    // MARK: - Properties

    var coordinator: Coordinator?

    private var trendingView: MovieListView? = nil
    private var trendingListViewModel: TrendingListViewModel?
    private var topRatedListViewModel: TopRatedListViewModel?
    private var favoriteViewModel: FavoriteViewModel?

    // MARK: - Initializer

    init(
        trendingListViewModel: TrendingListViewModel? = nil,
        topRatedListViewModel: TopRatedListViewModel? = nil,
        favoriteViewModel: FavoriteViewModel? = nil,
        coordinator: Coordinator
    ) {
        self.trendingListViewModel = trendingListViewModel
        self.topRatedListViewModel = topRatedListViewModel
        self.favoriteViewModel = favoriteViewModel
        self.coordinator = coordinator
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    // MARK: - View Lifecycle

    override func loadView() {
        self.trendingView = MovieListView()
        self.trendingView?.collectionDataSource = self
        self.trendingView?.collectionViewDelegate = self
        view = trendingView
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        trendingView?.renderLoadingState()

        trendingListViewModel?.fetchTrendings(
            of: TrendingParams(mediaType: .movie, timeWindow: .day)
        ) { [weak self] trendingListViewModel, error in
            guard let self = self else {
                return
            }

            if error != nil {
                self.trendingView?.renderErrorState()
            } else if let trendingListViewModel = trendingListViewModel {
                self.trendingListViewModel = trendingListViewModel
                self.trendingView?.renderSuccessState(with: trendingListViewModel.titlePage)
            }
        }

        topRatedListViewModel?.fetchTopRated { [weak self] topRatedListViewModel, error in
            guard let self = self else {
                return
            }

            if error != nil {
                self.trendingView?.renderErrorState()
            } else if let topRatedListViewModel = topRatedListViewModel {
                self.topRatedListViewModel = topRatedListViewModel
                self.trendingView?.renderSuccessState(with: topRatedListViewModel.titlePage)
            }
        }

        favoriteViewModel?.fetchFavorites { [weak self] favoriteViewModel in
            guard let self = self else {
                return
            }

            if let favoriteViewModel = favoriteViewModel {
                self.favoriteViewModel = favoriteViewModel
                self.trendingView?.renderFavoriteState(with: favoriteViewModel)
            }
        }
    }
}

// MARK: - UICollectionViewDelegate and UICollectionViewDataSource implementation

extension MovieListViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.trendingListViewModel?.numberOfRowsInSection ??
                self.topRatedListViewModel?.numberOfRowsInSection ??
                self.favoriteViewModel?.numberOfRowsInSection ?? 0
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let trending = trendingListViewModel?.getTrending(indexPath.row) ??
                topRatedListViewModel?.getTrending(indexPath.row) ??
                favoriteViewModel?.getTrending(indexPath.row) else { return UICollectionViewCell() }

        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: MovieListCollectionViewCell.identifier, for: indexPath) as? MovieListCollectionViewCell
        cell?.setupData(model: trending)

        let swipeUpsideDown = UISwipeGestureRecognizer(target: self, action: #selector(favorite(_:)))
        swipeUpsideDown.direction = .down
        cell?.addGestureRecognizer(swipeUpsideDown)

        let swipeLeftToRight = UISwipeGestureRecognizer(target: self, action: #selector(favorite(_:)))
        swipeLeftToRight.direction = .right
        cell?.addGestureRecognizer(swipeLeftToRight)

        return cell ?? UICollectionViewCell()
    }

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard let trending = trendingListViewModel?.getTrending(indexPath.row) ??
                topRatedListViewModel?.getTrending(indexPath.row) ??
              favoriteViewModel?.getTrending(indexPath.row),
        let movieId = trending.movieId else { return }

        coordinator?.eventOccurred(with: Event.movieClicked, parameters: movieId)
    }

    @objc func favorite(_ sender: UISwipeGestureRecognizer) {
        guard let cell = sender.view as? MovieListCollectionViewCell else {
            return
        }

        cell.animateFavorite(for: sender.direction)

        self.trendingListViewModel?.insertFavorite(for: cell.getMovieId())
        self.topRatedListViewModel?.insertFavorite(for: cell.getMovieId())

        DispatchQueue.main.asyncAfter(deadline: .now() + 0.50) {
            self.favoriteViewModel?.removeFavorite(for: cell.getMovieId())
            if let favoriteViewModel = self.favoriteViewModel {
                self.trendingView?.renderFavoriteState(with: favoriteViewModel)
            }
        }
    }
}
