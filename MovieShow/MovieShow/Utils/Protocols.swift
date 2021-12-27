//
//  Protocols.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import Foundation

protocol SelfConfiguringCell {
    static var reuseID: String { get }
    func configure(with viewmodel: MovieViewModel)
}
