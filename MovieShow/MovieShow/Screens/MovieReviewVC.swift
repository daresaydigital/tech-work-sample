//
//  MovieReviewVC.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2022-01-02.
//

import UIKit

class MovieReviewVC: UIViewController {
    
    var reviews: [Review] = [] {
        didSet {
            tableView.reloadData()
            reviews.forEach { print("DEBUG: reviews age \($0.reviewAgeText)") }
        }
    }
    
    lazy var tableView: UITableView = {
        let tv = UITableView()
        tv.frame = view.bounds
        tv.separatorStyle = .none
        tv.register(ReviewCell.self, forCellReuseIdentifier: ReviewCell.reuseID)
        return tv
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .brown
        configureTableView()
    }
    
    func configureTableView() {
        view.addSubview(tableView)
        tableView.dataSource = self
        
    }
}

extension MovieReviewVC: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return reviews.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: ReviewCell.reuseID , for: indexPath) as! ReviewCell
        let review = reviews[indexPath.item]
        let viewmodel = ReviewViewmodel(review: review)
        cell.viewmodel = viewmodel
        return cell
    }
}
