//
//  UIImage.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/9/1401 AP.
//

import Foundation
import UIKit

extension UIImage {
    
    enum SFSymbol: String {
        case favorite = "heart"
        case filledFavorite = "heart.fill"
        case favoriteList = "heart.text.square.fill"
    }
    
    convenience init?(symbolicName: SFSymbol, pointSize: CGFloat) {
        
        let symbolConfiguration = UIImage.SymbolConfiguration(pointSize: pointSize)
        
        self.init(systemName: symbolicName.rawValue, withConfiguration: symbolConfiguration)
    }
}
