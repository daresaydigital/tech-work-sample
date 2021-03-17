//
//  HomeForecastTableViewCell.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 21/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import UIKit

class HomeForecastTableViewCell: UITableViewCell {

    
    @IBOutlet var hourlyForecastCollectionView : UICollectionView!
    var arrList : [forecastModelList]?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        hourlyForecastCollectionView.delegate = self
        hourlyForecastCollectionView.dataSource = self
        Utility.createNibForCollection(cellIdentifier: "HourlyForeCastCollectionViewCell", nibName: "HourlyForeCastCollectionViewCell", colView: hourlyForecastCollectionView)
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func configCell(data: [forecastModelList]){
        arrList = data
        hourlyForecastCollectionView.reloadData()
    }
    
}
extension HomeForecastTableViewCell : UICollectionViewDelegate , UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "HourlyForeCastCollectionViewCell", for: indexPath) as! HourlyForeCastCollectionViewCell
        if let foreCastModel = arrList?[indexPath.row]{
        cell.configCell(model: foreCastModel)
        }
        let blurAlpha : CGFloat = 0.1
        if indexPath.row == 0 {
            cell.viusalEffectView.alpha = blurAlpha

        }
        else{
            cell.viusalEffectView.alpha = blurAlpha * CGFloat(indexPath.row)

        }
        return cell
        
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 9
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 95, height: self.frame.height)
    }
    
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 0
    }
  
}

