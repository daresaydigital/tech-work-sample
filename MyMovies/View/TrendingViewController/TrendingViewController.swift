//
//  TrendingViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class TrendingViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {

    private var trendingView: TrendingView? = nil
    private var trendingListViewModel: TrendingListViewModel

    init(trendingListViewModel: TrendingListViewModel) {
        self.trendingListViewModel = trendingListViewModel
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    override func loadView() {
        self.trendingView = TrendingView()
        self.trendingView?.collectionViewDelegate = self
        self.trendingView?.collectionDataSource = self
        view = trendingView
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.trendingListViewModel.numberOfRowsInSection
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let trending = trendingListViewModel.getTrending(indexPath.row)

        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "TrendingCollectionViewCell", for: indexPath) as? TrendingCollectionViewCell
        cell?.setupData(model: trending)

        return cell ?? UICollectionViewCell()
    }
}
