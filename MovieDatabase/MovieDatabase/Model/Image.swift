//
//  Image.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

class Image: Codable {
    let imageData: Data!

    init(withImage image: UIImage) {
        self.imageData = image.jpegData(compressionQuality: 1.0)
    }

    func getImage() -> UIImage? {
        guard let imageData = self.imageData else {
            return nil
        }
        let image = UIImage(data: imageData)

        return image
    }
}
