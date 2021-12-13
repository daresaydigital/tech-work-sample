//
//  UserDefaultStorage.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/13/21.
//

import Foundation
/*
 I have also uploaded the better version of this anotation and property wrapper as 'SwiftDataManager' on my github
 link: https://github.com/EmadBeyrami/SwiftDataManager
 I coded a lighter one for this use case.
 */

// Responsible for UserDefault of App
@propertyWrapper
struct UserDefaultStorage<T> {
    let key: UserDefaultData.UserDefaultsKey
    let defaultValue: T

    init(_ key: UserDefaultData.UserDefaultsKey, defaultValue: T) {
        self.key = key
        self.defaultValue = defaultValue
    }

    var wrappedValue: T {
        get {
            return UserDefaults.standard.object(forKey: key.rawValue) as? T ?? defaultValue
        }
        set {
            UserDefaults.standard.set(newValue, forKey: key.rawValue)
            UserDefaults.standard.synchronize()
        }
    }
}
