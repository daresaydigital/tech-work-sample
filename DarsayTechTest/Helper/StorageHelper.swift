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
    typealias StoredObject = [Movie]
    
    private static let favoriteListKey = "favoriteListKey"

    private static let shared = FavoriteStorage()
    
    static var currentList: [Movie] {
        get {
            return shared.getObject(by: favoriteListKey) ?? []
        }
        
        set {
            return shared.setObject(for: favoriteListKey, object: newValue)
        }
    }
    
    static func append(movie: Movie) {
        
        guard currentList.filter({ $0.id == movie.id }).isEmpty else { return }
        
        var tempList = currentList
        tempList.append(movie)
        currentList = tempList
    }
    
    static func remove(movie: Movie) {
        var tempList = currentList
        
        if let index = tempList.firstIndex(of: movie) {
            tempList.remove(at: index)
            currentList = tempList
        }
    }
    
    static func removeAll() {
        shared.remove(key: favoriteListKey)
    }
}
