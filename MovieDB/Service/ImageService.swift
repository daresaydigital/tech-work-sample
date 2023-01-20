//
//  ImageService.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 17.01.2023.
//

import UIKit

final class ImageService {
    
    static let shared = ImageService()
    private init() {}
    
    private let imageCache = NSCache<NSString, UIImage>()

    func checkCache(from imageUrl: URL, completion: @escaping (UIImage?) -> Void) {
        let cacheKey = NSString(string: imageUrl.absoluteString)
        if let image = imageCache.object(forKey: cacheKey) {
            completion(image)
            return
        }
        completion(nil)
    }
    
    func dataToImage(data: Data) -> UIImage? {
        if let image = UIImage(data: data) {
            return image
        }
        return nil
    }
}
