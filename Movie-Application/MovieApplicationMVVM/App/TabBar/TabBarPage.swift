//
//  TabBarPage.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/3/22.
//

import UIKit

enum TabBarPage {
    case popular
    case topRated
    case watchlist

    init?(index: Int) {
        switch index {
        case 0:
            self = .popular
        case 1:
            self = .topRated
        case 2:
            self = .watchlist
        default:
            return nil
        }
    }

    func pageTitleValue() -> String {
        switch self {
        case .popular:
            return "Popular"
        case .topRated:
            return "Top Rated"
        case .watchlist:
            return "Watchlist"
        }
    }

    func pageOrderNumber() -> Int {
        switch self {
        case .popular:
            return 0
        case .topRated:
            return 1
        case .watchlist:
            return 2
        }
    }

    func pageIcon() -> UIImage? {
        switch self {
        case .popular:
            return UIImage(systemName: "flame")
        case .topRated:
            return UIImage(systemName: "list.number")
        case .watchlist:
            return UIImage(systemName: "bookmark")
        }
    }

    func pageSelectedIcon() -> UIImage? {
        switch self {
        case .popular:
            return UIImage(systemName: "flame.fill")
        case .topRated:
            return UIImage(systemName: "list.number")
        case .watchlist:
            return UIImage(systemName: "bookmark.fill")
        }
    }

}
