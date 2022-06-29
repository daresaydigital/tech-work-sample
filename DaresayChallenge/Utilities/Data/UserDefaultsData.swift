//
//  UserDefaultsData.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import Foundation

struct UserDefaultsData {
    
    enum UserDefaultsKey: String {
        case accessToken
        case configModel
        case favorites
    }
    
    @UserDefaultsStorage(.accessToken, defaultValue: APIKey.readAccessToken.rawValue)
    static var accessToken: String
    
    @UserDefaultsStorage(.configModel, defaultValue: ConfigurationModel())
    static var configModel: ConfigurationModel
    
    @UserDefaultsStorage(.favorites, defaultValue: [MoviesModel]())
    static var favoriteList: [MoviesModel]
    
    static func clearUserDefaultFor(_ key: UserDefaultsKey) {
        UserDefaults.standard.removeObject(forKey: key.rawValue)
        UserDefaults.standard.synchronize()
    }
    
    static func clearAllUserDefault() {
        if let bundleID = Bundle.main.bundleIdentifier {
            UserDefaults.standard.removePersistentDomain(forName: bundleID)
        }
        UserDefaults.standard.synchronize()
    }
}
