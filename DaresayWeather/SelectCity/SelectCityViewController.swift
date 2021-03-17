//
//  SelectCityViewController.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 23/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import UIKit

protocol SelectCityVCDelegate: NSObjectProtocol {
    func didSelectCity(strCityID : String)-> Void
}

class SelectCityViewController: UIViewController {
    
    weak var delegate : SelectCityVCDelegate?
    @IBOutlet weak var imgBackground : UIImageView!
    @IBOutlet weak var tblView : UITableView!
    @IBOutlet weak var btnClose : UIButton!
    @IBOutlet weak var txtSearch : UITextField!
    @IBOutlet weak var bgVisualEffect: UIVisualEffectView!
    var arrCities = [[String : String]]()
    var arrFilteredCities = [[String : String]]()
    let arrCitiesKey = "arrCities"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupVC()
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
}
// MARK: - TextFieldDelegates

extension SelectCityViewController : UITextFieldDelegate{
    @objc func didEditText(_ textField: UITextField) {
        let strTxtField = textField.text
        arrFilteredCities = arrCities.filter {  (cityModel) -> Bool in
            
            let strCityName = cityModel["cityName"] ?? ""
            if strCityName.containsIgnoringCase(find: strTxtField ?? ""){
                return true
            }
            return false
        }
        
        tblView.reloadData()
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    func textFieldDidBeginEditing(_ textField: UITextField) {
        self.txtSearch.text = ""
    }
}
// MARK: - Custom Functions

extension SelectCityViewController {
    
    @objc func dismissVC(){
        self.dismiss(animated: true) {
            
        }
    }
    func setupVC(){
        tblView.delegate = self
        tblView.dataSource = self
        txtSearch.addTarget(self, action: #selector(didEditText(_:)), for: .editingChanged)
        btnClose.addTarget(self, action: #selector(self.dismissVC), for: .touchUpInside)
        self.imgBackground.image = Utility.changeBackgroundBasedOnTheme()
        bgVisualEffect.effect = Utility.changeBlurEffectBasedOnTheme()
        let appdel = UIApplication.shared.delegate as! AppDelegate
        
        arrCities = appdel.arrCities

        tblView.reloadData()
        
    }
}

// MARK: - TableView Datasource and Delegates

extension SelectCityViewController : UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return arrFilteredCities.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: SelectCityTableViewCellEnum.SelectCityTableViewCell.rawValue, for: indexPath) as! SelectCityTableViewCell
        
        let filteredCity = arrFilteredCities[indexPath.row]
        cell.lblCity.text = filteredCity["cityName"]
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let filteredCity = arrFilteredCities[indexPath.row]
        let cityID = filteredCity["cityID"]
        self.dismiss(animated: true) {
           self.delegate?.didSelectCity(strCityID: cityID ?? "")

        }
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 0.09510869565 * self.view.frame.height
    }
}

enum SelectCityTableViewCellEnum : String {
    
   case  SelectCityTableViewCell = "SelectCityTableViewCell"
}
