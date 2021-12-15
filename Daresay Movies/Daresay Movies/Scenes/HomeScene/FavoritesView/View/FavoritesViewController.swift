//
//  FavoritesViewController.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/15/21.
//

import UIKit

class FavoritesViewController: UIViewController, Storyboarded {
    
    // MARK: - Coordinator
    weak var coordinator: HomeCoordinator?
    
    // MARK: - IBOutlets
    @IBOutlet var collectionView: UICollectionView!
    
    // MARK: - Properties
    var favoriteList: MovieArrayModel!
    private var moviesDataSource: DaMoviesCollectionViewDataSource<HomeMovieCell>!
    
    // MARK: - LifeCycle
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    // MARK: - View
    private func setupView() {
        guard favoriteList != nil else {
            assertionFailure("Fill FavoriteList")
            return
        }
        
        self.title = LocalizedStrings.test.value
        
        moviesDataSource = DaMoviesCollectionViewDataSource(items: favoriteList, collectionView: collectionView, delegate: self)
        collectionView.dataSource = moviesDataSource
        collectionView.delegate = moviesDataSource
        collectionView.contentInset.top = 10
    }
}

// MARK: - DaMoviesCollectionViewDelegate
extension FavoritesViewController: DaMoviesCollectionViewDelegate {
    
    func collection(willDisplay cellIndexPath: IndexPath, cell: UICollectionViewCell) {
        guard let cell = cell as? HomeMovieCell else { return }
        cell.delegate = self
        cell.index = cellIndexPath.item
    }
    
    func colelction<T>(didSelectModelAt model: T) {
        guard let model = model as? MovieModel else { return }
        coordinator?.showDetail(model: model)
    }
    
}

extension FavoritesViewController: MovieCellDelagate {
    func isFaved(_ isFaved: Bool, model: MovieModel, cellIndex: Int) {
        print("isFAVED: ====", isFaved, model, cellIndex)
        isFaved ? FavoriteMoviesHandler.shared.fave(model) : FavoriteMoviesHandler.shared.unfave(model)
        moviesDataSource.items[cellIndex].isFaved = isFaved
        moviesDataSource.collectionView.reloadData()
    }
}
