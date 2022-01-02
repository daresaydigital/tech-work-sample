//
//  MovieDetailOverview.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import UIKit

class MovieDetailOverview: UIView {
    private var movieModel: MovieInfoModel
    
    init(frame: CGRect, movieModel: MovieInfoModel) {
        self.movieModel = movieModel
        super.init(frame: frame)
        self.backgroundColor = .clear
        self.overviewTitle.isHidden = false
        self.overviewText.isHidden = false
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private lazy var overviewTitle: UIView = {
        let titleView = UITextView(frame: .zero)
        titleView.translatesAutoresizingMaskIntoConstraints = false
        titleView.isScrollEnabled = false
        titleView.isEditable = false
        titleView.text = "Overview"
        titleView.textColor = .lightGray
        titleView.font = .boldSystemFont(ofSize: 22)
        self.addSubview(titleView)
        titleView.topAnchor.constraint(equalTo: self.topAnchor, constant: 0).isActive = true
        titleView.heightAnchor.constraint(equalToConstant: 40).isActive = true
        titleView.leadingAnchor.constraint(equalTo: self.leadingAnchor, constant: 0).isActive = true
        titleView.trailingAnchor.constraint(equalTo: self.trailingAnchor, constant: 0).isActive = true
        return titleView
    }()
    
    private lazy var overviewText: UITextView = {
        let textView = UITextView(frame: .zero)
        textView.translatesAutoresizingMaskIntoConstraints = false
        textView.isEditable = false
        textView.text = movieModel.overview
        textView.textColor = .gray
        textView.font = .systemFont(ofSize: 16)
        self.addSubview(textView)
        textView.topAnchor.constraint(equalTo: overviewTitle.bottomAnchor, constant: 0).isActive = true
        textView.bottomAnchor.constraint(equalTo: self.bottomAnchor).isActive = true
        textView.leadingAnchor.constraint(equalTo: self.leadingAnchor, constant: 0).isActive = true
        textView.trailingAnchor.constraint(equalTo: self.trailingAnchor, constant: 0).isActive = true
        return textView
    }()
}
