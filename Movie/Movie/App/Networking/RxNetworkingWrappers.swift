//
//  RxNetworkingWrappers.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-25.
//

import Foundation
import RxSwift

extension ReviewAPI: ReactiveCompatible { }

extension Reactive where Base == ReviewAPI {
    func fetchReviews(
        for movie: Id,
        page: Int,
        locale: Locale = Current.locale
    ) -> Observable<Result<ReviewPayload, Error>> {
        return .create { observer in
            self.base.fetchReviews(for: movie, page: page, locale: locale, completion: { result in
                switch result {
                case .success(let response):
                    observer.onNext(.success(response))
                    observer.onCompleted()
                case .failure(let error):
                    observer.onNext(.failure(error))
                    observer.onCompleted()
                }
            })
            return Disposables.create { self.base.router.cancel() }
        }
    }
}

extension MovieAPI: ReactiveCompatible { }

extension Reactive where Base == MovieAPI {
    func fetchGenres(locale: Locale) -> Observable<Result<GenrePayload, Error>> {
        return .create { observer in
            self.base.fetchGenres(locale: locale, completion: { result in

                switch result {
                case .success(let response):
                    observer.onNext(.success(response))
                    observer.onCompleted()
                case .failure(let error):
                    observer.onNext(.failure(error))
                    observer.onCompleted()
                }

            })
            return Disposables.create { self.base.router.cancel() }
        }
    }
}

extension Reactive where Base == MovieAPI {
    func fetchTrending(
        period: Trending,
        page: Int,
        locale: Locale
    ) -> Observable<Result<MoviePayload, Error>> {

        return .create { observer in
            self.base.fetchTrending(period: period, page: page, locale: locale, completion: { result in

                switch result {
                case .success(let response):
                    observer.onNext(.success(response))
                    observer.onCompleted()
                case .failure(let error):
                    observer.onNext(.failure(error))
                    observer.onCompleted()
                }

            })
            return Disposables.create { self.base.router.cancel() }
        }

    }
}

extension Reactive where Base == MovieAPI {
    func fetchPopular(
        page: Int,
        locale: Locale
    ) -> Observable<Result<MoviePayload, Error>> {

        return .create { observer in
            self.base.fetchPopular(page: page, locale: locale, completion: { result in
                switch result {
                case .success(let response):
                    observer.onNext(.success(response))
                    observer.onCompleted()
                case .failure(let error):
                    observer.onNext(.failure(error))
                    observer.onCompleted()
                }

            })
            return Disposables.create { self.base.router.cancel() }
        }

    }
}

extension Reactive where Base == MovieAPI {
    func fetchTopRated(
        page: Int,
        locale: Locale
    ) -> Observable<Result<MoviePayload, Error>> {

        return .create { observer in
            self.base.fetchTopRated(page: page, locale: locale, completion: { result in
                switch result {
                case .success(let response):
                    observer.onNext(.success(response))
                    observer.onCompleted()
                case .failure(let error):
                    observer.onNext(.failure(error))
                    observer.onCompleted()
                }

            })
            return Disposables.create { self.base.router.cancel() }
        }

    }
}
