//
//  UIActivityIndicatorView+Extension.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 18.01.2023.
//

import UIKit

extension UIActivityIndicatorView {
    
    func startAnimatingAsync() {
        DispatchQueue.main.async { [weak self] in
            self?.startAnimating()
        }
    }
    
    func stopAnimatingAsync() {
        DispatchQueue.main.async { [weak self] in
            self?.stopAnimating()
        }
    }
}
