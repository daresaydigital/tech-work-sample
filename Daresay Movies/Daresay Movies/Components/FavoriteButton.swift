//
//  FavoriteButton.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/15/21.
//

import UIKit

class FavoriteButton: UIButton {
    
    var isFaved: Bool! = false {
        didSet {
            self.imageView?.image = isFaved ? favedImage : unfavedImage
        }
    }
    
    private let favedImage: UIImage = UIImage(systemName: "heart.fill")!
    private let unfavedImage: UIImage = UIImage(systemName: "heart")!
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        
        self.setTitle("", for: .normal)
        self.imageView?.image = unfavedImage
        self.addTarget(self, action: #selector(favButtonPressed), for: .touchUpInside)
    }
    
    @objc private func favButtonPressed(_ button: UIButton) {
        isFaved = !isFaved
        isFavBinder(isFaved)
    }
    
    private var isFavBinder: DataCompletion<Bool> = { _ in }
    func bind(completion: @escaping DataCompletion<Bool>) {
        self.isFavBinder = completion
    }
}
