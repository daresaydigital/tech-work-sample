//
//  MoviesCollectionViewHeader.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 15.01.2023.
//

import UIKit
import SnapKit

final class MoviesCollectionReusableView: UICollectionReusableView {
    
    static let identifier = String(describing: MoviesCollectionReusableView.self)
    private var segmentedControl: UISegmentedControl!
    var handleSegmentChange: ((Int) -> Void)?

    override init(frame: CGRect) {
        super.init(frame: frame)
        
        setupView()
        setupHierarchy()
        setupLayout()
    }

    private func setupView() {
        backgroundColor = .bg
        let titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.text]
        let segmentItems = Constant.MoviesCollectionReusableView.segmentItems
        segmentedControl = UISegmentedControl(items: segmentItems)
        segmentedControl.selectedSegmentIndex = 0
        segmentedControl.addTarget(self, action: #selector(self.segmentedValueChanged(_:)), for: .valueChanged)
        segmentedControl.selectedSegmentTintColor = .bg
        segmentedControl.setTitleTextAttributes(titleTextAttributes, for: .normal)
        segmentedControl.setTitleTextAttributes(titleTextAttributes, for: .selected)
    }
    
    private func setupHierarchy() {
        addSubview(segmentedControl)
    }
    
    private func setupLayout() {
        segmentedControl.snp.makeConstraints { make in
            make.center.equalToSuperview()
            make.width.equalTo(350)
        }
    }

    required init?(coder: NSCoder) {
        fatalError()
    }
}


extension MoviesCollectionReusableView {
    
    @objc func segmentedValueChanged(_ sender: UISegmentedControl!) {
        handleSegmentChange?(sender.selectedSegmentIndex)
    }
}
