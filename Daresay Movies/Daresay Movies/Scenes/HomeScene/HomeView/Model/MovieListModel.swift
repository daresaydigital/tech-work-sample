//
//  MovieListModel.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import Foundation

// MARK: - MovieArrayModel
typealias MovieArrayModel = [MovieModel]
// MARK: - MovieModel
struct MovieModel: Codable {
    let adult: Bool?
    let backdropPath: String?
    let genreIDS: [Int]?
    let id: Int?
    let originalLanguage, originalTitle, overview: String?
    let popularity: Double?
    let posterPath, releaseDate, title: String?
    let video: Bool?
    let voteAverage: Double?
    let voteCount: Int?
    var isFaved: Bool = false

    enum CodingKeys: String, CodingKey {
        case adult
        case backdropPath = "backdrop_path"
        case genreIDS = "genre_ids"
        case id
        case originalLanguage = "original_language"
        case originalTitle = "original_title"
        case overview, popularity
        case posterPath = "poster_path"
        case releaseDate = "release_date"
        case title, video
        case voteAverage = "vote_average"
        case voteCount = "vote_count"
    }
}

// MARK: - Equatable (==)
extension MovieModel: Equatable {
    
    static func == (lhs: Self, rhs: Self) -> Bool {
        return lhs.id == rhs.id
    }
    
}

// MARK: - Favorite extensions
extension Sequence where Self == MovieArrayModel {

    func fetchFavorites(from FavList: MovieArrayModel) -> MovieArrayModel {
        var movieList = self
        let favourites = FavList
        for (i, movies) in movieList.enumerated() {
            for fav in favourites where movies == fav {
                    movieList[i].isFaved = true
            }
        }
        return movieList
    }
}
