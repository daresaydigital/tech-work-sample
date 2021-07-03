//
//  ImageHandler.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-03.
//

import Foundation
import SwiftUI

enum imageSize: String {
    case w92, w154, w185, w342, w500, w780, original
}

class ImageHandler: ObservableObject {
    @Published private(set) var image: UIImage?
    
    func loadImage(withPath posterPath: String, size: imageSize? = .w342){
        guard let url = URL(string: "https://image.tmdb.org/t/p/\(size!.rawValue)\(posterPath)") else{
                print("Image url broken")
                return
        }
        
        URLSession.shared.dataTask(with: url) { data, _, error in
            if let data = data {
                DispatchQueue.main.sync {
                    self.image = UIImage(data: data) ?? UIImage()
                }
                return
            }
            print("Error while downloading image. Error: \(String(describing: error))")
        }.resume()
    }
}
