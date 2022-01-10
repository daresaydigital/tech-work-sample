//
//  Cells+EXT.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2022-01-08.
//

import UIKit

extension UICollectionViewCell {
    static var reuseID: String {
        String(describing: self)
    }
}

extension UITableViewCell {
    static var reuseID: String {
        String(describing: self)
    }
}
