//
//  MovieDetailsViewModel.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import Foundation

class MovieDetailsViewData {
    var movieInfoModel: MovieInfoModel
    
    init(_ movieInfoModel: MovieInfoModel) {
        self.movieInfoModel = movieInfoModel
    }
}

class MovieDetailsViewModel {
    weak var viewController: MovieDetailsViewController?
    private var dataModel: MovieDetailsViewData
    
    init(_ movieInfoModel: MovieInfoModel) {
        dataModel = MovieDetailsViewData(movieInfoModel)
    }
    
    func movieInfoModel() -> MovieInfoModel {
        return dataModel.movieInfoModel
    }
    
    func movieTitle() -> String {
        return dataModel.movieInfoModel.title
    }
}
