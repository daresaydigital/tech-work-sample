//
//  TopRatedMoviesView.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

final class TopRatedMoviesView: UIViewController, ViewInterface {
	
	var presenter: TopRatedMoviesPresenterViewInterface!
	
	// MARK: - Properties
    @IBOutlet weak var collectionView: UICollectionView!
    
    private let movieImagesCache = NSCache<NSNumber, UIImage>()
	// MARK: - Initialize

	
	// MARK: - Lifecycle
	
	override func viewDidLoad() {
		super.viewDidLoad()
        configureNavigation()
        setupCollectionView()
		
		self.applyTheme()
		self.presenter.viewDidLoad()
	}
	
	
	// MARK: - Theme
	
	func applyTheme() {
        view.backgroundColor = .systemBackground
    }
    
    // MARK: - Private functions
    
    // function to setup and configure navigation details
    private func configureNavigation() {
        navigationController?.navigationBar.prefersLargeTitles = true
        self.title = "Top Rated"
    }
	
    // function to setup and configure collectionView details
    private func setupCollectionView() {
        collectionView.delegate = self
        collectionView.dataSource = self
        collectionView.register(MovieCell.self, forCellWithReuseIdentifier: "MovieCell")
    }
    
    // function to configure contextMenu for each collectionView cell
    private func configureContextMenu(index: Int, imageData: Data) -> UIContextMenuConfiguration {
        
        
        let context = UIContextMenuConfiguration(identifier: nil, previewProvider: nil) { (action) -> UIMenu? in
            
            let viewDetails = UIAction(title: "View Details", image: UIImage(systemName: "text.below.photo.fill"), identifier: nil, discoverabilityTitle: nil, state: .off) { (_) in
                self.presenter.showMovieDetails(index)
            }
            
            let addToWatchList = UIAction(title: "Add to Watchlist", image: UIImage(systemName: "bookmark"), identifier: nil, discoverabilityTitle: nil, state: .off) { (_) in
                self.presenter.addToWatchList(index: index, imageData: imageData)
            }
            
            return UIMenu(title: self.presenter.getMovieTitle(index: index), image: nil, identifier: nil, options: UIMenu.Options.displayInline, children: [addToWatchList, viewDetails])
            
        }
        return context
        
    }
    
    private func configurePagination(_ cellRow: Int) {
        if cellRow == presenter.numberOfMovies - 1 {
            presenter.getTopRatedMovies()
        }
    }
	
}

extension TopRatedMoviesView: TopRatedMoviesViewInterface {
    
    func showError(with error: RequestError) {
        let errorAlert = UIAlertController(title: "Error Occured", message: error.errorDescription, preferredStyle: .alert)
        let alertAction = UIAlertAction(title: "Retry", style: .default) { [weak self] action in
            self?.presenter.alertRetryButtonDidTap()
        }
        errorAlert.addAction(alertAction)
        self.present(errorAlert, animated: true, completion: nil)
    }
    
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

extension TopRatedMoviesView: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        presenter.numberOfMovies
    }
    
    func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        configurePagination(indexPath.row)
        
        // for caching cell movie image
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
        let cellNumber = NSNumber(value: indexPath.item)
        
        if let cachedImage = self.movieImagesCache.object(forKey: cellNumber) {
            return configureContextMenu(index: indexPath.row, imageData: cachedImage.jpegData(compressionQuality: 1.0) ?? Data())
        }
        
        return configureContextMenu(index: indexPath.row, imageData: Data())
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
