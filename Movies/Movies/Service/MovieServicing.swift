//
//  MovieServicing.swift
//  Movies
//
//  Created by Richard Segerblom on 2022-12-21.
//

import Combine

protocol MovieServicing {
    func fetchMostPopular() -> AnyPublisher<[Movie], Error>
    func fetchTopRated() -> AnyPublisher<[Movie], Error>
}
