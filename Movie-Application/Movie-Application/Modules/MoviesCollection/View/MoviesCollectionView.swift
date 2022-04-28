//
//  MoviesCollectionView.swift
//  MoviesCollection
//
//  Created by mohannazakizadeh on 4/26/22.
//

import UIKit

final class MoviesCollectionView: UICollectionViewController, ViewInterface {
    
    var presenter: MoviesCollectionPresenterViewInterface!
    
    // MARK: - Properties
    var viewType: ViewType!
    
    init() {
        super.init(collectionViewLayout: UICollectionViewFlowLayout())
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    // MARK: - Lifecycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configureCollectionView()
        
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
    
    private func configureCollectionView() {
        let layout = UICollectionViewFlowLayout()
        let collectionView = UICollectionView(frame: CGRect.zero, collectionViewLayout: layout)
        collectionView.delegate = self
        collectionView.dataSource = self
        view.addSubview(collectionView)
    }
    
    // MARK: - Theme
    
    func applyTheme() {
        
    }
    
    
    // MARK: - Actions
    
    
}

extension MoviesCollectionView: MoviesCollectionViewInterface {
    
}


// MARK: - collection view methods
extension MoviesCollectionView: UICollectionViewDelegateFlowLayout {
    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        presenter.numberOfMovies()
    }
    
    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = UICollectionViewCell()
        cell.largeContentImage = presenter.getMovieImage(index: indexPath.row)
        cell.layer.cornerRadius = 10
        return cell
    }
    
    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        1
    }
    
    override func collectionView(_ collectionView: UICollectionView, contextMenuConfigurationForItemAt indexPath: IndexPath, point: CGPoint) -> UIContextMenuConfiguration? {
        configureContextMenu(index: indexPath.row)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let noOfCellsInRow = 2
        let flowLayout = collectionViewLayout as! UICollectionViewFlowLayout
        let totalSpace = flowLayout.sectionInset.left
            + flowLayout.sectionInset.right
            + (flowLayout.minimumInteritemSpacing * CGFloat(noOfCellsInRow - 1))

        let size = Int((view.bounds.width - totalSpace) / CGFloat(noOfCellsInRow))
        return CGSize(width: size, height: size + 50)
    }
}

