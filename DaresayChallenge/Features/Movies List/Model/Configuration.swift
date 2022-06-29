//
//  Configuration.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import Foundation

extension ServerModels {
    enum Configuration {
        struct Request {}
        
        typealias Response = ConfigurationModel
    }
}

struct ConfigurationModel: Codable {
    var images: ImageConfiguration?
    var changeKeys: [String]?
}

// MARK: - Images
struct ImageConfiguration: Codable {
    var baseURL: String?
    var secureBaseURL: String?
    var backdropSizes: [String]?
    var logoSizes: [String]?
    var posterSizes: [String]?
    var profileSizes: [String]?
    var stillSizes: [String]?
    
    enum CodingKeys: String, CodingKey {
        case baseURL = "baseUrl"
        case secureBaseURL = "secureBaseUrl"
        case backdropSizes
        case logoSizes
        case posterSizes
        case profileSizes
        case stillSizes
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
