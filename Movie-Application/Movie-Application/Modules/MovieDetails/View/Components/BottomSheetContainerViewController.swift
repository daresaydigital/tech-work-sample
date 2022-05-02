//
//  BottomSheetContainerViewController.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import UIKit

open class BottomSheetContainerViewController<Content: UIViewController, BottomSheet: UIViewController> : UIViewController, UIGestureRecognizerDelegate {
    
    // MARK: - Initialization
    public init(contentViewController: Content,
                bottomSheetViewController: BottomSheet,
                bottomSheetConfiguration: BottomSheetConfiguration) {
        
        self.contentViewController = contentViewController
        self.bottomSheetViewController = bottomSheetViewController
        self.configuration = bottomSheetConfiguration
        
        super.init(nibName: nil, bundle: nil)
        
        self.setupUI()
    }
    
    required public init?(coder: NSCoder) {
        fatalError("init(coder:) is not supported")
    }
    
    // MARK: - Bottom Sheet Actions
    public func showBottomSheet(animated: Bool = true) {
        self.topConstraint.constant = -configuration.height
        
        if animated {
            UIView.animate(withDuration: 0.2, animations: {
                self.view.layoutIfNeeded()
            }, completion: { _ in
                self.state = .full
            })
        } else {
            self.view.layoutIfNeeded()
            self.state = .full
        }
    }
    
    public func hideBottomSheet(animated: Bool = true) {
        self.topConstraint.constant = -configuration.initialOffset
        
        if animated {
            UIView.animate(withDuration: 0.3,
                           delay: 0,
                           usingSpringWithDamping: 0.8,
                           initialSpringVelocity: 0.5,
                           options: [.curveEaseOut],
                           animations: {
                self.view.layoutIfNeeded()
            }, completion: { _ in
                self.state = .initial
            })
        } else {
            self.view.layoutIfNeeded()
            self.state = .initial
        }
    }
    
    // MARK: - Pan Action
    @objc func handlePan(_ sender: UIPanGestureRecognizer) {
        let translation = sender.translation(in: bottomSheetViewController.view)
        let velocity = sender.velocity(in: bottomSheetViewController.view)
        
        let yTranslationMagnitude = translation.y.magnitude
        
        switch sender.state {
        case .began, .changed:
            if self.state == .full {
                // Assert that the user scrolls down ward. The bottom sheet is already at its full height; no upward scrolling is allowed
                guard translation.y > 0 else { return }
                
                // Change the topConstraint’s constant to match the current position of the user’s finger
                topConstraint.constant = -(configuration.height - yTranslationMagnitude)
                
                // Update the root view to show the constraint’s change
                self.view.layoutIfNeeded()
                
            } else {
                
                // Calculate the new constant by using the BottomSheetConfiguration’s initialOffset and the translation magnitude
                let newConstant = -(configuration.initialOffset + yTranslationMagnitude)
                
                // down ward scrollin is allowed
                guard translation.y < 0 else { return }
                guard newConstant.magnitude < configuration.height else {
                    self.showBottomSheet()
                    return
                }
                
                topConstraint.constant = newConstant
                
                self.view.layoutIfNeeded()
            }
        case .ended:
            if self.state == .full {
                
                if velocity.y < 0 {
                    // Bottom Sheet was full initially and the user tried to move it to the top
                    self.showBottomSheet()
                } else if yTranslationMagnitude >= configuration.height / 2 || velocity.y > 1000 {
                    self.hideBottomSheet()
                } else {
                    self.showBottomSheet()
                }
            } else {
                
                if yTranslationMagnitude >= configuration.height / 2 || velocity.y < -1000 {
                    
                    self.showBottomSheet()
                    
                } else {
                    self.hideBottomSheet()
                }
            }
        case .failed:
            if self.state == .full {
                self.showBottomSheet()
            } else {
                self.hideBottomSheet()
            }
        default: break
        }
    }
    // MARK: - Tap Gesture Handler
    @objc func contentViewTapped() {
        self.hideBottomSheet()
    }
    
    // MARK: - Configuration
    public struct BottomSheetConfiguration {
        let height: CGFloat
        let initialOffset: CGFloat
    }
    
    private let configuration: BottomSheetConfiguration
    
    // MARK: - State
    public enum BottomSheetState {
        case initial
        case full
    }
    
    var state: BottomSheetState = .initial
    
    // MARK: - Children
    let contentViewController: Content
    let bottomSheetViewController: BottomSheet
    
    // MARK: - Top Constraint
    private var topConstraint = NSLayoutConstraint()
    
    // MARK: - Pan Gesture
    lazy var panGesture: UIPanGestureRecognizer = {
        let pan = UIPanGestureRecognizer()
        pan.delegate = self
        pan.addTarget(self, action: #selector(handlePan))
        return pan
    }()
    
    // MARK: - Tap Gesture
    
    lazy var tapGesture: UITapGestureRecognizer = {
        let tap = UITapGestureRecognizer()
        tap.addTarget(self, action: #selector(contentViewTapped))
        tap.delegate = self
        return tap
    }()
    
    // MARK: - UI Setup
    private func setupUI() {
        self.addChild(contentViewController)
        self.addChild(bottomSheetViewController)
        
        self.view.addSubview(contentViewController.view)
        self.view.addSubview(bottomSheetViewController.view)
        bottomSheetViewController.view.addGestureRecognizer(panGesture)
        contentViewController.view.addGestureRecognizer(tapGesture)
        
        contentViewController.view.translatesAutoresizingMaskIntoConstraints = false
        bottomSheetViewController.view.translatesAutoresizingMaskIntoConstraints = false
        
        NSLayoutConstraint.activate([
            contentViewController.view.leftAnchor
                .constraint(equalTo: self.view.leftAnchor),
            contentViewController.view.rightAnchor
                .constraint(equalTo: self.view.rightAnchor),
            contentViewController.view.topAnchor
                .constraint(equalTo: self.view.topAnchor),
            contentViewController.view.bottomAnchor
                .constraint(equalTo: self.view.bottomAnchor)
        ])
        
        contentViewController.didMove(toParent: self)
        
        topConstraint = bottomSheetViewController.view.topAnchor
            .constraint(equalTo: self.view.bottomAnchor,
                        constant: -configuration.initialOffset)
        
        NSLayoutConstraint.activate([
            bottomSheetViewController.view.heightAnchor
                .constraint(equalToConstant: configuration.height),
            bottomSheetViewController.view.leftAnchor
                .constraint(equalTo: self.view.leftAnchor),
            bottomSheetViewController.view.rightAnchor
                .constraint(equalTo: self.view.rightAnchor),
            topConstraint
        ])
        
        bottomSheetViewController.didMove(toParent: self)
    }
    
    // MARK: - UIGestureRecognizer Delegate
    public func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldRecognizeSimultaneouslyWith otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return true
    }
}
