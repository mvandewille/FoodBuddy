//
//  AllergenSettingsController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 3/1/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class AllergenSettingsController : UIViewController
{
    //MARK: Variables
    @IBOutlet weak var _allergenTable: UITableView!
    @IBOutlet weak var _errorLabel: UILabel!
    @IBOutlet weak var _saveBtn: UIButton!
    
    //MARK: Save Btn Action
    @IBAction func saveAllergies(_ sender: Any)
    {
        userDict["allergens"] = allergenArray
        doHTTP(dict: userDict)
    }
    
    var userDict: Dictionary<String, Any> = [:]
    var allergenArray: [String] = []
    var allAllergens = ["Milk", "Eggs", "Tree Nuts", "Peanuts", "Shellfish", "Wheat", "Soy", "Fish", "Banana", "Garlic"]

    //MARK: View Init/Deinit
    override func viewDidLoad() {
        super.viewDidLoad()
        DispatchQueue.main.async {
            self._saveBtn.layer.cornerRadius = 5
        }
        let email = UserDefaults.standard.string(forKey: "email")
        DoFieldCheck(email: email!)
        self._errorLabel.isHidden = true
    }
    
    func setAllergenTable()
    {
        self._allergenTable.allowsMultipleSelection = true
        self._allergenTable.allowsMultipleSelectionDuringEditing = true
        self._allergenTable.dataSource = self
        self._allergenTable.delegate = self
    }
    
    //MARK: Get User Allergens HTTP
    func DoFieldCheck(email: String)
    {
        let urlStr = "http://foodbuddy-env-main.eba-yminfrgp.us-east-2.elasticbeanstalk.com/user/find/email/basic?email=" + email
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
                self.userDict = dictionary
                self.allergenArray = self.userDict["allergens"] as! [String]
                DispatchQueue.main.async {
                    self.setAllergenTable()
                }
            }
        }
        task.resume()
    }
    
    //MARK: Update User Info HTTP
    func doHTTP(dict : Dictionary<String, Any>)
    {
        let jsonData = try? JSONSerialization.data(withJSONObject: dict, options: [])
        let urlStr = "http://foodbuddy-env-main.eba-yminfrgp.us-east-2.elasticbeanstalk.com/user/update"
        let url = URL(string: urlStr)
        var request = URLRequest(url: url!)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.setValue("application/json; charset=utf-8", forHTTPHeaderField: "Content-Type")
                
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                DispatchQueue.main.async {
                    self._errorLabel.text = "Unexpected http error"
                    self._errorLabel.isHidden = false
                }
                return
            }
            let responseJSON = try? JSONSerialization.jsonObject(with: data, options: [])
            if let responseJSON = responseJSON as? [String: Any] {
                if (responseJSON["response"] as? Int == 1)
                {
                    DispatchQueue.main.async {
                        self.performSegue(withIdentifier: "allergenUnwind", sender: nil)
                    }
                }
                else
                {
                    DispatchQueue.main.async {
                        self._errorLabel.text = responseJSON["message"] as? String
                        self._errorLabel.isHidden = false
                    }
                }
            }
        }
        task.resume()
    }
}

//MARK: UITableView Extensions
extension AllergenSettingsController: UITableViewDataSource, UITableViewDelegate
{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return allAllergens.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: UITableViewCell.CellStyle.default, reuseIdentifier: "cell")
        cell.textLabel?.text = allAllergens[indexPath.row]
        if allergenArray.firstIndex(of: cell.textLabel!.text!) != nil
        {
            cell.accessoryType = UITableViewCell.AccessoryType.checkmark
        }
        cell.selectionStyle = .none
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        if (tableView.cellForRow(at: indexPath)?.accessoryType == UITableViewCell.AccessoryType.checkmark)
        {
            tableView.cellForRow(at: indexPath)?.accessoryType = UITableViewCell.AccessoryType.none
            let testStr = tableView.cellForRow(at: indexPath)?.textLabel?.text!
            let fakeIndex = allergenArray.firstIndex(of: testStr!)
            allergenArray.remove(at: fakeIndex!)
        }
        else
        {
            tableView.cellForRow(at: indexPath)?.accessoryType = UITableViewCell.AccessoryType.checkmark
            let testStr = (tableView.cellForRow(at: indexPath)?.textLabel!.text)!
            let testIndex = allergenArray.firstIndex(of: testStr)
            if testIndex == nil
            {
                allergenArray.append((tableView.cellForRow(at: indexPath)?.textLabel!.text)!)
            }
        }
        
    }
}
