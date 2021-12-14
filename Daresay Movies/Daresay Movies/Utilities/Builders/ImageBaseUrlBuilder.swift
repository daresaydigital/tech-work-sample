//
//  ImageBaseUrlBuilder.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import Foundation

class ImageBaseUrlBuilder {
    
    private var model: ConfigModel
    private var type: ImageTypes
    init(config: ConfigModel = UserDefaultData.configModel, forTypeAndSize: ImageTypes) {
        model = config
        type = forTypeAndSize
    }
    
    func createURL(filePath: String) -> URL? {
        let baseConfig = model.images
        let baseURL = baseConfig?.secureBaseURL ?? ""
        let size = getSizeString()
        return URL(string: baseURL + size + filePath)
    }
    
    private func getSizeString() -> String {
        return type.sizeString
    }
}
