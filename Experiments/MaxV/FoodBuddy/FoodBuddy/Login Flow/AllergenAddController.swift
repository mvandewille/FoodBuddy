//
//  AllergenAddController.swift
//  iOS UI Mockup
//
//  Created by Max Van de Wille on 2/26/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class AllergenAddController : UIViewController, UITableViewDelegate, UITableViewDataSource
{
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        super.touchesBegan(touches, with: event)
    }
    
    var incomingDict: Dictionary<String, Any> = [:]
    var allergenArray: [String] = []
    var allAllergens = ["Milk", "Eggs", "Tree Nuts", "Peanuts", "Shellfish", "Wheat", "Soy", "Fish", "Banana", "Garlic"]
    
    @IBOutlet weak var _calories: UITextField!
    @IBOutlet weak var _continue: UIButton!
    @IBOutlet weak var _stack: UIStackView!
    @IBOutlet weak var _errorlabel: UILabel!
    @IBOutlet weak var _allergenTable: UITableView!
    
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
        cell.tintColor = UIColor(rgb: 0x54A456)
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
    
    @IBAction func doSubmit(_ sender: Any)
    {
        incomingDict["calorieLimit"] = Int(_calories.text!)
        incomingDict["allergens"] = allergenArray
        doHTTP(dict: incomingDict)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        incomingDict = UserDefaults.standard.dictionary(forKey: "userInfo")!
        self._errorlabel.isHidden = true
        self._allergenTable.allowsMultipleSelection = true
        self._allergenTable.allowsMultipleSelectionDuringEditing = true
        var textCalories = ""
        if let v = incomingDict["calorieLimit"]
        {
            textCalories = "\(v)"
        }
        _calories.text = textCalories
        _stack.setCustomSpacing(50.0, after: _calories)
        _allergenTable.dataSource = self
        _allergenTable.delegate = self
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
        super.viewWillAppear(animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
        super.viewWillAppear(animated)
    }
    
    func doHTTP(dict : Dictionary<String, Any>)
    {
        let jsonData = try? JSONSerialization.data(withJSONObject: dict, options: [])
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/update"
        let url = URL(string: urlStr)
        var request = URLRequest(url: url!)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.setValue("application/json; charset=utf-8", forHTTPHeaderField: "Content-Type")
                
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                DispatchQueue.main.async {
                    self._errorlabel.text = "Unexpected http error"
                    self._errorlabel.isHidden = false
                }
                return
            }
            let responseJSON = try? JSONSerialization.jsonObject(with: data, options: [])
            if let responseJSON = responseJSON as? [String: Any] {
                if (responseJSON["response"] as? Int == 1)
                {
                    DispatchQueue.main.async {
                        self.performSegue(withIdentifier: "signupSuccess3", sender: nil)
                    }
                }
                else
                {
                    DispatchQueue.main.async {
                        self._errorlabel.text = responseJSON["message"] as? String
                        self._errorlabel.isHidden = false
                    }
                }
            }
        }
        task.resume()
    }
}
