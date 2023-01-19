//
//  MoviesViewController.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 15.01.2023.
//

import UIKit
import SnapKit

class MoviesViewController: UIViewController {
    
    private let moviesViewModel = MoviesViewModel()
    private let moviesCollectionView = UICollectionView(
        frame: .zero,
        collectionViewLayout: UICollectionViewFlowLayout()
    )
    private var movieModelList: [MovieModel] = []

    override func viewDidLoad() {
        super.viewDidLoad()
        setup()
        setupView()
        setupHierarchy()
        setupLayout()
    }
    
    override func viewWillLayoutSubviews() {
        super.viewWillLayoutSubviews()
        moviesCollectionView.collectionViewLayout.invalidateLayout()
    }
    
    private func setup() {
        moviesViewModel.delegate = self
        moviesViewModel.getData()
    }
    
    private func setupView() {
        view.backgroundColor = .bg
        title = Constant.MoviesViewController.title
        navigationController?.navigationBar.prefersLargeTitles = true
        navigationController?.navigationBar.barTintColor = .bg
        navigationController?.navigationBar.largeTitleTextAttributes = [
            NSAttributedString.Key.foregroundColor: UIColor.text]
        navigationController?.navigationBar.titleTextAttributes = [
            NSAttributedString.Key.foregroundColor: UIColor.text]

        moviesCollectionView.backgroundColor = .bg
        moviesCollectionView.delegate = self
        moviesCollectionView.dataSource = self
        moviesCollectionView.register(MoviesCollectionViewCell.self)
        moviesCollectionView.register(
            MoviesCollectionReusableView.self,
            forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader,
            withReuseIdentifier: MoviesCollectionReusableView.identifier
        )
    }
    
    private func setupHierarchy() {
        view.addSubview(moviesCollectionView)
    }
    
    private func setupLayout() {
        moviesCollectionView.snp.makeConstraints { make in
            make.edges.equalTo(view.safeAreaLayoutGuide)
        }
    }
}


// MARK: - MovieCollectionView delegates
extension MoviesViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.moviesViewModel.movieModelListCount
    }
        
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell: MoviesCollectionViewCell = collectionView.dequeueReusableCell(for: indexPath)
        cell.configure(movieModel: self.movieModelList[indexPath.row])
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let movieDetailsViewController = MovieDetailsViewController(movieModel: movieModelList[indexPath.row])
        show(movieDetailsViewController, sender: self)
    }
}


// MARK: - MovieCollectionView layout delegates
extension MoviesViewController: UICollectionViewDelegateFlowLayout {
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: 20, left: 20, bottom: 20, right: 20)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.frame.size.width / 2.5, height: collectionView.frame.size.width / 2)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 20
    }
}


// MARK: - MoviesViewController header
extension MoviesViewController {
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, referenceSizeForHeaderInSection section: Int) -> CGSize {
        return CGSize(
            width: view.frame.size.width,
            height: 50
        )
    }
    
    func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
        guard let header = collectionView.dequeueReusableSupplementaryView(
            ofKind: kind,
            withReuseIdentifier: MoviesCollectionReusableView.identifier,
            for: indexPath
        ) as? MoviesCollectionReusableView else {
            return UICollectionReusableView()
        }
        header.handleSegmentChange = { [weak self] segmentIndex in
            self?.moviesViewModel.setSegmentChange(segmentIndex)
            self?.moviesViewModel.showMovies()
        }
        return header
    }
}


//MARK: - MoviesViewController MoviesViewModelProtocol
extension MoviesViewController: MoviesViewModelProtocol {
    
    func setMoviesData(movieModelList: [MovieModel]) {
        self.movieModelList = movieModelList
    }
    
    func reloadCollectionView() {
        moviesCollectionView.reloadDataAsync()
    }
}


//MARK: - MoviesViewController
extension MoviesViewController: UIScrollViewDelegate {
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        let currentOffset = scrollView.contentOffset.y
        let maximumOffset = scrollView.contentSize.height - scrollView.frame.size.height

        if maximumOffset - currentOffset <= 50.0 {
            moviesViewModel.appendNewMovies()
        }
    }
}
