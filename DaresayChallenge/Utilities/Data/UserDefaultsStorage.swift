//
//  UserDefaultsStorage.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import Foundation

@propertyWrapper
struct UserDefaultsStorage<T: Codable> {
    
    struct Wrapper<T>: Codable where T : Codable {
        let wrapper: T
    }
    
    private let key: UserDefaultsData.UserDefaultsKey
    private let defaultValue: T
    private let storage: UserDefaults = .standard
    
    init(_ key: UserDefaultsData.UserDefaultsKey, defaultValue: T) {
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
