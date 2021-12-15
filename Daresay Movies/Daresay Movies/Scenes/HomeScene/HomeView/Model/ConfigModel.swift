//
//  ConfigModel.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import Foundation

// MARK: - Base
struct ConfigModel: Codable {
    var images: ImagesConfigModel?
    var changeKeys: [String]?

    enum CodingKeys: String, CodingKey {
        case images
        case changeKeys = "change_keys"
    }
}

// MARK: - Images
struct ImagesConfigModel: Codable {
    var baseURL: String?
    var secureBaseURL: String?
    var backdropSizes: [String]?
    var logoSizes: [String]?
    var posterSizes: [String]?
    var profileSizes: [String]?
    var stillSizes: [String]?
    
    enum CodingKeys: String, CodingKey {
        case baseURL = "base_url"
        case secureBaseURL = "secure_base_url"
        case backdropSizes = "backdrop_sizes"
        case logoSizes = "logo_sizes"
        case posterSizes = "poster_sizes"
        case profileSizes = "profile_sizes"
        case stillSizes = "still_sizes"
    }
}

enum ImageTypes {
    case backDrop(BDSize)
    case Logo(LogoSizes)
    case poster(PosterSizes)
    case profile(LogoSizes)
    
    var sizeString: String {
        switch self {
        case .backDrop(let bDSize):
            return bDSize.rawValue
        case .Logo(let logoSizes):
            return logoSizes.rawValue
        case .poster(let posterSizes):
            return posterSizes.rawValue
        case .profile(let logoSizes):
            return logoSizes.rawValue
        }
    }
}
    
    enum Size {
        case small
        case normal
        case original
    }

enum BDSize: String, Codable {
    case w300
    case w780
    case w1280
    case original
}

enum LogoSizes: String, Codable {
    case w45
    case w92
    case w154
    case w185
    case w300
    case w500
    case original
}

enum PosterSizes: String, Codable {
    case w342
    case w500
    case w780
    case original
}
