//
//  Movies.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import Foundation

extension ServerModels {
    enum Movies {
        struct Request {}
        
        typealias Response = MovieSchema
    }
}

struct MovieSchema: ServerModel {
    let page: UInt?
    let results: [MoviesModel]?
    let totalPages, totalResults: UInt?
}

// MARK: - MovieModel
// Using a reference type here
// since we are passing it around in the app
// and modifying it's values.
final class MoviesModel: ServerModel {
    let adult: Bool?
    let backdropPath: String?
    let genreIDS: [Int]?
    let movieID: Int?
    let originalLanguage, originalTitle, overview: String?
    let popularity: Double?
    let posterPath: String?
    let releaseDate: String?
    let title: String?
    let video: Bool?
    let voteAverage: Double?
    let voteCount: Int?
    var isFavorite: Bool = false
    
    var posterURL: URL? {
        imageURL(posterPath, typeAndSize: .poster(.w342))
    }
    
    var backgroundImageURL: URL? {
        imageURL(posterPath, typeAndSize: .backDrop(.w780))
    }
    
    enum CodingKeys: String, CodingKey {
        case adult
        case backdropPath
        case genreIDS = "genreIds"
        case movieID = "id"
        case originalLanguage
        case originalTitle
        case overview, popularity
        case posterPath
        case releaseDate
        case title, video
        case voteAverage
        case voteCount
    }
    
    private func imageURL(_ url: String?, typeAndSize: ImageTypes) -> URL? {
        guard let url = url else { return nil }
        let urlBuilder = ImageBaseUrlBuilder(forTypeAndSize: typeAndSize)
        let fullUrl = urlBuilder.createURL(filePath: url)
        return fullUrl
    }
}

// MARK: - Equatable
extension MoviesModel: Equatable {
    static func == (lhs: MoviesModel, rhs: MoviesModel) -> Bool {
        return lhs.movieID == rhs.movieID
    }
}
