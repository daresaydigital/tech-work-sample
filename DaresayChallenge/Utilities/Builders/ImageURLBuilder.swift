//
//  ImageURLBuilder.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import Foundation

final class ImageBaseUrlBuilder {
    
    // MARK: - Variables
    private var model: ConfigurationModel
    private var type: ImageTypes
    
    // MARK: - Init
    init(config: ConfigurationModel = UserDefaultsData.configModel, forTypeAndSize: ImageTypes) {
        model = config
        type = forTypeAndSize
    }
    
    // MARK: - Helpers
    public func createURL(filePath: String) -> URL? {
        let baseConfig = model.images
        let baseURL = baseConfig?.secureBaseURL ?? ""
        let size = getSizeString()
        return URL(string: baseURL + size + filePath)
    }
    
    private func getSizeString() -> String {
        return type.sizeString
    }
}

