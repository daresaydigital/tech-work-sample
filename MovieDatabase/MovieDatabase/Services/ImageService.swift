//
//  ImageService.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

enum ImageLoaderError: Error {
    case imageNotLoaded
}

class ImageService {

    static private let imageCache: NSCache<NSString, UIImage> = NSCache<NSString, UIImage>()

    static func getImage(url: URL?, isFavorite: Bool = false, completion: @escaping (Result<UIImage,ImageLoaderError>)->Void) {
        guard let imageURL = url else {
            completion(.failure(.imageNotLoaded))
            return
        }

        if let cachedImage = imageCache.object(forKey: imageURL.absoluteString as NSString) {
            completion(.success(cachedImage))
        } else {
            let task = URLSession.shared.dataTask(with: imageURL) { (data, response, error) in
                guard error == nil else {
                    completion(.failure(.imageNotLoaded))
                    return
                }
                if let responseURL = response?.url, responseURL == imageURL,
                    let data = data,
                    let image = UIImage(data: data) {
                    completion(.success(image))
                } else {
                    completion(.failure(.imageNotLoaded))
                }
            }
            task.resume()
        }
    }
}
