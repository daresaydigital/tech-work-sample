//
//  PopularResponseModel.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import Foundation

class PopularResponseModel: Decodable {
    let page: Int
    let results: [MovieInfoModel]
    let totalPages: Int
    let totalResults: Int
}
