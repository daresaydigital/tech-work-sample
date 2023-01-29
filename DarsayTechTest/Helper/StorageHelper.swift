//
//  StorageHelper.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

protocol StorageProtocol {
    
    associatedtype StoredObject: Codable
    
    func setObject(object: StoredObject)
    func getObject() -> StoredObject?
    func remove()
}

final class FavoriteStorage: StorageProtocol {
    typealias StoredObject = [Movie]
    
    private static let favoriteListKey = "favoriteListKey"

    static let shared = FavoriteStorage()
    
    var currentList: [Movie] {
        get {
            return getObject() ?? []
        }
        
        set {
            return setObject(object: newValue)
        }
    }
    
    func append(movie: Movie) {
        
        guard !currentList.contains(where: { $0.id == movie.id }) else { return }
        
        var tempList = currentList
        tempList.append(movie)
        currentList = tempList
    }
    
    func remove(movie: Movie) {
        var tempList = currentList
        
        if let index = tempList.firstIndex(of: movie) {
            tempList.remove(at: index)
            currentList = tempList
        }
    }
 
    func setObject(object: StoredObject) {
        let data = try? JSONEncoder().encode(object)
        UserDefaults.standard.set(data, forKey: Self.favoriteListKey)
    }
    
    func getObject() -> StoredObject? {
        guard let data = UserDefaults.standard.object(forKey: Self.favoriteListKey) as? Data else {
            return nil
        }

        let value = try? JSONDecoder().decode(StoredObject.self, from: data)
        return value
    }
    
    func remove() {
        UserDefaults.standard.removeObject(forKey: Self.favoriteListKey)
    }
}
