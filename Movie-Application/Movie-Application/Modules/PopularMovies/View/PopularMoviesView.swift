//
//  PopularMoviesView.swift
//  PopularMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import UIKit

final class PopularMoviesView: UIViewController, ViewInterface {

    var presenter: PopularMoviesPresenterViewInterface!

    // MARK: - Properties
    @IBOutlet var collectionView: UICollectionView!

    private let movieImagesCache = NSCache<NSNumber, UIImage>()
    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        self.presenter.viewDidLoad()
    }

    // MARK: - Theme

    func applyTheme() {
        view.backgroundColor = .systemBackground
    }

    // MARK: - Private functions
    private func setupView() {
        configureNavigation()
        setupCollectionView()
        self.applyTheme()
    }

    // function to setup and configure navigation details
    private func configureNavigation() {
        navigationController?.navigationBar.prefersLargeTitles = true
        self.title = "Popular"
    }

    // function to setup and configure collectionView details
    private func setupCollectionView() {
        collectionView.delegate = self
        collectionView.dataSource = self
        collectionView.register(MovieCell.self, forCellWithReuseIdentifier: "MovieCell")
    }

    func configureContextMenu(index: Int, imageData: Data) -> UIContextMenuConfiguration {
        presenter.configureContextMenu(index: index, imageData: imageData)
    }

    func configurePagination(_ cellRow: Int) {
        if cellRow == presenter.numberOfMovies - 1 {
            presenter.getPopularMovies()
        }
    }

}

extension PopularMoviesView: PopularMoviesViewInterface {

    func showError(with error: RequestError) {
        let errorAlert = UIAlertController(title: "Error Occured",
                                           message: error.errorDescription,
                                           preferredStyle: .alert)
        let alertAction = UIAlertAction(title: "Retry", style: .default) { [weak self] (_) in
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

extension PopularMoviesView: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        presenter.numberOfMovies
    }

    func collectionView(_ collectionView: UICollectionView,
                        willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        configurePagination(indexPath.row)

        // for caching cell movie image
        guard let cell = cell as? MovieCell else { return }
        let cellNumber = NSNumber(value: indexPath.item)

        if let cachedImage = self.movieImagesCache.object(forKey: cellNumber) {
            cell.movieImageView.image = cachedImage
        } else {
            self.presenter.getMovieImage(index: indexPath.row, completion: { [weak self] (image) in
                if collectionView.indexPath(for: cell) == indexPath {
                    cell.movieImageView.image = image
                }
                self?.movieImagesCache.setObject(image, forKey: cellNumber)
            })
        }

    }

    func collectionView(_ collectionView: UICollectionView,
                        cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "MovieCell",
                                                            for: indexPath) as? MovieCell
        else { return UICollectionViewCell() }
        cell.layer.cornerRadius = 10
        return cell
    }

    func numberOfSections(in collectionView: UICollectionView) -> Int {
        1
    }

    func collectionView(_ collectionView: UICollectionView,
                        contextMenuConfigurationForItemAt indexPath: IndexPath,
                        point: CGPoint) -> UIContextMenuConfiguration? {

        let cellNumber = NSNumber(value: indexPath.item)

        if let cachedImage = self.movieImagesCache.object(forKey: cellNumber) {
            return configureContextMenu(index: indexPath.row,
                                        imageData: cachedImage.jpegData(compressionQuality: 1.0) ?? Data())
        }

        return configureContextMenu(index: indexPath.row, imageData: Data())
    }

    func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        sizeForItemAt indexPath: IndexPath) -> CGSize {
        let noOfCellsInRow = 2

        guard let flowLayout = collectionViewLayout as? UICollectionViewFlowLayout
        else {
            return CGSize(width: 0, height: 0)

        }
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
        presenter.movieSelected(at: indexPath.row)
    }
}
