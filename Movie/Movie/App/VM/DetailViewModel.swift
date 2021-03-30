//
//  DetailViewModel.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-26.
//

import Foundation
import RxSwift

protocol DetailViewModelInput {
    //
}

protocol DetailViewModelOutput {
    var backgroundImageURL: URL { get }
    var posterImageURL: URL { get }

    var title: String { get }
    var genre: Observable<String> { get }
    var release: String { get }
    var description: String { get }
}

protocol DetailViewModelType {
    var input: DetailViewModelInput { get }
    var output: DetailViewModelOutput { get }
}

final class DetailViewModel: DetailViewModelInput,
                             DetailViewModelOutput,
                             DetailViewModelType {

    var backgroundImageURL: URL
    var posterImageURL: URL
    var title: String
    var genre: Observable<String>
    var release: String
    var description: String

    var input: DetailViewModelInput { self }
    var output: DetailViewModelOutput { self }

    init(movie: Movie, api: MovieAPI = MovieAPI()) {
        title = movie.title
        release = movie.releaseDate
        description = movie.overview

        backgroundImageURL = movie.backDropUrl
        posterImageURL = movie.posterUrl

        let genre = api.rx // this should be probably cached.
            .fetchGenres(locale: Current.locale)
            .map { result -> String in
                switch result {
                case .success(let payload):
                    return payload.genres.filter { genre in
                        movie.genreIDS.contains(genre.id)
                    }
                    .map { $0.name }
                    .joined(separator: ",")
                case .failure:
                    return ""
                }
            }
        self.genre = genre

    }
    #if DEBUG
    deinit {
        print("\(self) de-init")
    }
    #endif
}
