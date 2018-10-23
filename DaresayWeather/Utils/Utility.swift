//
//  Utility.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 21/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import Foundation
import UIKit
import Alamofire
class Connectivity {
    static var isConnectedToInternet:Bool {
        return NetworkReachabilityManager()!.isReachable
    }
}
public class Utility : NSObject {
    static func createNibForCollection(cellIdentifier : String, nibName : String , colView : UICollectionView){
        let nib = UINib(nibName : nibName , bundle : nil)
        colView.register(nib, forCellWithReuseIdentifier: cellIdentifier)
    }
    
    static func createNibForTable(cellIdentifier : String, nibName : String , tblView : UITableView){
        let nib = UINib(nibName : nibName , bundle : nil)
        tblView.register(nib, forCellReuseIdentifier: cellIdentifier)
    }
    
    
    static func getDayNameBy(stringDate: String) -> String
    {
        let df  = DateFormatter()
        df.dateFormat = "YYYY-MM-dd HH:mm:ss"
        let date = df.date(from: stringDate)!
        df.dateFormat = "EEEE"
        return df.string(from: date);
    }
    
    static func getDate(dateStr : String) -> (String, String, Date) {
        let inputDateFormatter = DateFormatter()
        inputDateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        inputDateFormatter.timeZone = NSTimeZone(name: "UTC")! as TimeZone
        if let inputDate = inputDateFormatter.date(from: dateStr)
        {
            
            
            let outputDateFormatter = DateFormatter()
            outputDateFormatter.dateFormat = "dd-MM-yyyy"
            outputDateFormatter.timeZone = NSTimeZone(name: "UTC")! as TimeZone
            inputDateFormatter.dateFormat = "h:mm a"
            inputDateFormatter.timeZone = NSTimeZone.local
            let timeStamp = inputDateFormatter.string(from: inputDate)
            let otDate = outputDateFormatter.string(from: inputDate)
            let outputDate = outputDateFormatter.date(from: otDate)
            return  (otDate , timeStamp, outputDate!)
        }
        return ("", "", Date())
    }
    
   static func getHourFromDate(strDate : String)-> String{
        let dateAsString = strDate
        let dateFormatter = DateFormatter()
        dateFormatter.locale = Locale.current
        dateFormatter.dateFormat = "YYYY-MM-DD HH:mm:ss"
    if  let date = dateFormatter.date(from: dateAsString) {
        
        dateFormatter.dateFormat = "hh a"
        dateFormatter.amSymbol = "AM"
        dateFormatter.pmSymbol = "PM"
        return dateFormatter.string(from: date)
    }
    return "N/A"
    
    }
    static func getDayFromDate(strDate : String)-> String{
        let dateAsString = strDate
        let dateFormatter = DateFormatter()
        dateFormatter.locale = Locale.current
        dateFormatter.dateFormat = "YYYY-MM-DD HH:mm:ss"
        if  let date = dateFormatter.date(from: dateAsString) {
            
            dateFormatter.dateFormat = "EEEE"
            return dateFormatter.string(from: date)
        }
        return "N/A"
        
    }
    static func changeLabelColorBasedOnTheme(lbl : UILabel){
       lbl.textColor = returnColorBasedOnTheme()
    }
    static func changeTextFieldColorBasedOnTheme(txtField : UITextField){
        txtField.textColor = returnColorBasedOnTheme()
    }
    
    private static func returnColorBasedOnTheme()-> UIColor {
        let appDel = UIApplication.shared.delegate as! AppDelegate
        if appDel.selectedAppTheme == .darkMode {
            return .white
        }
        else {
            return .white
        }
    }
    
    static func changeBackgroundBasedOnTheme()-> UIImage {
        let appDel = UIApplication.shared.delegate as! AppDelegate
        if appDel.selectedAppTheme == .darkMode {
            return UIImage(named:"nightSky2")!
           

        }
        else {
            return  UIImage(named:"morningNew")!
        }
    }
    
    static func getCurrentTheme() -> apptheme {
        let appDel = UIApplication.shared.delegate as! AppDelegate
        return appDel.selectedAppTheme
        
    }
    static func changeBlurEffectBasedOnTheme()-> UIBlurEffect {
        let appDel = UIApplication.shared.delegate as! AppDelegate
        if appDel.selectedAppTheme == .darkMode {
            return UIBlurEffect(style: .dark)
            
            
        }
        else {
            return  UIBlurEffect(style: .light)
        }
        
    }
    
    private static func showOnWindow(_ alert : UIAlertController) {
            if var topController = UIApplication.shared.keyWindow?.rootViewController {
                while let presentedViewController = topController.presentedViewController {
                    topController = presentedViewController
                }
                topController.present(alert, animated: true, completion: nil)
                // topController should now be your topmost view controller
            }
        
    }
    
    static func showMsg(msg : String)-> Void {
        let alertController = UIAlertController(title: "", message: msg, preferredStyle: .alert)
        let alertAction = UIAlertAction(title: "Return", style: .cancel) { (action) in
            
        }
        alertController .addAction(alertAction)

        showOnWindow(alertController)

    }
    static func showMsg(title : String = "Notification", msg : String , btnActionTitle : String? = "Okay", completionAction: @escaping () -> Void ,btnActionCancel : String? = "Cancel", parentViewController:UIViewController? ) -> Void{
        
        
        let alertController = UIAlertController(title: title, message: msg, preferredStyle: .alert)
        let alertAction = UIAlertAction(title: btnActionTitle, style: .default) { (action) in
            
            completionAction()
        }
        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel) { (action) in
            
        }
        alertController .addAction(alertAction)
        alertController .addAction(cancelAction)
        parentViewController?.present(alertController, animated: true, completion: nil)
    }
    
    static func getDateFromTimeStamp(timeStamp : Double, timeZone : TimeZone) -> Date? {
        
        let timeStampDate = Date(timeIntervalSince1970: timeStamp)
        return convertDateToTime(currentDate: timeStampDate, currenttimeZone: timeZone)
       // return dateString
    }
    
    static func convertDateToTime(currentDate : Date, currenttimeZone : TimeZone)-> Date?{
        let dayTimePeriodFormatter = DateFormatter()
        dayTimePeriodFormatter.dateFormat = "HH:mm:ss"
        let timeZone = currenttimeZone
            dayTimePeriodFormatter.timeZone = timeZone
        // UnComment below to get only time
        //  dayTimePeriodFormatter.dateFormat = "hh:mm a"
        let dateString = dayTimePeriodFormatter.string(from: currentDate as Date)
        return dayTimePeriodFormatter.date(from: dateString)
    }
    
    static func checkDayOrNight(strSunsetTime : String , strSunriseTime : String, currentTimeZone : TimeZone) {
        let appDel = UIApplication.shared.delegate as! AppDelegate
        let sunsetTime = getDateFromTimeStamp(timeStamp: Double(strSunsetTime) ?? 0 , timeZone: currentTimeZone)
        let sunriseTime = getDateFromTimeStamp(timeStamp: Double(strSunriseTime) ?? 0, timeZone: currentTimeZone)
        
        if let currentTime = convertDateToTime(currentDate: Date(), currenttimeZone: currentTimeZone ){
            
            if currentTime < sunsetTime! && currentTime > sunriseTime! {
                appDel.selectedAppTheme = .lightMode
            }
            else{
                appDel.selectedAppTheme = .darkMode

            }
        }
        
        
        
        

    
    }
    static func getIconFromCode(strImageCode : String)-> UIImage {
        switch strImageCode {
        case "01d":
            return #imageLiteral(resourceName: "sun")
        case "01n":
            return #imageLiteral(resourceName: "night")
        case "02d":
            return #imageLiteral(resourceName: "cloud-1")
        case "02n":
            return #imageLiteral(resourceName: "cloud-1")
        case "03d":
            return #imageLiteral(resourceName: "cloud-1")
        case "03n":
            return #imageLiteral(resourceName: "cloud-1")
        case "04d":
            return #imageLiteral(resourceName: "cloud-1")
        case "04n":
            return #imageLiteral(resourceName: "cloud-1")
        case "9d":
            return #imageLiteral(resourceName: "rain")
        case "9n":
            return #imageLiteral(resourceName: "rain")
        case "10d":
            return #imageLiteral(resourceName: "rain")
        case "10n":
            return #imageLiteral(resourceName: "rain")
        case "11d":
            return #imageLiteral(resourceName: "storm")
        case "11n":
            return #imageLiteral(resourceName: "storm")
        case "13d":
            return #imageLiteral(resourceName: "snow")
        case "13n":
            return #imageLiteral(resourceName: "snow")
        case "50d":
            return #imageLiteral(resourceName: "haze")
        case "50n":
            return #imageLiteral(resourceName: "haze")
        default:
            return #imageLiteral(resourceName: "cloud-1")
        }
    }

}
extension Date {
    
    var hour: Int { return Calendar.current.component(.hour, from: self) } // get hour only from Date
    
    
    func dayOfWeek() -> String? {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "EEEE, MMM dd"
        return dateFormatter.string(from: self).capitalized
        // or use capitalized(with: locale) if you want
    }
    
    
}
extension String {
    func contains(find: String) -> Bool{
        return self.range(of: find) != nil
    }
    func containsIgnoringCase(find: String) -> Bool{
        return self.range(of: find, options: .caseInsensitive) != nil
    }
}

extension UIView {
    
    func dropShadow() {
        
        self.layer.masksToBounds = false
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.shadowOpacity = 0.5
        self.layer.shadowOffset = CGSize(width: -1, height: 1)
        self.layer.shadowRadius = 1
        
        self.layer.shadowPath = UIBezierPath(rect: self.bounds).cgPath
        self.layer.shouldRasterize = true
        
        self.layer.rasterizationScale = UIScreen.main.scale
        
    }
}
