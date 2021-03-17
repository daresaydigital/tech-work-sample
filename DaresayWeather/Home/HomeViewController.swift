//
//  HomeViewController.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 20/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import UIKit
import CoreLocation
import TimeZoneLocate

class HomeViewController: UIViewController {

    
    @IBOutlet weak var bgVisualEffect: UIVisualEffectView!
    @IBOutlet weak var headerHeightConstraint: NSLayoutConstraint!
    @IBOutlet var imgBackground : UIImageView!
    @IBOutlet var lblCityName : baseUiLabel!
    @IBOutlet var lblDay : baseUiLabel!
    @IBOutlet var lblTemp : baseUiLabel!
    @IBOutlet var btnAddCity : UIButton!
    @IBOutlet weak var titleTopConstraint: NSLayoutConstraint!
    @IBOutlet var tblView : UITableView!
    @IBOutlet weak var headerView: UIView!
    
    var maxHeaderHeight: CGFloat = 0;
    var minHeaderHeight: CGFloat = 0;
    var previousScrollOffset: CGFloat = 0;
    var arrCities = [[String : String]]()
    var lastContentOffset : CGPoint = CGPoint(x: 0, y: 0)
    var timer = Timer()
    var arrCitiesID = [[String : String]]()
    let locationManager = CLLocationManager()
     let weatherManager = WeatherManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        setupVC() // Basic Setup when view loads
        setupUserCurrentLocation() //Setup location and delegates
        
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        //Resize header to its maximum size whenever view appears
        self.headerHeightConstraint.constant = self.maxHeaderHeight
        updateHeader()


    }
  
}

// MARK: - Select City Delegates and Functions

extension HomeViewController : SelectCityVCDelegate {
    //display SelectCityViewController
    @IBAction func didTapOnAddCityBtn(_ sender: Any) {
        let sb = UIStoryboard(name: Storyboards.Main.rawValue, bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: AppVCs.SelectCityViewController.rawValue) as! SelectCityViewController
        vc.delegate = self
        self.present(vc, animated: true) {
            
        }
    }
    //hit webservice via city id to get weather data for selected city
    func didSelectCity(strCityID: String) {
        self.getWeatherDataViaCityID(cityID: strCityID )

    }
}

// MARK: - Custom Functions for basic setup
extension HomeViewController {
    func setupVC(){
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(applicationDidBecomeActive),
                                               name: .UIApplicationDidBecomeActive,
                                               object: nil)
        tblView.isHidden = true
        tblView.delegate = self
        tblView.dataSource = self
        tblView.estimatedRowHeight = 300
        tblView.rowHeight = UITableViewAutomaticDimension;
        tblView.isScrollEnabled = true
        maxHeaderHeight = 0.2717391304 * self.view.frame.height
        minHeaderHeight = 0.1358695652 * self.view.frame.height
        
        Utility.createNibForTable(cellIdentifier: homeViewControllerCells.HomeForecastTableViewCell.rawValue, nibName: homeViewControllerCells.HomeForecastTableViewCell.rawValue, tblView: tblView)
        Utility.createNibForTable(cellIdentifier: homeViewControllerCells.HomeForecastDaysTableViewCell.rawValue, nibName: homeViewControllerCells.HomeForecastDaysTableViewCell.rawValue, tblView: tblView)
        Utility.createNibForTable(cellIdentifier: homeViewControllerCells.HomeDescriptionTableViewCell.rawValue, nibName: homeViewControllerCells.HomeDescriptionTableViewCell.rawValue, tblView: tblView)
        Utility.createNibForTable(cellIdentifier: homeViewControllerCells.HomeOtherInformationTableViewCell.rawValue, nibName: homeViewControllerCells.HomeOtherInformationTableViewCell.rawValue, tblView: tblView)


        changeTheme()
        
        
        
    }
   

    
   
    
     func changeTheme(){
        
        // check if day or night shift
        self.imgBackground.image = Utility.changeBackgroundBasedOnTheme()
        self.lblTemp.formatMode = Utility.getCurrentTheme()
        self.lblDay.formatMode = Utility.getCurrentTheme()
        self.lblCityName.formatMode = Utility.getCurrentTheme()
        bgVisualEffect.effect = Utility.changeBlurEffectBasedOnTheme()

        
        self.tblView.reloadData()

    }
    
    @objc func applicationDidBecomeActive() {
        // handle event
        
        if CLLocationManager.authorizationStatus() == .denied {
         
        }
        else{
            locationManager.startUpdatingLocation()
        }
    }
  
}


// MARK: - Location Manager Delegates

extension HomeViewController: CLLocationManagerDelegate {
    
    func setupUserCurrentLocation(){
        
        isAuthorizedtoGetUserLocation()
        if CLLocationManager.locationServicesEnabled() {
            locationManager.delegate = self
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.startUpdatingLocation()
            
        }
    }
    func isAuthorizedtoGetUserLocation() {
        
        //if not denied then check if its  authorized or not.
        if CLLocationManager.authorizationStatus() != .denied {
            if CLLocationManager.authorizationStatus() != .authorizedWhenInUse     {
                locationManager.requestWhenInUseAuthorization()
            }
        }
        
    }
    
    //check if user has given access for location while in use
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if status == .authorizedWhenInUse {
            print("User allowed us to access location")
        }
    }
    //check for error if location cannot be found
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("Did location updates is called but failed getting location \(error)")
        
        if CLLocationManager.authorizationStatus() == .denied {
            
        }
        
    }
    
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        //when location will be changed
        if let userLat = locations.last?.coordinate.latitude , let userLon = locations.last?.coordinate.longitude {
            //Get weather data from api
            getWeatherDataViaLocation(lat: String(userLat), lon: String(userLon))
            manager.stopUpdatingLocation()//stop
        }
    }
    
   
}
// MARK: - Weather Api Functions

extension HomeViewController {
    //Weather via city id
    func getWeatherDataViaCityID(cityID: String){
        //Created params
        let requestParam =  self.weatherManager.paramsForWeatherViaCityID(cityID: cityID, apiEndPoint: apiEndPoints.weather)
        //Hit webservice using params
        self.weatherManager.api(requestParam) {
            //if successful response
            
            if self.weatherManager.isSuccess {
                //set basic data which is Temperature, City and Date
                self.setupDataAfterWebservice()
                //for future forecast
                self.getForeCastDataViaCityID(cityID: cityID)
                self.tblView.isHidden = false
                self.tblView.reloadData()

        }

    }
    }
    // Same as above just changed url from weather to forecast
    func getForeCastDataViaCityID(cityID :  String){
        let requestParam =  self.weatherManager.paramsForWeatherViaCityID(cityID: cityID, apiEndPoint: apiEndPoints.forecast)
        self.weatherManager.api(requestParam) {
            if self.weatherManager.isSuccess {
                
                // Display Data
                self.tblView.reloadData()
                
            }
        }
        
    }
    //Weather Via Location
    func getWeatherDataViaLocation(lat: String, lon : String){
        let requestParam =  self.weatherManager.paramsForWeatherViaLocation(latitude: lat, longitude: lon, apiEndPoint: apiEndPoints.weather)
        //Hit webservice using params

        self.weatherManager.api(requestParam) {
            //if is successful
            if self.weatherManager.isSuccess {
                
                //set basic data which is Temperature, City and Date
                    self.setupDataAfterWebservice()
                self.tblView.reloadData()

                //for future forecast

                    self.getForeCastDataViaLocation(lat: lat, lon: lon)
                self.tblView.isHidden = false

            }
        }
        
    }
    // Forecast via location
    //Same as above jjust changed the endpoint from weather to forecast
    func getForeCastDataViaLocation(lat: String, lon : String){
        let requestParam =  self.weatherManager.paramsForWeatherViaLocation(latitude: lat, longitude: lon, apiEndPoint: apiEndPoints.forecast)
        self.weatherManager.api(requestParam) {
            if self.weatherManager.isSuccess {
                
                
                self.tblView.reloadData()
                
            }
        }
        
    }

    //setup initial data from webservice
    func setupDataAfterWebservice(){
        //check if data is not nil
        if let data = self.weatherManager.getWeatherViaLocationData() {
            lblCityName.text = data.name
            lblDay.text = Date().dayOfWeek()!
            lblTemp.text = String(Int(data.main?.temp ?? 0)) + "ºc"
            
            let sunsetTime = String(data.sys?.sunset ?? 0)
            let sunriseTime = String(data.sys?.sunrise ?? 0)
            
            var timeZone = TimeZone.current
            if let coords = data.coord ,let cityLat = coords.lat , let cityLong = coords.lon, let sys = data.sys , let countryCode = sys.country {
                let cityLocation = CLLocation(latitude:CLLocationDegrees(cityLat) , longitude: CLLocationDegrees(cityLong))
                timeZone = TimeZoneLocate.timeZone(location: cityLocation, countryCode: countryCode)!
            
            }
            

            Utility.checkDayOrNight(strSunsetTime: sunsetTime, strSunriseTime: sunriseTime, currentTimeZone: timeZone)

            
            changeTheme()
           
        }
       
    
        

    
    }
 

}

// MARK: - TableView delegate and datasource
extension HomeViewController: UITableViewDelegate , UITableViewDataSource{
    
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        if let forecastData = self.weatherManager.getForcecastDays() {
            
            //Here the first three rows are constants i.e collection cell, description cell and other info cell
            return 3 + forecastData.count
            
        }
        //Here the first three rows are constants i.e collection cell, description cell and other info cell
        //when the webservice with endpoint of weather it will update the first three rows.
        return 3
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        //This cell is for hourly forecast. It contains a Collection View
        if indexPath.row == 0 {
            
            let cell = tableView.dequeueReusableCell(withIdentifier: homeViewControllerCells.HomeForecastTableViewCell.rawValue, for: indexPath) as! HomeForecastTableViewCell
            //safety check
            let forecastData = self.weatherManager.getForecastViaLocation()
            if let arrList = forecastData?.list {
                cell.configCell(data: arrList)
            }
            return cell
        }
        //This cell is for Description of weather.
        else if indexPath.row == 1 {
            let cell = tableView.dequeueReusableCell(withIdentifier: homeViewControllerCells.HomeDescriptionTableViewCell.rawValue, for: indexPath) as! HomeDescriptionTableViewCell
            //safety check
            if let model = self.weatherManager.getWeatherViaLocationData() {
                cell.configCell(mod: model)
            }
            return cell
        }
        //This cell is for Other info of weather. It contains 4 types of info.
        //Humidity , Sea Level , Ground Level, Pressure.
        else if indexPath.row == 2 {
            let cell = tableView.dequeueReusableCell(withIdentifier: homeViewControllerCells.HomeOtherInformationTableViewCell.rawValue, for: indexPath) as! HomeOtherInformationTableViewCell
            //safety check
            if let model = self.weatherManager.getForecastViaLocation() {
                cell.configCell(mod: model)
            }
            return cell
        }
        else{
            //This cell is for future forecast of weather.
            let cell = tableView.dequeueReusableCell(withIdentifier: homeViewControllerCells.HomeForecastDaysTableViewCell.rawValue, for: indexPath) as! HomeForecastDaysTableViewCell
            //safety check
            if let forecastData = self.weatherManager.getForcecastDays() {
                cell.configCell(data: forecastData[indexPath.row - 3])
            }
            
            
            return cell
        }
        
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        //specific height for rows
        if indexPath.row == 0 {
            return 0.1793478261 * self.view.frame.height
        }
        if indexPath.row == 2 {
            return 0.1725543478 * self.view.frame.height
        }
        return 0.09510869565 * self.view.frame.height
    }
    
    
// MARK: - Animatable Header
    
    //When we start scrolling tableview up/down
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        //here we set difference between last state position of scroll with the updated one.
        let scrollDiff = scrollView.contentOffset.y - self.previousScrollOffset
        let absoluteTop: CGFloat = 0;
        //get bottom value of content scroll.
        let absoluteBottom: CGFloat = scrollView.contentSize.height - scrollView.frame.size.height;
        
        let isScrollingDown = scrollDiff > 0 && scrollView.contentOffset.y > absoluteTop
        let isScrollingUp = scrollDiff < 0 && scrollView.contentOffset.y < absoluteBottom
        //check if scroll ended
        if canAnimateHeader(scrollView) {
            
            // Calculate new header height
            var newHeight = self.headerHeightConstraint.constant
            if isScrollingDown {
                newHeight = max(self.minHeaderHeight, self.headerHeightConstraint.constant - abs(scrollDiff))
            } else if isScrollingUp {
                newHeight = min(self.maxHeaderHeight, self.headerHeightConstraint.constant + abs(scrollDiff))
            }
            
            // Header needs to animate
            if newHeight != self.headerHeightConstraint.constant {
                self.headerHeightConstraint.constant = newHeight
                self.updateHeader()
                self.setScrollPosition(self.previousScrollOffset)
            }
            //update this value as last state position of scroll
            self.previousScrollOffset = scrollView.contentOffset.y
        }
    }
    
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        self.scrollViewDidStopScrolling()
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if !decelerate {
            self.scrollViewDidStopScrolling()
        }
    }
    //Update header when scrolling stop
    func scrollViewDidStopScrolling() {
        let range = self.maxHeaderHeight - self.minHeaderHeight
        let midPoint = self.minHeaderHeight + (range / 2)
        
        if self.headerHeightConstraint.constant > midPoint {
            self.expandHeader()
        } else {
            self.collapseHeader()
        }
    }
    
    func canAnimateHeader(_ scrollView: UIScrollView) -> Bool {
        // Calculate the size of the scrollView when header is collapsed
        let scrollViewMaxHeight = scrollView.frame.height + self.headerHeightConstraint.constant - minHeaderHeight
        // Make sure that when header is collapsed, there is still room to scroll
        return scrollView.contentSize.height > scrollViewMaxHeight
    }
    
    //This function makes the header size to its minimum height set by us
    func collapseHeader() {
        self.view.layoutIfNeeded()
        UIView.animate(withDuration: 0.2, animations: {
            self.headerHeightConstraint.constant = self.minHeaderHeight
            self.updateHeader()
            self.view.layoutIfNeeded()
        })
    }
    //This function makes the header size to its max height set by us
    func expandHeader() {
        self.view.layoutIfNeeded()
        UIView.animate(withDuration: 0.2, animations: {
            self.headerHeightConstraint.constant = self.maxHeaderHeight
            self.updateHeader()
            self.view.layoutIfNeeded()
        })
    }
    
    func setScrollPosition(_ position: CGFloat) {
        self.tblView.contentOffset = CGPoint(x: self.tblView.contentOffset.x, y: position)
    }
    //This is called to update the header. The header will resize to only display city Name when scroll upwards
    //Scrolling downward will restore to its max height
    func updateHeader() {
        let range = self.maxHeaderHeight - self.minHeaderHeight
        let openAmount = self.headerHeightConstraint.constant - self.minHeaderHeight
        let percentage = openAmount / range
        //how much to decrease the value
        var topValue = -openAmount + 10
        // as soon as the title goes out of the frame we put it back because the max and min height difference is small
        if topValue < -10 {
            topValue = 10
        }
        self.titleTopConstraint.constant = topValue
        //to provide smooth fading effect
        self.lblTemp.alpha = percentage

    }
}



