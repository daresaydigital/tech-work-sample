//
//  GeneresFilterOptionsRouter.swift
//  GeneresFilterOptions
//
//  Created by mohannazakizadeh on 4/26/22.
//

import UIKit

final class GeneresFilterOptionsRouter: RouterInterface {

    weak var presenter: GeneresFilterOptionsPresenterRouterInterface!

    weak var viewController: UIViewController?
}

extension GeneresFilterOptionsRouter: GeneresFilterOptionsRouterInterface {

}
