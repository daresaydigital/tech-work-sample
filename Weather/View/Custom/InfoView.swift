//
//  InfoView.swift
//  Weather
//
//  Created by Christian  Huang on 03/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit

class InfoView: UIView {
    var detail1: String = "" {
        didSet{
            detail1Label.text = detail1
        }
    }
    var detail2: String = "" {
        didSet{
            detail2Label.text = detail2
        }
    }
    @IBOutlet private weak var title1Label: UILabel!
    @IBOutlet private weak var detail1Label: UILabel!
    @IBOutlet private weak var title2Label: UILabel!
    @IBOutlet private weak var detail2Label: UILabel!
    @IBOutlet private weak var separatorView: UIView!

    class func instanceFromNib() -> InfoView {
        let instance = UINib(nibName: "InfoView", bundle: nil).instantiate(withOwner: nil, options: nil)[0] as! InfoView
        instance.translatesAutoresizingMaskIntoConstraints = false
        instance.heightAnchor.constraint(equalToConstant: instance.bounds.height).isActive = true
        return instance
    }
    
    func configureInfoView(title1: String, title2: String, hasSeparator: Bool = true) {
        title1Label.text = title1
        title2Label.text = title2
        separatorView.alpha = hasSeparator ? 1 : 0
    }
    
    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */

}
