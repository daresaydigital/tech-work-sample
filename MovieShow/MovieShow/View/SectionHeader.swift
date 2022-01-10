//
//  SectionHeader.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import UIKit

class SectionHeader: UICollectionReusableView {
    static let reuseID = "SectionHeader"
    
    let title: UILabel = {
        let title = UILabel()
        title.textColor = .white
        title.font = UIFontMetrics.default.scaledFont(for: UIFont.systemFont(ofSize: 30, weight: .bold))
        return title
    }()
    
    let separator: UIView = {
        let view = UIView(frame: .zero)
        view.backgroundColor = .white
        view.anchor(height: 1)
        return view
    }()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        configureUI()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func configureUI() {
        let stack = UIStackView(arrangedSubviews: [title, separator])
        stack.axis = .vertical
        addSubview(stack)
        stack.anchor(top: topAnchor, left: leftAnchor, bottom: bottomAnchor, right: rightAnchor, paddingLeft: 20, paddingBottom: 10, paddingRight: 20)
    }
}
