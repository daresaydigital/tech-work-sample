//
//  ReviewViewModel.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2022-01-08.
//

import Foundation

struct ReviewViewmodel {
    let id: String
    let author: String?
    let content: String?
    let age: String?
    let rating: Int?
    let authorAvatarURL: URL?
    
    init(review: Review) {
        self.id = review.id
        self.author = review.author
        self.content = review.content
        self.age = review.reviewAgeText
        self.rating = review.rating
        self.authorAvatarURL = review.avatarURL
    }
}
