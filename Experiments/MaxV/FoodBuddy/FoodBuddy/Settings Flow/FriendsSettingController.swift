//
//  FriendsSettingController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 3/6/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class FriendsSettingController: UIViewController, UITableViewDelegate, UITableViewDataSource
{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return friendsArr.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: UITableViewCell.CellStyle.default, reuseIdentifier: "cell")
        cell.textLabel?.text = friendsArr[indexPath.row] as! String
        return cell
    }
    
    @IBOutlet weak var _friendsTable: UITableView!
    
    var friendsArr : [Any] = []
    
    override func viewDidLoad() {
        getFollowers()
        _friendsTable.delegate = self
        _friendsTable.dataSource = self
    }
    
    func getFollowers()
    {
        let userDict = UserDefaults.standard.dictionary(forKey: "userInfo")
        let email = userDict?["email"] as! String
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/find/following?email=" + email
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
                if var dictionary = response as? [String: Any] {
                    if let blank = dictionary["content"] as? Array<String>
                    {
                        self.friendsArr = dictionary["content"] as! [String]
                    }
                    else
                    {
                        //TODO - HANDLE ERROR
                    }
                }
            }
            task.resume()
        }
    
}
