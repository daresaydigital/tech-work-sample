//
//  EmptyState.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-17.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import Foundation

enum EmptyState {

    case noInternet
    case networkError
    case noReviews
    case noFavorites

    var imageName: String {
        switch self {
        case .noInternet:
            return "exclamationmark.icloud"
        case .networkError:
            return "exclamationmark.circle"
        case .noReviews:
            return "exclamationmark.bubble"
        case .noFavorites:
            return "heart"
        }
    }

    var title: String {
        switch self {
        case .noInternet:
            return "Something went wrong!"
        case .networkError:
            return "There is something wrong with the network!"
        case .noReviews:
            return "No reviews for this movie yet!"
        case .noFavorites:
            return "You haven't selected any favorites yet!"
        }
    }
}
