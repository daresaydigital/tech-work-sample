//
//  UserDefaultData.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/13/21.
//

import Foundation

struct UserDefaultData {
    
    enum UserDefaultsKey: String {
        case appleLanguages  = "AppleLanguages"
        case currentLanguage
        case accessToken
        case configModel
        case favorites
    }
    
    @UserDefaultStorage(.currentLanguage, defaultValue: "en")
    static var currentLanguage: String
    
    @UserDefaultStorage(.appleLanguages, defaultValue: ["en"])
    static var appleLanguage: [String]
    
    @UserDefaultStorage(.accessToken, defaultValue: Constants.accessToken)
    static var accessToken: String
    
    @UserDefaultStorage(.configModel, defaultValue: ConfigModel())
    static var configModel: ConfigModel
    
    @UserDefaultStorage(.favorites, defaultValue: MovieArrayModel())
    static var favoriteList: MovieArrayModel
    
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
