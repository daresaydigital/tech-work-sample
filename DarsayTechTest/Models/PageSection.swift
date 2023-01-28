//
//  PageSection.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

struct PageSection: Identifiable, Hashable {
    typealias ID = String
    let id: ID
    
    var cellReuseIdentifier: String {
        "cell-\(id)"
    }
}
