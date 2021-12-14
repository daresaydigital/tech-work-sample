//
//  DaMoviesCollectionViewDataSource.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import UIKit

/*
 
 This is DaMoviesCollectionViewDataSource responsible for data source of simple collectionViews.
 
 You can easily add new datas to array by confirming your UICollectionViewCell to DaMoviesCollectionViewCell.
 */

protocol DaMoviesCollectionViewDelegate: class {
    func collection(willDisplay cellIndexPath: IndexPath, cell: UICollectionViewCell)
    func collection(_ collectionView: UICollectionView, didSelectItem index: IndexPath)
    func collection(_ collectionView: UICollectionView, didDeselectItemAt index: IndexPath)
    func colelction<T>(didSelectModelAt model: T)
    func scrollDidEndDragging(_ scrollView: UIScrollView, willDecelerate: Bool)
}

extension DaMoviesCollectionViewDelegate {
    func collection(willDisplay cellIndexPath: IndexPath, cell: UICollectionViewCell) { }
    func collection(_ collectionView: UICollectionView, didSelectItem index: IndexPath) { }
    func collection(_ collectionView: UICollectionView, didDeselectItemAt index: IndexPath) { }
    func colelction<T>(didSelectModelAt model: T) { }
    func scrollDidEndDragging(_ scrollView: UIScrollView, willDecelerate: Bool) { }
}

class DaMoviesCollectionViewDataSource<T: DaMoviesCollectionViewCell>: NSObject, UICollectionViewDataSource, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout {
    // MARK: - Variables
    var items: [T.CellViewModel] = []
    var selectItem: IndexPath?
    var collectionView: UICollectionView
    
    weak var delegate: DaMoviesCollectionViewDelegate?
    
    // MARK: - Initializer
    init(items: [T.CellViewModel], collectionView: UICollectionView, delegate: DaMoviesCollectionViewDelegate) {
        self.items = items
        self.collectionView = collectionView
        // Register cell to collectionView
        self.collectionView.register(UINib(nibName: String.init(describing: T.self), bundle: nil), forCellWithReuseIdentifier: String.init(describing: T.self))
        self.delegate = delegate
    }
    
    // MARK: - UICollectionView DataSource
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return items.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: String.init(describing: T.self), for: indexPath) as! T
        cell.configureCellWith(items[indexPath.row])
        return cell
    }
    
    // MARK: - UICollectionView Delegate
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        delegate?.collection(collectionView, didSelectItem: indexPath)
        delegate?.colelction(didSelectModelAt: items[indexPath.row])
    }
    
    func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        let cell = cell as! T
        delegate?.collection(willDisplay: indexPath, cell: cell)
    }
    
    // MARK: - ScrollView Delegate
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        delegate?.scrollDidEndDragging(scrollView, willDecelerate: decelerate)
    }
    
    // MARK: - UICollectionView Delegate FlowLayout
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let item = items[indexPath.row]
        let baseCell = collectionView.dequeueReusableCell(withReuseIdentifier: String(describing: T.self), for: indexPath)
        let cell = baseCell as! T
        let size = cell.configCellSize(item: item)
        return size
    }
    
    public func appendItemsToCollectionView( _ newItems: [T.CellViewModel]) {
          // append to last of list
          self.items.append(contentsOf: newItems)
          // Now performing insert
        
          // Get the last row index (numberOfRows - 1)
          var lastRowIndex = collectionView.numberOfItems(inSection: 0) - 1
          if lastRowIndex < 0 {
              lastRowIndex = 0
              self.collectionView.reloadData()
          } else {
              let indexPaths = newItems.enumerated().map { (index, _) -> IndexPath in
                  IndexPath(item: items.count - 1 - index, section: 0)
              }
              self.collectionView.performBatchUpdates({
                  self.collectionView.insertItems(at: indexPaths)
              }, completion: nil)
        }
    }

    public func refreshWithNewItems(_ newItems: [T.CellViewModel]) {
        
        self.items = newItems
        self.collectionView.reloadData()
    }
}
