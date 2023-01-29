//
//  UIButton.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/9/1401 AP.
//

import Foundation
import UIKit

extension UIButton {
    public func addPrimaryAction(_ handler: @escaping UIActionHandler) {
        addAction(.init(handler: handler), for: .primaryActionTriggered)
    }
}
