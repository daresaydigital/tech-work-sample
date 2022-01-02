//
//  URLRouter.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import Foundation

enum URLRouter {

    case popular(Int)
    case topRated(Int)

    static let BaseURL = "https://api.themoviedb.org/3/"
    
    var url: URL {
        let apiKey = "8ecb944aae5bedb9723ec0180cabb163"
        let language = "en-US"
        var reletivePathAndPage: (String, Int) {
            switch self {
            case .popular(let page):
                return ("movie/popular", page)
            case .topRated(let page):
                return ("movie/top_rated", page)
            }
        }
        let (relativePath, page) = reletivePathAndPage
        let urlString = URLRouter.BaseURL + relativePath + "?api_key=\(apiKey)&language=\(language)&page=\(page)"
        let escapedUrlString: String = urlString.addingPercentEncoding(withAllowedCharacters:CharacterSet.urlQueryAllowed) ?? urlString
        return URL(string: escapedUrlString)!
    }
}
