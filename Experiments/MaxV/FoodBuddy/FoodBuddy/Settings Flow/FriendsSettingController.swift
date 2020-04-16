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
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        super.touchesBegan(touches, with: event)
    }
    
    @IBOutlet weak var _friendsTable: UITableView!
    @IBOutlet weak var _addBtn: UIButton!
    @IBOutlet weak var _email: UITextField!
    @IBOutlet weak var _errorLabel: UILabel!
    
    @IBAction func _addFollowing(_ sender: Any)
    {
        self._errorLabel.isHidden = true
        var userDict : Dictionary<String, Any> = [:]
        userDict["email"] = UserDefaults.standard.string(forKey: "email")
        userDict["following"] = _email.text
        doHTTP(dict: userDict)
    }
    
    func refresh()
    {
        DispatchQueue.main.async {
            let indexPath = IndexPath(row: self.friendsArr.count - 1, section: 0)
            self._friendsTable.beginUpdates()
            self._friendsTable.insertRows(at: [indexPath], with: .automatic)
            self._friendsTable.endUpdates()
            self._email.text = ""
            self.view.endEditing(true)
        }
    }
    
    var friendsArr : [String] = []
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return friendsArr.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: UITableViewCell.CellStyle.default, reuseIdentifier: "cell")
        cell.textLabel?.text = friendsArr[indexPath.row]
        return cell
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if (editingStyle == .delete)
        {
            doHTTPDelete(following: friendsArr[indexPath.row], index: indexPath)
        }
    }
    
    override func viewDidLoad() {
        self._errorLabel.isHidden = true
        DispatchQueue.main.async {
            self._addBtn.layer.cornerRadius = 5
        }
        getFollowers()
    }
    
    func displayFollowers()
    {
        DispatchQueue.main.async {
            self._friendsTable.delegate = self
            self._friendsTable.dataSource = self
        }
    }
    
    func getFollowers()
    {
        let email = UserDefaults.standard.string(forKey: "email")
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/find/following?email=" + email!
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
                    if let blank = dictionary["content"] as? Array<String>
                    {
                        self.friendsArr = dictionary["content"] as! [String]
                        self.displayFollowers()
                    }
                    else
                    {
                        //TODO - HANDLE ERROR
                    }
                }
            }
            task.resume()
        }
    
    func doHTTP(dict : Dictionary<String, Any>)
    {
        let jsonData = try? JSONSerialization.data(withJSONObject: dict, options: [])
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/add/following"
        let url = URL(string: urlStr)
        var request = URLRequest(url: url!)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.setValue("application/json; charset=utf-8", forHTTPHeaderField: "Content-Type")
                
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                DispatchQueue.main.async {
                    self._errorLabel.text = "Unexpected http error"
                    self._errorLabel.textColor = UIColor.red
                    self._errorLabel.isHidden = false
                }
                return
            }
            let responseJSON = try? JSONSerialization.jsonObject(with: data, options: [])
            if let responseJSON = responseJSON as? [String: Any] {
                if (responseJSON["response"] as? Int == 1)
                {
                    self.friendsArr.append(dict["following"] as! String)
                    DispatchQueue.main.async {
                        self._errorLabel.textColor = UIColor.green
                        self._errorLabel.text = "Successfully following user!"
                        self._errorLabel.isHidden = false
                        self.refresh()
                    }
                }
                else
                {
                    DispatchQueue.main.async {
                        self._errorLabel.text = responseJSON["message"] as? String
                        self._errorLabel.textColor = UIColor.red
                        self._errorLabel.isHidden = false
                    }
                }
            }
        }
        task.resume()
    }
    
    func doHTTPDelete(following: String, index: IndexPath)
    {
        var urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/delete/following?email="
        urlStr.append(UserDefaults.standard.string(forKey: "email")! + "&following=" + following)
        let url = URL(string: urlStr)
        var request = URLRequest(url: url!)
        request.httpMethod = "DELETE"
                
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                DispatchQueue.main.async {
                    self._errorLabel.text = "Unexpected http error"
                    self._errorLabel.textColor = UIColor.red
                    self._errorLabel.isHidden = false
                }
                return
            }
            let responseJSON = try? JSONSerialization.jsonObject(with: data, options: [])
            if let responseJSON = responseJSON as? [String: Any] {
                if (responseJSON["response"] as? Int == 1)
                {
                    DispatchQueue.main.async {
                        self._errorLabel.textColor = UIColor.green
                        self._errorLabel.text = "Unfollowed" + following
                        self._errorLabel.isHidden = false
                        self.friendsArr.remove(at: self.friendsArr.firstIndex(of: following)!)
                        self._friendsTable.beginUpdates()
                        self._friendsTable.deleteRows(at: [index], with: .automatic)
                        self._friendsTable.endUpdates()
                    }
                }
                else
                {
                    DispatchQueue.main.async {
                        self._errorLabel.text = responseJSON["message"] as? String
                        self._errorLabel.textColor = UIColor.red
                        self._errorLabel.isHidden = false
                    }
                }
            }
        }
        task.resume()
    }
}
