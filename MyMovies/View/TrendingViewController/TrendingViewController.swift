//
//  TrendingViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class TrendingViewController: UIViewController {

    private var trendingView: TrendingView? = nil
    private var trendingListViewModel: TrendingListViewModel?
    private var topRatedListViewModel: TopRatedListViewModel?

    init(trendingListViewModel: TrendingListViewModel? = nil, topRatedListViewModel: TopRatedListViewModel? = nil) {
        self.trendingListViewModel = trendingListViewModel
        self.topRatedListViewModel = topRatedListViewModel
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

        return cell ?? UICollectionViewCell()
    }
}
