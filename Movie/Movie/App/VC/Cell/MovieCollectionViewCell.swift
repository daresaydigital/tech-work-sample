//
//  MovieCollectionViewCell.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-26.
//

import UIKit
import Nuke

final class MovieCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var backgroundImageView: UIImageView!
    @IBOutlet weak var mainLabel: UILabel!
    @IBOutlet weak var detailLabel: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
        setupUI()
    }

    override func prepareForReuse() {
        backgroundImageView.image = nil
        Nuke.cancelRequest(for: backgroundImageView)
    }

    // this normally should be passed from a cell viewModel. Due to simplicity of the data however, it can be done like this as well.
    public func setupCell(model: Movie) {
        mainLabel.text = model.title
        detailLabel.text = model.releaseDate

        let resize = ImageProcessors.Resize(size: backgroundImageView.bounds.size)
        let req = ImageRequest(url: model.backDropUrl, processors: [resize])
        Nuke.loadImage(with: req, into: backgroundImageView)
    }
    private func setupUI() {
        mainLabel.font = UIFont.systemFont(ofSize: 20)
        mainLabel.numberOfLines = 0
        mainLabel.textColor = .white
        detailLabel.font = UIFont.systemFont(ofSize: 15)
        detailLabel.textColor = .white
    }

}
