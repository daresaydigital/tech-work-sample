//
//  HomeViewController.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/13/21.
//

import UIKit

class HomeViewController: UIViewController, Storyboarded {

    // MARK: - Coordinator
    weak var coordinator: HomeCoordinator?
    
    // MARK: - ViewModel
    private let homeVM: HomeViewModel = HomeViewModel(homeService: HomeService.shared)
    
    // MARK: - IBOutlets
    @IBOutlet var collectionView: UICollectionView!
    
    // MARK: - Properties
    private var moviesDataSource: DaMoviesCollectionViewDataSource<HomeMovieCell>!
    
    // MARK: - LifeCycle
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        setupView()
        setupBindings()
        callService()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        navigationController?.setNavigationBarHidden(false, animated: true)
    }
    
    // MARK: - View
    private func setupView() {
        
        self.title = LocalizedStrings.test.value
        
        moviesDataSource = DaMoviesCollectionViewDataSource(items: [], collectionView: collectionView, delegate: self)
        collectionView.dataSource = moviesDataSource
        collectionView.delegate = moviesDataSource
        collectionView.contentInset.top = 10
        
        self.navigationController?.navigationBar.tintColor = .systemGreen
    }
    
    // AlertVeiw
    private func showMessage(title: String = "Error", message: String, buttonTitle: String = "Ok") {
        let ac = UIAlertController(title: title,
                                    message: message,
                                    preferredStyle: .alert)
        ac.addAction(UIAlertAction(title: buttonTitle, style: .default, handler: nil))
        self.present(ac, animated: true, completion: nil)
    }
    
    // MARK: - Bindings
    private func setupBindings() {
        
        // subscribe to loading
        homeVM.loading = { [weak self] isLoading in
            guard let self = self else { return }
            self.view.isUserInteractionEnabled = !isLoading
            isLoading ? self.view.animateActivityIndicator() : self.view.removeActivityIndicator()
        }
        // subscribe to movies Response
        homeVM.list = { [weak self] movies in
            guard let self = self else { return }
            // Show Images to collectionView
            DispatchQueue.main.async {
                self.moviesDataSource.refreshWithNewItems(movies)
            }
            self.homeVM.isPaging = false
        }
        
        // subscribe to Errors
        homeVM.errorHandler = { [weak self] error in
            guard let self = self else { return }
            self.showMessage(message: error)
        }
    }
    
    // MARK: - service
    private func callService() {
        homeVM.getMovies()
    }
    
}

// MARK: - DaMoviesCollectionViewDelegate
extension HomeViewController: DaMoviesCollectionViewDelegate {
    
    func collection(willDisplay cellIndexPath: IndexPath, cell: UICollectionViewCell) {
        guard let cell = cell as? HomeMovieCell else { return }
        cell.delegate = self
        cell.index = cellIndexPath.item
    }
    
    func scrollDidEndDragging(_ scrollView: UIScrollView, willDecelerate: Bool) {
        if homeVM.isValidForPaging(scrollView: scrollView) {
            homeVM.getNextPageMovies()
        }
    }
    
}

extension HomeViewController: MovieCellDelagate {
    func isFaved(_ isFaved: Bool, model: MovieModel, cellIndex: Int) {
        print("isFAVED: ====", isFaved, model, cellIndex)
        homeVM.isFaved(isFaved, model: model)
        moviesDataSource.items[cellIndex].isFaved = isFaved
        moviesDataSource.collectionView.reloadData()
    }
}
