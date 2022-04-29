//
//  WatchlistMoviesView.swift
//  WatchlistMovies
//
//  Created by mohannazakizadeh on 4/29/22.
//

import UIKit

final class WatchlistMoviesView: UIViewController, ViewInterface {
	
	var presenter: WatchlistMoviesPresenterViewInterface!
	
	// MARK: - Properties
    @IBOutlet weak var collectionView: UICollectionView!
    
	// MARK: - Initialize

	
	// MARK: - Lifecycle
	
    override func viewDidLoad() {
        super.viewDidLoad()
        configureNavigation()
        setupCollectionView()
        
        self.applyTheme()
        self.presenter.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        presenter.getWatchlistMovies()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        presenter.deleteMovies()
    }
    // MARK: - Theme
    
    func applyTheme() {
        view.backgroundColor = .systemBackground
    }
    
    // MARK: - Private functions
    
    // function to setup and configure navigation details
    private func configureNavigation() {
        navigationController?.navigationBar.prefersLargeTitles = true
        self.title = "Watchlist"
    }
    
    // function to setup and configure collectionView details
    private func setupCollectionView() {
        collectionView.delegate = self
        collectionView.dataSource = self
        collectionView.register(MovieCell.self, forCellWithReuseIdentifier: "MovieCell")
    }
    
    private func configureContextMenu(_ index: Int) -> UIContextMenuConfiguration {
            
            let context = UIContextMenuConfiguration(identifier: nil, previewProvider: nil) { (action) -> UIMenu? in
                
                let viewDetails = UIAction(title: "View Details", image: UIImage(systemName: "text.below.photo.fill"), identifier: nil, discoverabilityTitle: nil, state: .off) { (_) in
                    self.presenter.showMovieDetails(index)
                }
                let remove = UIAction(title: "Remove from Watchlist", image: UIImage(systemName: "trash"), identifier: nil, discoverabilityTitle: nil,attributes: .destructive, state: .off) { (_) in
                    self.presenter.deletefromWatchList(index)
                }
                
                return UIMenu(title: self.presenter.getMovieTitle(index: index), image: nil, identifier: nil, options: UIMenu.Options.displayInline, children: [viewDetails, remove])
                
            }
            return context
            
        }
	
	// MARK: - Actions
	
	
}

extension WatchlistMoviesView: WatchlistMoviesViewInterface {
    
    func reloadCollectionView() {
        collectionView.reloadData()
    }
    
    func scrollToTop() {
        // checks if collection view has cells then scroll to top
        if collectionView?.numberOfItems(inSection: 0) ?? 0 > 0 {
            collectionView?.scrollToItem(at: IndexPath(row: 0, section: 0), at: .top, animated: true)
        }
    }
}

extension WatchlistMoviesView: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        presenter.numberOfMovies
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "MovieCell", for: indexPath) as? MovieCell else { return UICollectionViewCell() }
        cell.movieImageView.image = presenter.getMovieImage(index: indexPath.row)
        cell.layer.cornerRadius = 10
        return cell
    }
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        1
    }
    
    func collectionView(_ collectionView: UICollectionView, contextMenuConfigurationForItemAt indexPath: IndexPath, point: CGPoint) -> UIContextMenuConfiguration? {
        configureContextMenu(indexPath.row)
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
