//
//  ForecastDailyCollectionView.swift
//  Weather
//
//  Created by Christian  Huang on 11/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit

class ForecastDailyCollectionView: UICollectionView {

    var forecastDailyListViewModel = ForecastDailyListViewModel()
    
    override func awakeFromNib() {
        super.awakeFromNib()
        self.register(UINib(nibName: "ForecastCell", bundle: nil), forCellWithReuseIdentifier: "forecastCellIdentifier")
    }
    
}

//MARK:- ViewModel related
extension ForecastDailyCollectionView {
    func initViewModel() {
        self.dataSource = self
        forecastDailyListViewModel.forecastDailyList.listener = { forecastList in
            DispatchQueue.main.async {
                self.reloadData()
            }
        }
    }
}

//MARK:- UICollectionViewDataSource related
extension ForecastDailyCollectionView: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return forecastDailyListViewModel.forecastDailyCount
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "forecastCellIdentifier", for: indexPath) as! ForecastCell
        let forecastDailyViewModel = forecastDailyListViewModel.getForecastDailyViewModel(index: indexPath.item)
        cell.configureCell(forecastDailyViewModel: forecastDailyViewModel, isNow: indexPath.item == 0)
        return cell
    }
}

//MARK:- UICollectionViewDelegate related
extension ForecastDailyCollectionView: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        forecastDailyListViewModel.userSelectedForecast(indexPath.item)
    }
}
