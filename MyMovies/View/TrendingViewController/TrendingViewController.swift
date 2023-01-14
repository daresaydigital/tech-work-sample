//
//  TrendingViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class TrendingViewController: UIViewController, Coordinating {

    var coordinator: Coordinator?

    private var trendingView: TrendingView? = nil
    private var trendingListViewModel: TrendingListViewModel?
    private var topRatedListViewModel: TopRatedListViewModel?

    init(
        trendingListViewModel: TrendingListViewModel? = nil,
        topRatedListViewModel: TopRatedListViewModel? = nil,
        coordinator: Coordinator
    ) {
        self.trendingListViewModel = trendingListViewModel
        self.topRatedListViewModel = topRatedListViewModel
        self.coordinator = coordinator
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    override func loadView() {
        self.trendingView = TrendingView()
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
    }
}

extension TrendingViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.trendingListViewModel?.numberOfRowsInSection ??
                self.topRatedListViewModel?.numberOfRowsInSection ?? 0
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let trending = trendingListViewModel?.getTrending(indexPath.row) ??
                topRatedListViewModel?.getTrending(indexPath.row) else { return UICollectionViewCell() }

        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: TrendingCollectionViewCell.identifier, for: indexPath) as? TrendingCollectionViewCell
        cell?.setupData(model: trending)

        let swipeUpsideDown = UISwipeGestureRecognizer(target: self, action: #selector(favorite(_:)))
        swipeUpsideDown.direction = .down
        cell?.addGestureRecognizer(swipeUpsideDown)

        return cell ?? UICollectionViewCell()
    }

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard let trending = trendingListViewModel?.getTrending(indexPath.row) ??
                topRatedListViewModel?.getTrending(indexPath.row),
        let movieId = trending.movieId else { return }

        coordinator?.eventOccurred(with: Event.movieClicked, parameters: movieId)
    }

    @objc func favorite(_ sender: UISwipeGestureRecognizer) {
        guard let cell = sender.view as? TrendingCollectionViewCell else {
            return
        }

        cell.animateFavorite()

        self.trendingListViewModel?.insertFavorite(for: cell.getMovieId())
        self.topRatedListViewModel?.insertFavorite(for: cell.getMovieId())
    }
}
