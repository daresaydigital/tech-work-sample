//
//  Helper.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 19.01.2023.
//

import UIKit

final class Helper {
    
    static func distanceBetween(bottomOf view1: UIView, andTopOf view2: UIView) -> CGFloat {
        let frame2 = view1.convert(view2.bounds, from: view2)
        return abs(frame2.minY - view1.bounds.maxY)
    }
}
