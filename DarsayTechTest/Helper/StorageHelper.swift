//
//  StorageHelper.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

protocol StorageProtocol {
    
    associatedtype StoredObject: Codable
    
    func setObject(for key: String, object: StoredObject)
    func getObject(by key: String) -> StoredObject?
    func remove(key: String)
}

extension StorageProtocol {
    
    func setObject(for key: String, object: StoredObject) {
        let data = try? JSONEncoder().encode(object)
        UserDefaults.standard.set(data, forKey: key)
    }
    
    func getObject(by key: String) -> StoredObject? {
        guard let data = UserDefaults.standard.object(forKey: key) as? Data else {
            return nil
        }

        let value = try? JSONDecoder().decode(StoredObject.self, from: data)
        return value
    }
    
    func remove(key: String) {
        UserDefaults.standard.removeObject(forKey: key)
    }
}

final class FavoriteStorage: StorageProtocol {
    typealias StoredObject = Movie
    
    static let shared = FavoriteStorage()
    
}
