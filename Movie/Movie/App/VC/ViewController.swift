//
//  ViewController.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-24.
//

import UIKit
import RxSwift
import RxDataSources

final class ViewController: UIViewController {

    private let disposeBag = DisposeBag()
    var viewModel: HomeContainerViewModelType!
    @IBOutlet weak var collectionView: UICollectionView!

    override func viewDidLoad() {
        super.viewDidLoad()
        // in a larger scale app, this should be passed from outside.
        viewModel = HomeContainerViewModel(movieApi: MovieAPI())

        viewModel.input.fetchMovies(filter: .popular) // def

        setupComponents()
        bindViewModel()
    }

    let refreshControl = UIRefreshControl()

    private func setupComponents() {
        collectionView.delegate = nil
        collectionView.dataSource = nil

        collectionView
            .register(UINib(nibName: MovieCollectionViewCell.className, bundle: nil),
                      forCellWithReuseIdentifier: HomeDataSource.cellReuseIdentifier)
        collectionView.refreshControl = refreshControl

        let width = collectionView.frame.width - 32
        let layout = VerticalFlowLayout(preferredWidth: width,
                                        preferredHeight: 200)

        collectionView.collectionViewLayout = layout

        let image = UIImage(systemName: "film")!
        let barButtonItem = UIBarButtonItem(
            image: image,
            style: .done,
            target: self,
            action: #selector(filterButtonTapped)
        )
        barButtonItem.tintColor = .black

        navigationItem.rightBarButtonItems = [barButtonItem]
    }

    @objc func filterButtonTapped() {
        showAlertAction()
    }

    private func bindViewModel() {
        let output = viewModel.output
        let input = viewModel.input

        // MARK: - Input
        refreshControl.rx
            .controlEvent(.allEvents)
            .asObservable()
            .debounce(.seconds(1), scheduler: MainScheduler.instance)
            .withLatestFrom(output.pickedFilter)
            .subscribe(onNext: { filter in
                input.fetchMovies(filter: filter)
            })
            .disposed(by: disposeBag)

        collectionView.rx
            .reachedBottom(offset: 1)
            //            .skip(1)
            .debounce(.milliseconds(500), scheduler: MainScheduler.instance)
            .subscribe(onNext: { _ in input.loadMore() })
            .disposed(by: disposeBag)

        // MARK: - Output

        let movies = output.movies
            .observe(on: MainScheduler.instance)
            .share()

        movies
            .map { $0.isEmpty }
            .subscribe() // show an empty view, etc.
            .disposed(by: disposeBag)

        movies
            .map { [HomeDataSource.Model(model: "", items: $0)]}
            .bind(to: collectionView.rx.items(dataSource: HomeDataSource.dataSource()))
            .disposed(by: disposeBag)

        output.error
            .observe(on: MainScheduler.instance)
            .subscribe(onNext: { [weak self] error in
                #if DEBUG
                print("Error: \(error)")
                #endif
                self?.errorAlert(error)
            }).disposed(by: disposeBag)

        let isLoading =
            output.isLoading
            //            .debug("🤩")
            .observe(on: MainScheduler.instance)

        isLoading
            .bind(to: refreshControl.rx.isRefreshing)
            .disposed(by: disposeBag)

        output.pickedFilter
            .map { $0.rawValue }
            .observe(on: MainScheduler.instance)
            .subscribe(onNext: { [weak self] title in
                self?.title = title
            })
            .disposed(by: disposeBag)

        // MARK: - Navigation

        // normally i'd pass this up to the coordinators. However, since only have two views
        // it is ok to do it like this.

        collectionView.rx
            .modelSelected(Movie.self)
            .subscribe(onNext: { [weak self] movie in
                let vm = DetailViewModel(movie: movie)
                let storyboard = UIStoryboard(name: "Main", bundle: nil)
                let vc = storyboard.instantiateViewController(identifier: DetailViewController.className) as! DetailViewController
                vc.viewModel = vm
                self?.present(vc, animated: true, completion: nil)
            })
            .disposed(by: disposeBag)
    }

    private func showAlertAction() {
        let alertController = UIAlertController(title: "",
                                                message: "Pick a filter!",
                                                preferredStyle: .actionSheet)

        let popularAction = UIAlertAction(
            title: "Popular", style: .default, handler: { [weak self] _ in self?.viewModel.input.fetchMovies(filter: .popular)
            }
        )

        let trendingAction = UIAlertAction(
            title: "Trending(Daily)", style: .default, handler: { [weak self] _ in self?.viewModel.input.fetchMovies(filter: .trending)
            }
        )

        let topRatedAction = UIAlertAction(
            title: "TopRated", style: .default, handler: { [weak self] _ in self?.viewModel.input.fetchMovies(filter: .topRated)
            }
        )

        let cancelAction = UIAlertAction(
            title: "Cancel",
            style: .cancel,
            handler: nil
        )
        alertController.addAction(popularAction)
        alertController.addAction(trendingAction)
        alertController.addAction(topRatedAction)
        alertController.addAction(cancelAction)
        present(alertController, animated: true, completion: nil)
    }

    private func errorAlert(_ error: Error) {
        let alert = UIAlertController(
            title: "Error", message: error.localizedDescription, preferredStyle: .alert
        )
        let okAction = UIAlertAction(title: "Ok", style: .default, handler: nil)
        alert.addAction(okAction)
        present(alert, animated: true, completion: nil)
    }

}
