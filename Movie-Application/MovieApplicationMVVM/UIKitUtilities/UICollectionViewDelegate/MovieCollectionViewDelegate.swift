//
//  MovieCollectionViewDelegate.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/3/22.
//

import UIKit

protocol MovieCollectionViewDelegate: AnyObject {
    func collection(willDisplay cellIndexPath: IndexPath, cell: UICollectionViewCell)
    func collection(_ collectionView: UICollectionView, didSelectItem index: IndexPath)
    func collection(_ collectionView: UICollectionView,
                    layout collectionViewLayout: UICollectionViewLayout,
                    sizeForItemAt indexPath: IndexPath) -> CGSize
    func collection(_ collectionView: UICollectionView,
                    contextMenuConfigurationForItemAt indexPath: IndexPath,
                    point: CGPoint) -> UIContextMenuConfiguration?
}

extension MovieCollectionViewDelegate {
    func collection(willDisplay cellIndexPath: IndexPath, cell: UICollectionViewCell) {}
}

class MovieCollectionViewDataSource<T: MovieCollectionViewCell>: NSObject, UICollectionViewDataSource,
                                                                    UICollectionViewDelegate,
                                                                    UICollectionViewDelegateFlowLayout {
    // MARK: - Variables
    var items: [T.CellViewModel] = []
    var selectItem: IndexPath?
    var collectionView: UICollectionView

    weak var delegate: MovieCollectionViewDelegate?

    // MARK: - Initializer
    init(items: [T.CellViewModel], collectionView: UICollectionView, delegate: MovieCollectionViewDelegate) {
        self.items = items
        self.collectionView = collectionView
        // Register cell to collectionView
        self.collectionView.register(T.self, forCellWithReuseIdentifier: String.init(describing: T.self))
        self.delegate = delegate
    }

    // MARK: - UICollectionView DataSource
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return items.count
    }

    func collectionView(_ collectionView: UICollectionView,
                        cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier:
                                                                String.init(describing: T.self), for: indexPath)
                as? T else {
            return UICollectionViewCell()
        }
        return cell
    }

    // MARK: - UICollectionView Delegate
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        delegate?.collection(collectionView, didSelectItem: indexPath)
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

    func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        sizeForItemAt indexPath: IndexPath) -> CGSize {
        delegate?.collection(collectionView,
                             layout: collectionViewLayout,
                             sizeForItemAt: indexPath) ?? CGSize(width: 50, height: 50)
    }

    func collectionView(_ collectionView: UICollectionView,
                        willDisplay cell: UICollectionViewCell,
                        forItemAt indexPath: IndexPath) {
        delegate?.collection(willDisplay: indexPath, cell: cell)
    }

    func collectionView(_ collectionView: UICollectionView,
                        contextMenuConfigurationForItemAt indexPath: IndexPath,
                        point: CGPoint) -> UIContextMenuConfiguration? {
        delegate?.collection(collectionView, contextMenuConfigurationForItemAt: indexPath, point: point)
    }

}
