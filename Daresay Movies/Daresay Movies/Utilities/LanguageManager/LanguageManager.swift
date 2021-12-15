//
//  LanguageManager.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/13/21.
//

import UIKit

enum SupportedLanguages: Int, CaseIterable {
    case english
    case german
    case finnish
    case swedish
    
    var languageDirection: LanguageDirection {
        switch self {
        case .english:
            return .ltr
        case .german:
            return .ltr
        case .finnish:
            return .ltr
        case .swedish:
            return .ltr
        }
    }
}

@objc enum LanguageDirection: Int {
    case rtl
    case ltr
}

// MARK: - Language Managable Protocol
protocol LanguageManagable {
    var currentLanguage: SupportedLanguages { get set }
    var allSupportedLanguages: [SupportedLanguages] { get }
}

// MARK: - Language Manager
class LanguageManager: LanguageManagable {
    // MARK: - Properties
    static let shared: LanguageManager = LanguageManager()
    
    var allSupportedLanguages: [SupportedLanguages] = SupportedLanguages.allCases
    var currentLanguage: SupportedLanguages {
        get {
            let identifier = UserDefaultData.currentLanguage
            return SupportedLanguages(identifier: identifier)
        }
        set {
            UserDefaultData.currentLanguage = newValue.identifier
            UIView.appearance().semanticContentAttribute = newValue.direction.semantic
            Bundle.setLanguage(newValue.identifier)
        }
    }
    
    var languagecalendar: Calendar {
        let locale = Locale(identifier: currentLanguage.locale)
        return locale.calendar
    }
    // MARK: - Methods
    private init() {}
    
    class func isAValidLanguageIdentifier(_ identifier: String) -> Bool {
        for language in shared.allSupportedLanguages where language.identifier == identifier {
            return true
        }
        return false
    }
}

// MARK: - Extensions
// MARK: Supported Languages
extension SupportedLanguages {
    var text: String {
        switch self {
        case .english:
            return "English"
        case .finnish:
            return "Suomi"
        case .german:
            return "Deutsch"
        case .swedish:
            return "Swedish"
        }
    }
    
    var identifier: String {
        switch self {
        case .english:
            return "en-US"
        case .finnish:
            return "fi"
        case .german:
            return "de"
        case .swedish:
            return "sv"
        }
    }
    
    var direction: LanguageDirection {
        switch self {
        case .english:
            return .ltr
        case .finnish:
            return .ltr
        case .german:
            return .ltr
        case .swedish:
            return .ltr
        }
    }
    
    var textAlignment: NSTextAlignment {
        switch self {
        case .english:
            return .left
        case .finnish:
            return .left
        case .german:
            return .left
        case .swedish:
            return .left
        }
    }
    
    var oppositeTextAlignment: NSTextAlignment {
        switch self {
        case .english:
            return .left
        case .finnish:
            return .left
        case .german:
            return .left
        case .swedish:
            return .left
        }
    }
    
    var locale: String {
        switch self {
        case .english:
            return "en"
        case .finnish:
            return "fi"
        case .german:
            return "de"
        case .swedish:
            return "sv"
        }
    }
    
    init(identifier: String?) {
        switch identifier ?? "en-US" {
        case "en-US":
            self = .english
        case "fi":
            self = .finnish
        case "de":
            self = .german
        case "sv":
            self = .swedish
        default:
            self = .english
        }
    }
}

// MARK: LanguageDirection
extension LanguageDirection {
    var semantic: UISemanticContentAttribute {
        switch self {
        case .ltr:
            return .forceLeftToRight
        case .rtl:
            return .forceRightToLeft
        }
    }
}
