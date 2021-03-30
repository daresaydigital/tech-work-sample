//
//  VerticalFlowLayout.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-26.
//

import Foundation

/*
 snatched from:
 https://github.com/DeluxeAlonso/UpcomingMovies/blob/development/UpcomingMovies/ViewComponents/Layouts/VerticalFlowLayout.swift
 */

import UIKit

final class VerticalFlowLayout: UICollectionViewFlowLayout {

    private var preferredWidth: CGFloat
    private var preferredHeight: CGFloat
    private let margin: CGFloat
    private let minColumns: Int

    init(preferredWidth: CGFloat,
         preferredHeight: CGFloat,
         margin: CGFloat = 16.0,
         minColumns: Int = .zero) {
        self.preferredWidth = preferredWidth
        self.preferredHeight = preferredHeight
        self.margin = margin
        self.minColumns = minColumns
        super.init()

        sectionInsetReference = .fromSafeArea
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError()
    }

    override func prepare() {
        super.prepare()
        var finalWidth = preferredWidth
        var finalHeight = preferredHeight
        if minColumns != .zero, let collectionView = collectionView {
            let totalHorzontalSafeAreaInset = collectionView.safeAreaInsets.left + collectionView.safeAreaInsets.right

            let horizontalSpacePerItem = margin * 2 + totalHorzontalSafeAreaInset + minimumInteritemSpacing
            let totalHorizontalSpace = horizontalSpacePerItem * CGFloat(minColumns - 1)
            let maximumItemWidth = ((collectionView.bounds.size.width - totalHorizontalSpace) / CGFloat(minColumns)).rounded(.down)

            if maximumItemWidth < preferredWidth {
                finalWidth = maximumItemWidth
                finalHeight = finalWidth * (preferredHeight / preferredWidth)
            }
        }

        itemSize = CGSize(width: finalWidth, height: finalHeight)
        sectionInset = UIEdgeInsets(top: margin, left: margin,
                                    bottom: margin, right: margin)
    }
}
