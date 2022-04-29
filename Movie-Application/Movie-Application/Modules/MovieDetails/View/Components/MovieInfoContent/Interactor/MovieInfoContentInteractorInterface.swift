//
//  MovieInfoContentInteractorInterface.swift
//  MovieInfoContent
//
//  Created by mohannazakizadeh on 4/29/22.
//

import Foundation
import UIKit

protocol MovieInfoContentInteractorInterface: InteractorPresenterInterface {
    func getMovieImage(path: String) -> UIImage?
}
