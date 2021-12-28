//
//  UIIMageView+EXT.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import UIKit

extension UIImageView {
    func load(url: URL) {
        let cache = NSCache<NSString, UIImage>()
        let urlString = url.absoluteString as NSString
        if let image = cache.object(forKey: urlString) {
            self.image = image
        }
        DispatchQueue.global().async { [weak self] in
            if let data = try? Data(contentsOf: url) {
                if let image = UIImage(data: data) {
                    DispatchQueue.main.async {
                        self?.image = image
                    }
                    cache.object(forKey: urlString)
                }
            }
        }
    }
}
