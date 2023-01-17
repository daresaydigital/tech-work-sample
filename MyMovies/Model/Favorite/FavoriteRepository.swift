//
//  FavoriteRepository.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import Foundation

class FavoriteRepository {
    enum Key: String {
        case keys
    }

    let userDefaults: UserDefaults

    init(userDefaults: UserDefaults = .standard) {
        self.userDefaults = userDefaults
    }

    func insetOrUpdateMovie(for movieId: Int64, _ trending: TrendingViewModel) {
        let encoded = encode(trending)
        let movieIdString = String(movieId)

        var keys: [String]? = readValue(for: Key.keys.rawValue)
        if keys == nil {
            keys = [movieIdString]
        } else {
            if !keys!.contains(movieIdString) {
                keys?.append(movieIdString)
            }
        }

        saveValue(for: movieIdString, value: encoded)
        saveValue(for: Key.keys.rawValue, value: keys!)
    }

    func deleteMovie(for movieId: Int64) {
        let movieIdString = String(movieId)

        let keys: [String]? = readValue(for: Key.keys.rawValue)
        if var keys {
            keys.removeAll(where: { $0 == movieIdString })
            if keys.count > 0 {
                saveValue(for: Key.keys.rawValue, value: keys)
            } else {
                removeValue(for: Key.keys.rawValue)
            }
        }

        removeValue(for: movieIdString)
    }

    func getAllFavorites() -> [TrendingViewModel] {
        let favorites: [String]? = readValue(for: Key.keys.rawValue)

        guard let favorites else {
            return []
        }

        return favorites.compactMap { movieId in
            guard let data: String = self.readValue(for: movieId) else { return nil }
            do {
                let trendingViewModel = try self.decode(Data(data.utf8))
                return trendingViewModel
            } catch {
                return nil
            }
        }
    }

    private func encode(_ value: TrendingViewModel) -> String {
        let jsonDecoder = JSONEncoder()
        do {
            let data = try jsonDecoder.encode(value.getTrendingResult())
            return String(data: data, encoding: .utf8) ?? ""
        } catch {
            return ""
        }
    }

    private func decode(_ value: Data) throws -> TrendingViewModel {
        let jsonDecoder = JSONDecoder()
        do {
            let data = try jsonDecoder.decode(TrendingResult.self, from: value)
            return TrendingViewModel(trending: data)
        }
    }

    private func saveValue(for key: String, value: Any) {
        userDefaults.set(value, forKey: key)
    }

    private func readValue<T>(for key: String) -> T? {
        let value = userDefaults.value(forKey: key) as? T
        return value
    }

    private func removeValue(for key: String) {
        userDefaults.removeObject(forKey: key)
    }
}
