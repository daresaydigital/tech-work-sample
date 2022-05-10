//
//  WatchlistMoviesView.swift
//  WatchlistMovies
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import UIKit

final class WatchlistMoviesView: UIViewController, ViewInterface {

    var presenter: WatchlistMoviesPresenterViewInterface!

    // MARK: - Properties
    @IBOutlet var collectionView: UICollectionView!

    @IBOutlet var emptyWatchlistContainerView: UIStackView!

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
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

    private func setupView() {
        configureNavigation()
        setupCollectionView()

        self.applyTheme()
    }

    // function to setup and configure navigation details
    private func configureNavigation() {
        navigationController?.navigationBar.prefersLargeTitles = true
        self.title = "Watchlist"

        let dateAddedAction = UIAction(title: "Date Added",
                                       image: nil, identifier: nil,
                                       discoverabilityTitle: nil,
                                       state: .off) { (_) in
            self.presenter.sortByDate()
        }

        let nameSortAction = UIAction(title: "Name",
                                      image: nil, identifier: nil,
                                      discoverabilityTitle: nil,
                                      state: .off) { (_) in
            self.presenter.sortByName()
        }

        let userScoreSortAction = UIAction(title: "User Score",
                                           image: nil, identifier: nil,
                                           discoverabilityTitle: nil,
                                           state: .off) { (_) in
            self.presenter.sortByUserScore()
        }

    let sortMenu = UIMenu(title: "",
                          image: nil, identifier: nil,
                          options: .singleSelection,
                          children: [dateAddedAction, nameSortAction, userScoreSortAction])

    navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Sort", image: nil, primaryAction: nil, menu: sortMenu)
    }

    // function to setup and configure collectionView details
    private func setupCollectionView() {
        collectionView.delegate = self
        collectionView.dataSource = self
        collectionView.register(MovieCell.self, forCellWithReuseIdentifier: "MovieCell")
    }

    func configureContextMenu(_ index: Int) -> UIContextMenuConfiguration {
        presenter.configureContextMenu(index)
    }

    // MARK: - Actions

    @IBAction func browseMoviesDidTap(_ sender: UIButton) {
        presenter.browseMoviesDidTap()
    }

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

    func showError(with error: RequestError, index: Int) {
        let errorAlert = UIAlertController(title: "Error Occured",
                                           message: error.errorDescription,
                                           preferredStyle: .alert)
        let alertAction = UIAlertAction(title: "Retry", style: .default) { [weak self] (_) in
            self?.presenter.alertRetryButtonDidTap(index)
        }
        errorAlert.addAction(alertAction)
        self.present(errorAlert, animated: true, completion: nil)
    }

    func setWatchlistEmptyContainerisHidden(to isHidden: Bool) {
        emptyWatchlistContainerView.isHidden = isHidden
    }
}

extension WatchlistMoviesView: UICollectionViewDelegate,
                                UICollectionViewDataSource,
                                UICollectionViewDelegateFlowLayout {

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        presenter.numberOfMovies
    }

    func collectionView(_ collectionView: UICollectionView,
                        cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "MovieCell",
                                                            for: indexPath) as? MovieCell
        else { return UICollectionViewCell() }

        cell.movieImageView.image = presenter.getMovieImage(index: indexPath.row)
        cell.layer.cornerRadius = 10
        return cell
    }

    func numberOfSections(in collectionView: UICollectionView) -> Int {
        1
    }

    func collectionView(_ collectionView: UICollectionView,
                        contextMenuConfigurationForItemAt indexPath: IndexPath,
                        point: CGPoint) -> UIContextMenuConfiguration? {
        configureContextMenu(indexPath.row)
    }

    func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        sizeForItemAt indexPath: IndexPath) -> CGSize {
        let noOfCellsInRow = 2

        guard let flowLayout = collectionViewLayout as? UICollectionViewFlowLayout else {
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
