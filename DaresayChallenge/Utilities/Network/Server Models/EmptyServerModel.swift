//
//  EmptyServerModel.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import Foundation

/// A model to decode requests without any response.
extension ServerModels {
    
    struct EmptyServerModel: ServerModel { }
    
    struct StringServerModel: ServerModel {
        var bodyString: String?
    }
}
