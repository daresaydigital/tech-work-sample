//
//  ActivityIndicator.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 09/03/2021.
//

import UIKit

fileprivate var containerView : UIView!
fileprivate var alertView : UIView!
fileprivate var indicatorView : UIView!

extension UIViewController {
    func showActivityIndicator() {
        containerView = UIView(frame: view.bounds)
        alertView = UIView(frame: .zero)
        indicatorView = UIView(frame: .zero)
        
        containerView.backgroundColor = .black
        alertView.backgroundColor = .white
        
        containerView.alpha = 0
        alertView.alpha = 0
        indicatorView.alpha = 0
        
        UIView.animate(withDuration: 0.5) {
            containerView.alpha = 0.5
            alertView.alpha = 1
            indicatorView.alpha = 1
        }
        view.addSubview(containerView)
        NSLayoutConstraint.activate([
            containerView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            containerView.centerXAnchor.constraint(equalTo: view.centerXAnchor)
        ])
        
        ViewProperties.configureViewWithRoundBorderAndShadow(backgroundView: alertView)
        view.addSubview(alertView)
        alertView.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            alertView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            alertView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            alertView.widthAnchor.constraint(equalToConstant: 80),
            alertView.heightAnchor.constraint(equalToConstant: 80)
        ])

        let activityIndicator = UIImage(named: "activity_indicator")
        let imageView = UIImageView(image: activityIndicator)
        view.addSubview(indicatorView)
        indicatorView.addSubview(imageView)
        imageView.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            imageView.topAnchor.constraint(equalTo: indicatorView.topAnchor, constant: 0),
            imageView.leadingAnchor.constraint(equalTo: indicatorView.leadingAnchor, constant: 0),
            imageView.trailingAnchor.constraint(equalTo: indicatorView.trailingAnchor, constant: 0),
            imageView.bottomAnchor.constraint(equalTo: indicatorView.bottomAnchor, constant: 0)
        ])
        
        
        indicatorView.translatesAutoresizingMaskIntoConstraints = false
        ViewProperties.animateViewInfinitely(backgroundView: indicatorView)
        NSLayoutConstraint.activate([
            indicatorView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            indicatorView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            indicatorView.widthAnchor.constraint(equalToConstant: 50),
            indicatorView.heightAnchor.constraint(equalToConstant: 50)
        ])
    }
    
    func hideActivityIndicator() {
        DispatchQueue.main.async {
            containerView?.removeFromSuperview()
            alertView?.removeFromSuperview()
            indicatorView?.removeFromSuperview()

            containerView = nil
            alertView = nil
            indicatorView = nil
        }
    }
}
