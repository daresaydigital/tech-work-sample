//
//  ImageLoader.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Combine
import Foundation
import UIKit

final class ImageLoader {
    static let shared = ImageLoader()

    private lazy var backgroundQueue: OperationQueue = {
        let queue = OperationQueue()
        queue.maxConcurrentOperationCount = 5
        return queue
    }()

    func loadImage(from url: URL?) -> AnyPublisher<UIImage?, Never> {
        guard let url else { return Just(nil).eraseToAnyPublisher() }
       
        return URLSession.shared.dataTaskPublisher(for: url)
            .map { (data, _) -> UIImage? in return UIImage(data: data) }
            .catch { _ in return Just(nil) }
            .subscribe(on: backgroundQueue)
            .receive(on: RunLoop.main)
            .eraseToAnyPublisher()
    }
}
