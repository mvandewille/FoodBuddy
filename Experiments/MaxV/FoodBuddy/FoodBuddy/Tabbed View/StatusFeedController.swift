//
//  StatusFeedController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 3/15/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class StatusCell : UITableViewCell
{
    @IBOutlet weak var _profilePicture: UIStackView!
    @IBOutlet weak var _name: UILabel!
    @IBOutlet weak var _messaghe: UILabel!
    @IBOutlet weak var _likeBtn: UIButton!
    @IBOutlet weak var _likeCount: UILabel!
    @IBOutlet weak var _reportBtn: UIButton!
    
    @IBAction func addLike(_ sender: Any)
    {
        
    }
    
    @IBAction func reportStatus(_ sender: Any)
    {
        
    }
}

class StatusFeedController : UIViewController
{
    @IBOutlet weak var _tableView: UITableView!
    
    var statuses = [[String:Any]]()

    override func viewDidLoad() {
        super.viewDidLoad()
        getStatuses()
        _tableView.delegate = self
        _tableView.dataSource = self
    }
    
    func getStatuses()
    {
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/find/email/basic?email=" + UserDefaults.standard.string(forKey: "email")!
        let newString = urlStr.replacingOccurrences(of: " ", with: "+")
        let url = URL(string: newString)
        var request = URLRequest(url: url!)
        request.httpMethod = "GET"
        
        //send request and decode response if exists
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                return
            }
            let response = try? JSONSerialization.jsonObject(with: data, options: [])
            if let dictionary = response as? [String: Any] {
                self.statuses = dictionary["content"] as! [[String : Any]]
            }
        }
        task.resume()
    }
}

extension StatusFeedController : UITableViewDataSource, UITableViewDelegate
{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return statuses.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        <#code#>
    }
}
