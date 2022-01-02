//
//  Helper.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import UIKit
import Nuke

class ImageHelper {
    static func setupImageForView(_ imageView: UIImageView, url: String?) {
        let moviePosterPath = url ?? ""
        let request = ImageRequest(url: URL(string: "https://image.tmdb.org/t/p/w500\(moviePosterPath)")!, processors: [
            ImageProcessors.RoundedCorners(radius: 16)
        ])
        
        let options = ImageLoadingOptions(placeholder: UIImage(systemName: "film"),
                                          transition: .fadeIn(duration: 0.33),
                                          failureImage: UIImage(systemName: "film"),
                                          contentModes: .init(success: .scaleAspectFit, failure: .scaleAspectFit, placeholder: .scaleAspectFit))
        
        Nuke.loadImage(with: request, options: options, into: imageView)
    }
}
