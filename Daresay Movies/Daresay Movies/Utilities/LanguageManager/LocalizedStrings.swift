//
//  LocalizedStrings.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/13/21.
//

import Foundation

// We can also using swiftgen for generating string files
enum LocalizedStrings: String {
    
    case test = "Test_Value"
    case popularMovies
    case changeLanguage = "Change_Language"
    case cancel
    case toHome = "to_home"
    case guideline
    
    var value: String {
        return localized(key: self.rawValue)
    }
    
    private func localized(key: String) -> String {
        return NSLocalizedString(key, comment: "")
    }
}
