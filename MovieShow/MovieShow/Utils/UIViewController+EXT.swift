//
//  UIViewController+EXT.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import UIKit

extension UIViewController {
    func presentErrorMessageAlert(title: String, message: String) {
        let ac = UIAlertController(title: title, message: message, preferredStyle: .alert)
        ac.addAction(UIAlertAction(title: "OK", style: .cancel))
        present(ac, animated: true)
    }
    
}
