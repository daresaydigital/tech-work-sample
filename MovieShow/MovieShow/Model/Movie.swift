//
//  Movie.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import Foundation

struct MovieResponse: Decodable {
    let results: [Movie]
}

struct Movie: Codable, Hashable {
    
    let id: Int
    let title: String
    let backdropPath: String?
    let posterPath: String?
    let overview: String
    let voteAverage: Double
    let voteCount: Int
    let runtime: Int?
    let releaseDate: String?
    
    var backdropURL: URL {
        URL(string: "https://image.tmdb.org/t/p/w500\(backdropPath ?? "")")!
    }
    
    var postureURL: URL {
        URL(string: "https://image.tmdb.org/t/p/w500\(posterPath ?? "")")!
    }
    
    static private let durationFormatter: DateComponentsFormatter = {
        let formatter = DateComponentsFormatter()
        formatter.unitsStyle = .full
        formatter.allowedUnits = [.hour, .minute]
        return formatter
    }()
    
    static private let YearFromatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy"
        return formatter
    }()
    
    var ratingText: String {
        let rating = Int(voteAverage )
        let ratingText = (0..<rating).reduce(""){ (acc, _) -> String in
            acc + "★"
        }
        return ratingText
    }
    
    var releaseYear: String {
        guard let releaseDate = releaseDate, let date = Utils.dateFormatter.date(from: releaseDate) else {
            return "n/a"
        }
        return Movie.YearFromatter.string(from: date)
    }
    
    var durationText: String {
        guard let runtime = runtime, runtime > 0 else {
            return ""
        }
        return Movie.durationFormatter.string(from: TimeInterval(runtime) * 60) ?? "n/a"
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
    }
    
    static func ==(lhs: Movie, rhs: Movie) -> Bool {
        lhs.id == rhs.id
    }
}
