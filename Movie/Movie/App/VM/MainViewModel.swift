//
//  MainViewModel.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-25.
//

import Foundation
import RxSwift
import RxRelay
import RxCocoa
import RxDataSources

struct HomeDataSource {

    static let cellReuseIdentifier = "movieCell"

    typealias Model = SectionModel<String, Movie>
    typealias DataSource = RxCollectionViewSectionedReloadDataSource<Model>

    static func dataSource() -> DataSource {
        return .init(configureCell: { _, collectionView, indexPath, model in

            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: Self.cellReuseIdentifier,
                                                          for: indexPath)

            print("Model: \(model) for cell: \(cell)")
            cell.backgroundColor = .green
            return cell
        })
    }
}

public enum MovieCategory: String {
    case trending = "Trending"
    case topRated = "Top Rated"
    case popular = "Popular"
}

protocol HomeContainerViewModelInput {
    func fetchMovies(filter: MovieCategory)
    func loadMore()
}

protocol HomeContainerViewModelOutput {
    var movies: Observable<[Movie]> { get }
    var isLoading: Observable<Bool> { get }
    var pickedFilter: Observable<MovieCategory> { get }
    var error: Observable<Error> { get }
}

protocol HomeContainerViewModelType: class {
    var input: HomeContainerViewModelInput { get }
    var output: HomeContainerViewModelOutput { get }
}

final class HomeContainerViewModel: HomeContainerViewModelInput,
                                    HomeContainerViewModelOutput,
                                    HomeContainerViewModelType,
                                    Redux {

    typealias HomeStore = Store<State, Action>

    struct State: Equatable {

        var filter: MovieCategory = .trending
        var movies: [Movie] = []
        var page: Int = 1
        var error: Error?

        static func == (lhs: HomeContainerViewModel.State, rhs: HomeContainerViewModel.State) -> Bool {
            lhs.movies == rhs.movies && lhs.filter == rhs.filter && lhs.page == rhs.page
        }
    }

    enum Action {
        case filterSelected(MovieCategory)
        case movies([Movie])
        case reachedBottom
        case error(Error?)
    }

    static func reduce(state: State, action: Action) -> State {
        var state = state

        switch action {
        case .filterSelected(let category):
            state.filter = category
            state.movies = []
        case .movies(let movies):
            state.movies.append(contentsOf: movies)
            state.error = nil
        case .reachedBottom:
            state.page += 1
        case .error(let error):
            state.error = error
        }
        return state
    }

    // MARK: - Input
    func fetchMovies(filter: MovieCategory) {
        filterRelay.accept(filter)
    }

    func loadMore() {
        loadMoreRelay.accept(Void())
    }

    // MARK: - Output
    var movies: Observable<[Movie]>
    var isLoading: Observable<Bool>
    var pickedFilter: Observable<MovieCategory>
    var error: Observable<Error>

    var input: HomeContainerViewModelInput { self }
    var output: HomeContainerViewModelOutput { self }

    // this pattern works great also when the state has to be restored from some kind of storage. Here for example, we could have injected the cached movies retrieved from previous session, etc.
    private let store = HomeStore(initialState: State(),
                                  reducer: HomeContainerViewModel.reduce)
    private let disposeBag = DisposeBag()

    init(movieApi: MovieAPI) {

        let state = store.state

        // input
        filterRelay
            .asObservable()
            .map(Action.filterSelected)
            .observe(on: MainScheduler.instance)
            .bind(to: store)
            .disposed(by: disposeBag)

        loadMoreRelay
            .asObservable()
            .map { _ in Action.reachedBottom }
            .observe(on: MainScheduler.instance)
            .bind(to: store)
            .disposed(by: disposeBag)

        // output
        let activityIndicator = RxActivityIndicator()

        let rxApi = movieApi.rx

        let moviesResult = Observable.merge(
            filterRelay.asObservable().map { _ in },
            loadMoreRelay.asObservable()
        )
        .withLatestFrom(state)
        //        .debug("😇")
        .flatMapLatest { state -> Observable<Result<MoviePayload, Error>> in
            switch state.filter {
            case .popular:
                return rxApi
                    .fetchPopular(page: state.page, locale: Current.locale)
                    .trackActivity(activityIndicator)
            case .trending:
                return rxApi
                    .fetchTrending(period: .daily, page: state.page, locale: Current.locale)
                    .trackActivity(activityIndicator)
            case .topRated:
                return rxApi
                    .fetchTopRated(page: state.page, locale: Current.locale)
                    .trackActivity(activityIndicator)
            }
        }.share()

        moviesResult
            .map(extractFailure)
            .compactMap { error in Action.error(error) }
            .bind(to: store)
            .disposed(by: disposeBag)

        moviesResult
            .map(extractSuccess)
            .compactMap { $0?.results }
            .map(Action.movies)
            .bind(to: store)
            .disposed(by: disposeBag)

        self.isLoading = activityIndicator.asObservable()
        self.movies = state.map { $0.movies }
        self.pickedFilter = state.map { $0.filter }
        self.error = state.compactMap { $0.error }
    }

    #if DEBUG
    deinit {
        print("\(self) de-init")
    }
    #endif

    private let filterRelay = PublishRelay<MovieCategory>()
    private let loadMoreRelay = PublishRelay<Void>()

}
