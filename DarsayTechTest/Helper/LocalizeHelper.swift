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
        
        case unknownError
        case urlCreationFailed
    }
    
    func lookup(_ key: Localizedkey, _ args: CVarArg...) -> String {
    
        let format = NSLocalizedString(key.rawValue, comment: "")
        return String(format: format, locale: Locale.current, arguments: args)
    }
}
