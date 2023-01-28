//
//  URL.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

extension URL {
    
    static func getFullPath(sizeType: Image.Types = .backDrop(.w300), nestedURLString: String) throws -> Self {
        
        let urlString = [
            Constant.imageBaseURL,
            sizeType.sizeString,
            nestedURLString
        ].joined(separator: "")
        
        guard let url = URL(string: urlString) else { throw  AppError(reason: LocalizeHelper.shared.lookup(.urlCreationFailed)) }
        return url
    }
}
