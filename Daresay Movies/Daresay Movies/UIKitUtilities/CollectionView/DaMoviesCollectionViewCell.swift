//
//  DaMoviesCollectionViewCell.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import UIKit

protocol DaMoviesCollectionViewCell: UICollectionViewCell {
    associatedtype CellViewModel
    func configureCellWith(_ item: CellViewModel)
    func configCellSize(item: CellViewModel) -> CGSize
}
