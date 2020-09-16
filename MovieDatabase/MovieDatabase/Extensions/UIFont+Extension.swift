//
//  UIFont+Extension.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

extension UIFont {
    static func preferredFont(for style: TextStyle, weight: Weight) -> UIFont {
        if weight == .regular {
            return .preferredFont(forTextStyle: style)
        } else {
            let metrics = UIFontMetrics(forTextStyle: style)
            let desc = UIFontDescriptor.preferredFontDescriptor(withTextStyle: style)
            let font = UIFont.systemFont(ofSize: desc.pointSize, weight: weight)
            return metrics.scaledFont(for: font)
        }
    }
}
