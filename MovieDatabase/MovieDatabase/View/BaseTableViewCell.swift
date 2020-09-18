//
//  BaseTableViewCell.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-18.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

class BaseTableViewCell: UITableViewCell {

    static var reuseIdentifier: String {
        return nibName
    }

    static var nib: UINib {
        return UINib(nibName: nibName, bundle: .main)
    }

    private static var nibName: String {
        return String(describing: self)
    }

}
