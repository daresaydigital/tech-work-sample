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
        self.view.backgroundColor = .red

        // in a larger scale app, this should be passed from outside.
        viewModel = HomeContainerViewModel(movieApi: MovieAPI())

        // test:
        viewModel.input.fetchMovies(filter: .popular)
//
//        DispatchQueue.main.asyncAfter(deadline: .now() + 5) {
//            self.viewModel.input.fetchMovies(filter: .trending)
//        }
//
//        DispatchQueue.main.asyncAfter(deadline: .now() + 7) {
//            self.viewModel.input.loadMore()
//        }

        setupComponents()
        bindViewModel()
    }

    let refreshControl = UIRefreshControl()

    private func setupComponents() {
        collectionView.delegate = nil
        collectionView.dataSource = nil

        collectionView.register(UICollectionViewCell.self, forCellWithReuseIdentifier: HomeDataSource.cellReuseIdentifier)

        collectionView.refreshControl = refreshControl

        let barButtonItem = UIBarButtonItem(
            barButtonSystemItem: .action, target: self, action: #selector(filterButtonTapped)
        )

        navigationItem.rightBarButtonItems = [barButtonItem]
    }

    @objc func filterButtonTapped() {
        viewModel.input.fetchMovies(filter: .popular)
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

//        collectionView.rx
//            .reachedBottom()
//            .skip(1)
//            .debounce(.milliseconds(500), scheduler: MainScheduler.instance)
//            .subscribe(onNext: { _ in input.loadMore() })
//            .disposed(by: disposeBag)
//

        // MARK: - Output

//        output
//            .movies
//            .debug("🥰")
//            .subscribe()
//            .disposed(by: disposeBag)

        output
            .movies
            .map { [HomeDataSource.Model(model: "", items: $0)]}
            .bind(to: collectionView.rx.items(dataSource: HomeDataSource.dataSource()))
            .disposed(by: disposeBag)

        output.error
            .observe(on: MainScheduler.instance)
            .subscribe(onNext: { error in
                #if DEBUG
                print("Error: \(error)")
                #endif
            }).disposed(by: disposeBag)

        output.isLoading
            //            .debug("🤩")
            .observe(on: MainScheduler.instance)
            .bind(to: refreshControl.rx.isRefreshing)
            .disposed(by: disposeBag)

        // MARK: - Navigation

        // normally i'd pass this up to the coordinators. However, since only have two views
        // it is ok to do it like this.

        //        collectionView.rx
        //            .modelSelected()
    }

}
