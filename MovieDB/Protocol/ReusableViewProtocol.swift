//
//  ReusableViewProtocol.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 15.01.2023.
//

import Foundation

protocol ReusableViewProtocol {

    static var reuseIdentifier: String { get }
}

extension ReusableViewProtocol {

    static var reuseIdentifier: String {
        return String(describing: self)
    }
}
