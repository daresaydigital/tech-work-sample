//
//  MovieTableViewCell.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import UIKit

class MovieTableViewCell: UITableViewCell {
    
    // MARK: - Variables

    // MARK: - Init
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        
        setupUI()
    }
    
    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Helpers
private extension MovieTableViewCell {
    func setupUI() {
        
    }
}

// MARK: - Configuration
extension MovieTableViewCell {
    public func configureCell(with movieModel: MoviesModel) {
        
    }
}
