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
struct UserDefaultStorage<T: Codable> {
    
    struct Wrapper<T>: Codable where T : Codable {
        let wrapper: T
    }
    
    private let key: UserDefaultData.UserDefaultsKey
    private let defaultValue: T
    private let storage: UserDefaults = .standard
    
    init(_ key: UserDefaultData.UserDefaultsKey, defaultValue: T) {
        self.key = key
        self.defaultValue = defaultValue
    }

    var wrappedValue: T {
        get {
            // Read value from UserDefaults
            guard let data = storage.object(forKey: key.rawValue) as? Data else {
                // Return defaultValue when no data in UserDefaults
                return defaultValue
            }
            
            // Convert data to the desire data type
            let value = try? JSONDecoder().decode(Wrapper<T>.self, from: data)
            return value?.wrapper ?? defaultValue
        }
        set {
            // Convert newValue to data
            do {
                let data = try JSONEncoder().encode(Wrapper(wrapper: newValue))
                storage.set(data, forKey: key.rawValue)
            } catch {
                storage.removeObject(forKey: key.rawValue)
                print(error)
            }
        }
    }
}
