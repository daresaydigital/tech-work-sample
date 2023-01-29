//
//  LocalizeHelper.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

final class LocalizeHelper {
    
    static let shared = LocalizeHelper()

    enum Localizedkey: String {
        
        case ok
        case errorTitle
        case unknownError
        case urlCreationFailed
        case favoriteListTitle
        case popularListTitle
        case topRatedListTitle
    }
    
    func lookup(_ key: Localizedkey, _ args: CVarArg...) -> String {
    
        let format = NSLocalizedString(key.rawValue, comment: "")
        return String(format: format, locale: Locale.current, arguments: args)
    }
}
