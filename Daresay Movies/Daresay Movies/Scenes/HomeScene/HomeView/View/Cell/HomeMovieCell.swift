//
//  HomeMovieCell.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import UIKit

class HomeMovieCell: UICollectionViewCell {
    
    // MARK: - IBOutlets
    @IBOutlet var thumbnailImageView: UIImageView!
    @IBOutlet var sizeLabel: UILabel!
    
    // MARK: - LifeCycle
    override func awakeFromNib() {
        super.awakeFromNib()
        initialize()
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        thumbnailImageView.image = nil
        sizeLabel.text = ""
    }
    
    // MARK: - setupView
    private func initialize() {
        sizeLabel.backgroundColor = UIColor.systemGreen.withAlphaComponent(0.5)
    }
    
    // MARK: - Setters and Getters
    private func fill(_ model: TestMovieModel) {
        self.thumbnailImageView.image = model.image
    }
}
