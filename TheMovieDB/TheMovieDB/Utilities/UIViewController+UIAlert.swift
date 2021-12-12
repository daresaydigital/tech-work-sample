//
//  UIViewController+UIAlert.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import UIKit

extension UIViewController {
    
    func showErrorIfDisplayable(error: TMSError) {
        if error.isDisplayable {
            let alert = UIAlertController(title: "Oops!",
                                          message: error.localizedDescription,
                                          preferredStyle: .alert)
            
            alert.addAction(UIAlertAction(title: "OK",
                                          style: .default,
                                          handler: nil)
            )
            
            self.present(alert, animated: true, completion: nil)
        }
    }
}
