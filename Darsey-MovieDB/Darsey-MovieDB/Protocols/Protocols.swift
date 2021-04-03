//
//  Protocols.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 09/03/2021.
//

import Foundation

protocol LanguageDelegate{
    func chooseLanguage()
}

protocol MovieSegmentDelegate {
    func loadMovie(movieType: MovieType)
}
