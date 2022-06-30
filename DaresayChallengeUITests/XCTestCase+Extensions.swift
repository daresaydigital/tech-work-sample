//
//  XCTestCase+Extensions.swift
//  DaresayChallengeUITests
//
//  Created by Keihan Kamangar on 2022-06-30.
//

import XCTest

extension XCTestCase {
    func findElement(in item: XCUIElementQuery, with identifier: AccessibilityIdentifiers) -> XCUIElement {
        item[identifier.rawValue]
    }
}
