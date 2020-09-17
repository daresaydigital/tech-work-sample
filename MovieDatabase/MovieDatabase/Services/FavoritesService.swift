//
//  FavoritesService.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-17.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import Foundation

class FavoritesService {

    static var favorites: [Movie] = []
    static var images: [String:Image] = [:]

    static func addMovie(_ movie: Movie) {
        favorites.append(movie)
        saveFavoriteMovies()
    }

    static func addImage(_ image: Image, forKey key: String) {
        images[key] = image
        saveFavoriteMovieImages()
    }

    static func removeMovie(_ movie: Movie) {
        let editedFavorites = favorites.filter({$0.id != movie.id})
        favorites = editedFavorites
        saveFavoriteMovies()
    }

    static func removeImage(for key: String) {
        images[key] = nil
        saveFavoriteMovieImages()
    }

    static func isFavoriteMovie(_ movie: Movie) -> Bool {
        return !favorites.filter({ $0.id == movie.id }).isEmpty
    }

    static func fetchFavoriteMovies() {
        guard let dirUrl = try? FileManager.default.url(
            for: .documentDirectory,
            in: .userDomainMask,
            appropriateFor: nil,
            create: true) else { return }

        let fileUrl = dirUrl.appendingPathComponent("MyMovies.json")
        let jsonDecoder = JSONDecoder()
        do {
            let data = try Data(contentsOf: fileUrl, options: [])
            let fetchedFavorites = try jsonDecoder.decode([Movie].self, from: data)
            favorites = fetchedFavorites
        }catch {
            print(error)
        }
    }

    static func fetchFavoriteMovieImages() {
        guard let dirUrl = try? FileManager.default.url(
            for: .documentDirectory,
            in: .userDomainMask,
            appropriateFor: nil,
            create: true) else { return }

        let fileUrl = dirUrl.appendingPathComponent("MyImages.json")
        let jsonDecoder = JSONDecoder()
        do {
            let data = try Data(contentsOf: fileUrl, options: [])
            let fetchedImages = try jsonDecoder.decode([String:Image].self, from: data)
            images = fetchedImages
        }catch {
            print(error)
        }
    }

    static func saveFavoriteMovies() {
        guard let dirUrl = try? FileManager.default.url(
            for: .documentDirectory,
            in: .userDomainMask,
            appropriateFor: nil,
            create: true) else { return }

        let fileUrl = dirUrl.appendingPathComponent("MyMovies.json")
        let jsonEncoder = JSONEncoder()
        do {
            let data = try jsonEncoder.encode(favorites)
            try? data.write(to: fileUrl, options: [])
        } catch {
            print(error)
        }
    }

    static func saveFavoriteMovieImages() {
        guard let dirUrl = try? FileManager.default.url(
            for: .documentDirectory,
            in: .userDomainMask,
            appropriateFor: nil,
            create: true) else { return }

        let fileUrl = dirUrl.appendingPathComponent("MyImages.json")
        let jsonEncoder = JSONEncoder()
        do {
            let data = try jsonEncoder.encode(images)
            try? data.write(to: fileUrl, options: [])
        } catch {
            print(error)
        }
    }
}
