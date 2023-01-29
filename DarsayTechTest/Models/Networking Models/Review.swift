//
//  Review.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

struct Review: Codable {
  
    let author: String
    let authorDetail: AuthorDetail
    let url: String
    let id: String
    let content: String

    enum CodingKeys: String, CodingKey {
        case author
        case authorDetail = "author_details"
        case url
        case id
        case content
    }
}

struct AuthorDetail: Codable {
    let name: String
    let username: String
    let avatarPath: String
    
    enum CodingKeys: String, CodingKey {
        case name
        case username
        case avatarPath = "avatar_path"
    }
}
