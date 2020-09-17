//
//  EmptyStateView.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-17.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

protocol EmptyStateDelegate: AnyObject {
    func emptyStateButtonTapped()
}

class EmptyStateView: UIView {

    @IBOutlet var contentView: UIView!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var button: UIButton!
    
    weak var delegate: EmptyStateDelegate?

    var nibName: String {
        return String(describing: type(of: self))
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        loadView()
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        loadView()
    }

    private func loadView() {
        contentView = Bundle.main.loadNibNamed(nibName, owner: self, options: nil)![0] as? UIView
        contentView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        contentView.frame = bounds

        addSubview(contentView)
    }

    func configure(for emptyState: EmptyState, delegate: EmptyStateDelegate) {
        imageView.image = UIImage(systemName: emptyState.imageName)
        titleLabel.text = emptyState.title
    }

    @IBAction func didTapOnButton(_ sender: Any) {
        delegate?.emptyStateButtonTapped()
    }
}
