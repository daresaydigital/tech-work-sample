//
//  HomeMovieCell.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import UIKit
protocol MovieCellDelagate: AnyObject {
    func isFaved(_ isFaved: Bool, model: MovieModel, cellIndex: Int)
}
class HomeMovieCell: UICollectionViewCell {
    
    // MARK: - IBOutlets
    @IBOutlet var thumbnailImageView: UIImageView!
    @IBOutlet var FavBtn: UIButton!
    @IBOutlet var titleLabel: UILabel!
    
    // MARK: - Properties
    private lazy var placeHolderImage: UIImage = {
        let image = UIImage(systemName: "film")!.withTintColor(.systemGreen, renderingMode: .alwaysTemplate)
        return image
    }()
    private var isFaved: Bool! {
        didSet {
            model.isFaved = isFaved
            FavBtn.imageView?.image = isFaved ? favedImage : unfavedImage
        }
    }
    
    private let favedImage: UIImage = UIImage(systemName: "heart.fill")!
    private let unfavedImage: UIImage = UIImage(systemName: "heart")!
    private var model: MovieModel!
    var index: Int!
    weak var delegate: MovieCellDelagate?
    
    // MARK: - LifeCycle
    override func awakeFromNib() {
        super.awakeFromNib()
        initialize()
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        thumbnailImageView.image = nil
        titleLabel.text = ""
        model = nil
        index = nil
    }
    
    // MARK: - setupView
    private func initialize() {
        titleLabel.backgroundColor = UIColor.systemGreen.withAlphaComponent(0.5)
        FavBtn.addTarget(self, action: #selector(favButtonPressed), for: .touchUpInside)
        setCornerRadius(15)
    }
    
    @objc private func favButtonPressed(_ button: UIButton) {
        isFaved = !isFaved
        delegate?.isFaved(isFaved, model: model, cellIndex: index)
    }
    
    // MARK: - Setters and Getters
    private func fill(_ model: MovieModel) {
        print("🔴 isFaved ", model.isFaved)
        self.model = model
        isFaved = model.isFaved
        titleLabel.text = model.title
        if let imageURL = imageURL(model.posterPath) {
            thumbnailImageView.load(url: imageURL, placeholder: placeHolderImage)
        }
    }
    
    private func imageURL(_ url: String?) -> URL? {
        guard let url = url else { return nil }
        let urlBuilder = ImageBaseUrlBuilder(forTypeAndSize: .poster(.w342))
        let fullUrl = urlBuilder.createURL(filePath: url)
        return fullUrl
    }
}

extension HomeMovieCell: DaMoviesCollectionViewCell {
    
    func configureCellWith(_ item: MovieModel) {
        fill(item)
    }
    
    func configCellSize(item: MovieModel) -> CGSize {
        let deviceWidth = UIScreen.main.bounds.width
        return CGSize(width: (deviceWidth - 12) / 3, height: 180)
    }
}
