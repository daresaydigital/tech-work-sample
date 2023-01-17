//
//  UIImageView+ImageLoader.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

extension UIImageView {
    func loadImage(at url: URL) {
        UIImageLoader.loader.load(url, for: self)
    }

    func cancelImageLoad() {
        UIImageLoader.loader.cancel(for: self)
    }
}
