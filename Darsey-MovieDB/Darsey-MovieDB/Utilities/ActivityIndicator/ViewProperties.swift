//
//  ViewProperties.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 09/03/2021.
//

import UIKit

class ViewProperties {
    static func configureCircularViewWithShadow(backgroundView: UIView ){
        backgroundView.layer.shadowColor = UIColor.black.cgColor
        backgroundView.layer.shadowOpacity = 0.7
        backgroundView.layer.shadowOffset = CGSize(width: 0, height: 2)
        backgroundView.layer.shadowRadius = 5
        backgroundView.layer.shouldRasterize = false
        backgroundView.layer.cornerRadius = backgroundView.frame.height / 2
    }
    
    static func configureViewWithRoundBorderAndShadow(backgroundView: UIView ){
        backgroundView.layer.shadowColor = UIColor.black.cgColor
        backgroundView.layer.shadowOpacity = 0.7
        backgroundView.layer.shadowOffset = CGSize(width: 0, height: 3)
        backgroundView.layer.shadowRadius = 5
        backgroundView.layer.shouldRasterize = false
        backgroundView.layer.cornerRadius = 8
    }
    
    static func animateViewInfinitely(backgroundView: UIView ){
        Timer.scheduledTimer(withTimeInterval: 0.5, repeats: true) { (timer) in
            if backgroundView.window != nil {
                UIView.animate(withDuration: 0.25, delay: 0, options: [.curveEaseIn], animations: {
                    backgroundView.transform = CGAffineTransform(rotationAngle: .pi)
                }, completion: { (_) in
                    UIView.animate(withDuration: 0.25, delay: 0, options: [.curveEaseOut], animations: {
                        backgroundView.transform = CGAffineTransform(rotationAngle: .pi * 2)
                    }, completion: nil)})
            }
            else {
                timer.invalidate()
            }
        }
    }
    
    static func animateBounceEffect(backgroundView: UIView ){
        backgroundView.transform = CGAffineTransform(scaleX: 0.7, y: 0.7)
        
        UIView.animate(withDuration: 2.0, delay: 0.0, usingSpringWithDamping: 0.2, initialSpringVelocity: 20.0, options: .allowUserInteraction, animations: { [] in
            backgroundView.isHidden = false
            backgroundView.transform = CGAffineTransform(scaleX: 1.0, y: 1.0)
        },
        completion: nil)
    }
}
