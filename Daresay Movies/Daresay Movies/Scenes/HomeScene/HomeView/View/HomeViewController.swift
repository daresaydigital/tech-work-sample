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
    
    // MARK: - IBOutlets
    @IBOutlet var collectionView: UICollectionView!
    
    // MARK: - Properties
    private var moviesDataSource: DaMoviesCollectionViewDataSource<HomeMovieCell>!
    
    // MARK: - LifeCycle
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        setupView()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        navigationController?.setNavigationBarHidden(false, animated: true)
    }
    
    // MARK: - View
    private func setupView() {
        self.title = LocalizedStrings.test.value
        
        let testItems = [TestMovieModel(title: "test 1"),
                         TestMovieModel(title: "test 2"),
                         TestMovieModel(title: "test 3"),
                         TestMovieModel(title: "test 4"),
                         TestMovieModel(title: "test 5"),
                         TestMovieModel(title: "test 6"),
                         TestMovieModel(title: "test 7")]
        moviesDataSource = DaMoviesCollectionViewDataSource(items: testItems, collectionView: collectionView, delegate: self)
        collectionView.dataSource = moviesDataSource
        collectionView.delegate = moviesDataSource
    }
    
}

extension HomeViewController: DaMoviesCollectionViewDelegate { }
