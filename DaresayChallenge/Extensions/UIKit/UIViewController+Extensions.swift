//
//  UIViewController+Extensions.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import UIKit

protocol AlertViewable {
    func showAlert(title: String, message: String, options: [String], completion: ((String?) -> Void)?)
}
extension UIViewController: AlertViewable {
    func showAlert(title: String, message: String, options: [String], completion: ((String?) -> Void)? = nil) {
        let alertController = UIAlertController(title: title, message: message, preferredStyle: .alert)
        
        if !options.isEmpty {
            options.forEach { option in
                alertController.addAction(UIAlertAction(title: option, style: .default, handler: { (action) in
                    completion?(option)
                }))
            }
        }
        
        alertController.addAction(UIAlertAction(title: "Dismiss", style: .cancel, handler: nil))
        
        self.present(alertController, animated: true, completion: nil)
    }
}
