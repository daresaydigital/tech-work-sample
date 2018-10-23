//
//  AFNetwork.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 20/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import UIKit
import Alamofire

//main class
public class AFNetwork: NSObject {
    
    //MARK: constant and variable
    //manager
    public var alamoFireManager: Alamofire.SessionManager!
    public var failureMessage = "Unable to connect to the internet"
    
    //network
    public var baseURL = Constants.baseURL//DEFAULT_CONFIG.baseUrl
    public var commonHeaders: Dictionary<String, String> = [
        "Authorization" : "",
        "Accept" : "application/json"
    ]
    
    //spinner
    struct spinnerViewConfig {
        static let tag: Int = 98272
        static let color = UIColor.white
    }
    
    //progress view
    public var progressLabel: UILabel?
    var progressView: UIProgressView?
    struct progressViewConfig {
        static let tag: Int = 98273
        static let color = UIColor.white
        static let labelColor = UIColor.red
        static let trackTintColor = UIColor.red
        static let progressTintColor = UIColor.green
    }
    
    //shared Instance
    public static let shared: AFNetwork = {
        let instance = AFNetwork()
        return instance
    }()
    
    // MARK: - : override
    override init() {
        alamoFireManager = Alamofire.SessionManager(
            configuration: URLSessionConfiguration.default
        )
        alamoFireManager.session.configuration.timeoutIntervalForRequest = 120
    }
    
   
}

// MARK: - Request
extension AFNetwork {
    
    //general request
    public func apiRequest(_ info: AFParam, isSpinnerNeeded: Bool, success:@escaping (Data?) -> Void, failure:@escaping (Error) -> Void) {
        
        //if spinner needed
        if isSpinnerNeeded {
            DispatchQueue.main.async {
                AFNetwork.shared.showSpinner(nil)
            }
        }
        //URLEncoding(destination: .methodDependent)
        //request
        alamoFireManager.request(self.baseURL + info.endpoint, method: info.method, parameters: info.params, encoding: info.parameterEncoding, headers: mergeWithCommonHeaders(info.headers)).responseJSON { (response) -> Void in
            
            
            //remove spinner
            if isSpinnerNeeded {
                DispatchQueue.main.async {
                    AFNetwork.shared.hideSpinner()
                }
            }
            
            //check response result case
            switch response.result {
            case .success:
                debugPrint(response)
                success(response.data)
            case .failure:
                let error : Error = response.result.error!
                debugPrint("responseError: \(error)")
                Utility.showMsg(msg: error.localizedDescription)
               
                failure(error)
            }
        }
    }
    
    //file upload
    func apiRequestUpload(_ info: AFParam, isSpinnerNeeded: Bool, success:@escaping (NSDictionary?) -> Void, failure:@escaping (Error) -> Void) {
        
        //if spinner needed
        if isSpinnerNeeded {
            DispatchQueue.main.async {
                AFNetwork.shared.showSpinner(nil)
            }
        }
        
        let URL = try! URLRequest(url: self.baseURL + info.endpoint, method: info.method, headers: mergeWithCommonHeaders(info.headers))
        
        alamoFireManager.upload(multipartFormData: { (multipartFormData) in
            
            //multipart params
            if info.params != nil {
                
                for (key, value) in info.params! {
                    if let data = value.data(using: String.Encoding.utf8.rawValue) {
                        multipartFormData.append(data, withName: key)
                    }
                }
            }
            
            //multipart images
            if info.images != nil {
                if info.images!.count > 0 {
                    for value in info.images! {
                        let imageData = UIImagePNGRepresentation(value) as Data?
                        if imageData != nil {
                            multipartFormData.append(imageData!, withName: "image[]")
                        }
                    }
                }
            }
            
        }, with: URL, encodingCompletion: { (result) in
            
            //remove spinner
            if isSpinnerNeeded {
                DispatchQueue.main.async {
                    AFNetwork.shared.hideSpinner()
                }
            }
            
            switch result {
            case .success(let upload, _, _):
                upload
                    .uploadProgress { progress in
                        
                        //set progress view
                        self.setProgressProgress(Float(progress.fractionCompleted))
                        
                    }
                    .validate()
                    .responseJSON { response in
                        
                        //set progress view
                        self.setProgressProgress(1.0)
                        
                        switch response.result {
                        case .success(let value):
                            
                            debugPrint(value)
                            
                            if let result = response.result.value {
                                success(result as? NSDictionary)
                            }
                            else {
                                success(nil)
                            }
                        case .failure(let responseError):
                            
                            debugPrint("responseError: \(responseError)")
                            //Alert.showMsg(msg: responseError.localizedDescription)
                            failure(responseError)
                        }
                }
            case .failure(let encodingError):
                failure(encodingError)
            }
        })
    }
}

// MARK: - Progress and spinner methods
extension AFNetwork {
    
    public func showProgressView(_ customView: UIView?) {
        
        var window = customView
        
        if (window == nil) {
            window = returnTopWindow()
        }
        if window?.viewWithTag(progressViewConfig.tag) != nil {
            return
        }
        
        let backgroundView = UIView(frame: CGRect.zero)
        backgroundView.tag = progressViewConfig.tag
        backgroundView.translatesAutoresizingMaskIntoConstraints = false
        backgroundView.backgroundColor = UIColor.clear.withAlphaComponent(0.7)
        
        let progressContainer = UIView()
        progressContainer.translatesAutoresizingMaskIntoConstraints = false
        progressContainer.backgroundColor = UIColor.clear
        
        progressLabel = UILabel()
        progressLabel?.translatesAutoresizingMaskIntoConstraints = false
        progressLabel?.textColor = progressViewConfig.labelColor
        progressLabel?.text = "Upload progress 0%"
        progressLabel?.font = UIFont.systemFont(ofSize: 16.0, weight: UIFont.Weight.bold)
        progressLabel?.adjustsFontSizeToFitWidth = true
        progressLabel?.textAlignment = .center
        progressContainer.addSubview(progressLabel!)
        
        progressView = UIProgressView(progressViewStyle: .default)
        progressView?.translatesAutoresizingMaskIntoConstraints = false
        progressView?.progressTintColor = progressViewConfig.progressTintColor
        progressView?.trackTintColor = progressViewConfig.trackTintColor
        progressView?.progress = 0.0
        progressContainer.addSubview(progressView!)
        
        progressContainer.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|[progressView]-(10)-[progressLabel]|", options: [], metrics: nil, views: ["progressLabel" : progressLabel!, "progressView" : progressView!]))
        progressContainer.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|[progressView(200)]|", options: [], metrics: nil, views: ["progressView" : progressView!]))
        progressContainer.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|[progressLabel]|", options: [], metrics: nil, views: ["progressLabel" : progressLabel!]))
        backgroundView.addSubview(progressContainer)
        
        backgroundView.addConstraint(NSLayoutConstraint(item: backgroundView, attribute: .centerY, relatedBy: .equal, toItem: progressContainer, attribute: .centerY, multiplier: 1.0, constant: 0.0))
        backgroundView.addConstraint(NSLayoutConstraint(item: backgroundView, attribute: .centerX, relatedBy: .equal, toItem: progressContainer, attribute: .centerX, multiplier: 1.0, constant: 0.0))
        
        window?.addSubview(backgroundView)
        window?.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|[backgroundView]|", options: [], metrics: nil, views: ["backgroundView" : backgroundView]))
        window?.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|[backgroundView]|", options: [], metrics: nil, views: ["backgroundView" : backgroundView]))
    }
    
    //hide progress view
    public func hideProgressView() {
        
        let window: UIWindow? = returnTopWindow()
        window?.viewWithTag(progressViewConfig.tag)?.removeFromSuperview()
        progressLabel = nil
        progressView = nil
    }
    
    //show spinner
    public func showSpinner(_ customView: UIView?) {
        
        var window = customView
        
        if (window == nil) {
            window = returnTopWindow()
        }
        if ((window?.viewWithTag(spinnerViewConfig.tag)) != nil) {
            return
        }
        
        //background view
        let backgroundView = UIView(frame: CGRect.zero)
        backgroundView.tag = spinnerViewConfig.tag
        backgroundView.translatesAutoresizingMaskIntoConstraints = false
        backgroundView.backgroundColor = UIColor.clear.withAlphaComponent(0.5)
        window?.addSubview(backgroundView)
        window?.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|[backgroundView]|", options: [], metrics: nil, views: ["backgroundView" : backgroundView]))
        window?.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|[backgroundView]|", options: [], metrics: nil, views: ["backgroundView" : backgroundView]))
        
        //spinner
        let activityIndicator = UIActivityIndicatorView(activityIndicatorStyle: .whiteLarge)
        activityIndicator.color = spinnerViewConfig.color
        activityIndicator.translatesAutoresizingMaskIntoConstraints = false
        activityIndicator.startAnimating()
        backgroundView.addSubview(activityIndicator)
        backgroundView.addConstraint(NSLayoutConstraint(item: backgroundView, attribute: .centerX, relatedBy: .equal, toItem: activityIndicator, attribute: .centerX, multiplier: 1.0, constant: 0.0))
        backgroundView.addConstraint(NSLayoutConstraint(item: backgroundView, attribute: .centerY, relatedBy: .equal, toItem: activityIndicator, attribute: .centerY, multiplier: 1.0, constant: 0.0))
    }
    
    //hide spinner
    public func hideSpinner() {
        
        let window: UIWindow? = returnTopWindow()
        window?.viewWithTag(spinnerViewConfig.tag)?.removeFromSuperview()
    }
}

// MARK: - Helper methods
extension AFNetwork {
    
    //set progress and text of progress view
    func setProgressProgress(_ fractionCompleted:Float) {
        
        self.progressView?.progress = Float(fractionCompleted)
        self.progressLabel?.text = String(format: "Uploading Image %.0f%%", fractionCompleted * 100)
    }
    
    //return top window
    func returnTopWindow() -> UIWindow {
        
        let windows: [UIWindow] = UIApplication.shared.windows
        
        for topWindow: UIWindow in windows {
            if topWindow.windowLevel == UIWindowLevelNormal {
                return topWindow
            }
        }
        return UIApplication.shared.keyWindow!
    }
    
    //return merge headers
    func mergeWithCommonHeaders(_ headers: [String : String]?) -> Dictionary<String, String> {
        
        if headers != nil {
            for header in headers! {
                AFNetwork.shared.commonHeaders[header.key] = header.value
            }
        }
        return AFNetwork.shared.commonHeaders
    }
}


