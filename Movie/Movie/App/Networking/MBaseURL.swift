//
//  MApiConstants.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-24.
//

import Foundation

struct MBaseURL {
    static let url: URL = URL(string: "https://api.themoviedb.org/3/")!
    static let imageURL: URL = URL(string: "https://image.tmdb.org/t/p/w500")!
}

/*
 
 main v
 - trending today (?)
 - popular
 - top rated
 
 search v
 - showing search bar w abillity to search movies
 
 detail v
 - showing a view with horizontally scrollable reviews, details of the movie including a picture, and video trailer (if possible) on top.
 
 */
