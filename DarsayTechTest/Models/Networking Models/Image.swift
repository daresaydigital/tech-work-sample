//
//  Image.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

enum Image {
    
    enum Types {
        case backDrop(BDSize)
        case logo(LogoSizes)
        case poster(PosterSizes)
        case profile(LogoSizes)
        
        var sizeString: String {
            switch self {
            case .backDrop(let bDSize):
                return bDSize.rawValue
            case .logo(let logoSizes):
                return logoSizes.rawValue
            case .poster(let posterSizes):
                return posterSizes.rawValue
            case .profile(let logoSizes):
                return logoSizes.rawValue
            }
        }
    }
    
    enum PosterSizes: String, Codable {
        case w92
        case w154
        case w185
        case w342
        case w500
        case w780
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
}
