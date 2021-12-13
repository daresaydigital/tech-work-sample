//
//  UserDefaultData.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/13/21.
//

import Foundation

struct UserDefaultData {
    
    enum UserDefaultsKey: String {
        case test
    }
    
    @UserDefaultStorage(.test, defaultValue: "test string")
    static var test: String
    
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
