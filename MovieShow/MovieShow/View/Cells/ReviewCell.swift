//
//  ReviewCell.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2022-01-08.
//

import UIKit

class ReviewCell: UITableViewCell {
    
    lazy var imageVw: UIImageView = {
        let imageView = UIImageView()
        imageView.setDimensions(width: 35, height: 35)
        imageView.clipsToBounds = true
        imageView.layer.cornerRadius = 35/2
        imageView.image = SFSymbols.person
        return imageView
    }()
    
    var authorLabel = MovieTextLabel(font: 22, weight: .regular)
    var reviewContent = MovieTextLabel(font: 14, weight: .light)
    var reviewAge = MovieTextLabel(font: 16, weight: .light)
    var ratingLabel = UILabel()
    
    let cellView: UIView = {
        let view = UIView()
        view.layer.cornerRadius = 8
        view.clipsToBounds = true
        view.backgroundColor = .systemGray
        return view
    }()

    var viewmodel: ReviewViewmodel? {
        didSet {
            configure()
        }
    }
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        configureUI()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func configure() {
        guard let viewmodel = self.viewmodel else { return }
        authorLabel.text = viewmodel.author
        reviewContent.text = viewmodel.content
        reviewAge.text = viewmodel.age
        ratingLabel.text = String(repeating: "✭", count: viewmodel.rating ?? 0)
        if let url = viewmodel.authorAvatarURL {
            imageVw.load(url: url)
        }
    }
    
    private func configureUI() {
        authorLabel.setContentHuggingPriority(.defaultLow, for: .horizontal)
        let stack1 = UIStackView(arrangedSubviews: [authorLabel, ratingLabel])
        stack1.axis = .vertical
        
        let stack2 = UIStackView(arrangedSubviews: [stack1, reviewAge])
        stack2.axis = .horizontal
        
        let stack3 = UIStackView(arrangedSubviews: [stack2, reviewContent])
        stack3.axis = .vertical
        stack3.setCustomSpacing(20, after: stack2)
        cellView.addSubview(stack3)
        let stack3Padding: CGFloat = 16
        stack3.anchor(top: cellView.topAnchor, left: cellView.leftAnchor, bottom: cellView.bottomAnchor, right: cellView.rightAnchor, paddingTop: stack3Padding, paddingLeft: stack3Padding, paddingBottom: stack3Padding, paddingRight: stack3Padding)
        
       addSubview(cellView)
        let cellViewPadding: CGFloat = 20
        cellView.anchor(top: topAnchor, left: leftAnchor, bottom: bottomAnchor, right: rightAnchor, paddingTop: cellViewPadding, paddingLeft: cellViewPadding, paddingRight: cellViewPadding
        )
    }
}
