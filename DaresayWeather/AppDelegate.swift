//
//  AppDelegate.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 20/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import UIKit
import IQKeyboardManagerSwift
@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    var selectedAppTheme = apptheme.lightMode
    var arrCities = [[String : String]]()
    let arrCitiesKey = "arrCities"
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        
        IQKeyboardManager.sharedManager().enable = true
        selectedAppTheme = .lightMode// 6..<21 ~= Date().hour ? apptheme.lightMode : apptheme.darkMode
        let defaults = UserDefaults.standard
        if let arr = defaults.object(forKey: arrCitiesKey) as? [[String:String]]{
            arrCities = arr
        }
        else {
            self.addCityJson {
            
            }
        }
        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
    func addCityJson(completion: @escaping () -> Void){
        
        
        if let path = Bundle.main.path(forResource: Constants.cityJsonFile, ofType: "json") {
            do {
                let data = try Data(contentsOf: URL(fileURLWithPath: path), options: .mappedIfSafe)
                let decoder = JSONDecoder()
                let model = try decoder.decode(citiesModel.self, from: data)
                print(model)
                guard let cities = model.cities else {
                    completion()
                    return
                }
                
                arrCities = cities.map({ (cityModel) -> [String: String] in
                    let dict = ["cityName": (cityModel.name ?? ""), "cityID": String(cityModel.id ?? 0)]
                    
                    return dict
                    
                })
                
                
                let defaults = UserDefaults.standard
                defaults.set(arrCities, forKey: arrCitiesKey)
                completion()
                
            } catch {
                // handle error
                completion()
                
                print("Error!! Unable to load  \(Constants.cityJsonFile).json")
                
            }
        }
        else{
            print("Error!! Unable to load  \(Constants.cityJsonFile).json")
            completion()
            
        }
    }

}

