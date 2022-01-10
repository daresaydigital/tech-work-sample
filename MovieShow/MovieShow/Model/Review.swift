//
//  Review.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-28.
//

import Foundation

struct ReviewResponse: Codable, Hashable {
    let results: [Review]
    
}

struct Review: Codable, Hashable {
    let id: String
    let author: String?
    let content: String?
    let createdAt: String?
    let rating: Int?
    let authorDetails: AuthorDetails
    
    static func ==(lhs: Review, rhs: Review) -> Bool {
        lhs.id == rhs.id
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
    }
    
    static private let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy"
        formatter.locale = Locale(identifier: "en_US_POSIX")
        #warning("Date formatter is not working, come back and fix it.")
        return formatter
    }()

    
    var reviewAgeText: String {
        guard let ageString = createdAt  else { return ""}
        print("DEBUG: age string \(ageString)")
        let age = Review.dateFormatter.date(from: ageString)
        print("DEBUG: describing age \(String(describing: age))")
        let reviewAge = age?.timeIntervalSinceNow
        print("DEBUG: reviewAge \(String(describing: reviewAge))")
        return "\(String(describing: reviewAge))"
    }
    
    var avatarURL: URL? {
        URL(string: (authorDetails.avatarPath ?? ""))
    }
}

struct AuthorDetails: Codable, Hashable {
    let avatarPath: String?
}
