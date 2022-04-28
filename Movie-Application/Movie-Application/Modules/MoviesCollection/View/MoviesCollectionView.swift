//
//  MoviesCollectionView.swift
//  MoviesCollection
//
//  Created by mohannazakizadeh on 4/26/22.
//

import UIKit

final class MoviesCollectionView: UIViewController, ViewInterface {
    
    var presenter: MoviesCollectionPresenterViewInterface!
    
    // MARK: - Properties
    @IBOutlet weak var collectionView: UICollectionView!
    var viewType: ViewType!
    
    // cache for movies image in order to load image for cell from cache
    private let movieImagesCache = NSCache<NSNumber, UIImage>()
    
    // MARK: - Lifecycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupCollectionView()
        
        self.applyTheme()
        self.presenter.viewDidLoad()
    }
    
    // MARK: - Private functions
    private func configureContextMenu(index: Int) -> UIContextMenuConfiguration {
        
        switch viewType {
            
        case .topRated, .popular:
            return configureTopRatedAndPopularContextMenu(index)
            
        case .watchList:
            return configureWatchListContextMenu(index)
        
        default:
            return configureTopRatedAndPopularContextMenu(index)
        }
    }
    
    private func configureWatchListContextMenu(_ index: Int) -> UIContextMenuConfiguration {
        
        let context = UIContextMenuConfiguration(identifier: nil, previewProvider: nil) { (action) -> UIMenu? in
            
            let viewDetails = UIAction(title: "View Details", image: UIImage(systemName: "text.below.photo.fill"), identifier: nil, discoverabilityTitle: nil, state: .off) { (_) in
                self.presenter.showMovieDetails(index)
            }
            let remove = UIAction(title: "Remove from Watchlist", image: UIImage(systemName: "trash"), identifier: nil, discoverabilityTitle: nil,attributes: .destructive, state: .off) { (_) in
                
            }
            
            return UIMenu(title: self.presenter.getMovieTitle(index: index), image: nil, identifier: nil, options: UIMenu.Options.displayInline, children: [viewDetails, remove])
            
        }
        return context
        
    }
    
    private func configureTopRatedAndPopularContextMenu(_ index: Int) -> UIContextMenuConfiguration {
        
        let context = UIContextMenuConfiguration(identifier: nil, previewProvider: nil) { (action) -> UIMenu? in
            
            let viewDetails = UIAction(title: "View Details", image: UIImage(systemName: "text.below.photo.fill"), identifier: nil, discoverabilityTitle: nil, state: .off) { (_) in
                self.presenter.showMovieDetails(index)
            }
            
            let addToWatchList = UIAction(title: "Add to Watchlist", image: UIImage(systemName: "bookmark"), identifier: nil, discoverabilityTitle: nil, state: .off) { (_) in
                self.presenter.addToWatchList(index)
            }
            
            
            return UIMenu(title: self.presenter.getMovieTitle(index: index), image: nil, identifier: nil, options: UIMenu.Options.displayInline, children: [addToWatchList, viewDetails])
            
        }
        return context
        
    }
    
    private func setupCollectionView() {
        collectionView.delegate = self
        collectionView.dataSource = self
        collectionView.register(MovieCell.self, forCellWithReuseIdentifier: "MovieCell")
    }
    
    // MARK: - Theme
    
    func applyTheme() {
        
    }
    
    
    // MARK: - Actions
    
    
}

extension MoviesCollectionView: MoviesCollectionViewInterface {
    
}


// MARK: - collection view methods
extension MoviesCollectionView: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        presenter.numberOfMovies()
    }
    
    func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        guard let cell = cell as? MovieCell else { return }
        let cellNumber = NSNumber(value: indexPath.item)
        
        if let cachedImage = self.movieImagesCache.object(forKey: cellNumber) {
            cell.movieImageView.image = cachedImage
        } else {
            self.presenter.getMovieImage(index: indexPath.row, completion: { [weak self] (image) in
                cell.movieImageView.image = image
                self?.movieImagesCache.setObject(image, forKey: cellNumber)
            })
            
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "MovieCell", for: indexPath) as? MovieCell else { return UICollectionViewCell() }
        cell.layer.cornerRadius = 10
        return cell
    }
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        1
    }
    
    func collectionView(_ collectionView: UICollectionView, contextMenuConfigurationForItemAt indexPath: IndexPath, point: CGPoint) -> UIContextMenuConfiguration? {
        configureContextMenu(index: indexPath.row)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let noOfCellsInRow = 2

        let flowLayout = collectionViewLayout as! UICollectionViewFlowLayout
        flowLayout.minimumInteritemSpacing = 10
        flowLayout.minimumLineSpacing = 10
        flowLayout.sectionInset = UIEdgeInsets(top: 10, left: 10, bottom: 10, right: 10)

        let totalSpace = flowLayout.sectionInset.left
        + flowLayout.sectionInset.right
        + (flowLayout.minimumInteritemSpacing * CGFloat(noOfCellsInRow - 1))

        let size = Int((view.bounds.width - totalSpace) / CGFloat(noOfCellsInRow))
        return CGSize(width: size, height: size + 50)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        presenter.showMovieDetails(indexPath.row)
    }
}

