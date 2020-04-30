//
//  AddFoodController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 3/9/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class AddFoodController: UIViewController
{
    @IBOutlet weak var _foodName: UITextField!
    @IBOutlet weak var _calories: UITextField!
    @IBOutlet weak var _sodium: UITextField!
    @IBOutlet weak var _carbs: UITextField!
    @IBOutlet weak var _protein: UITextField!
    @IBOutlet weak var _fat: UITextField!
    @IBOutlet weak var _cholesterol: UITextField!
    @IBOutlet weak var _servings: UITextField!
    @IBOutlet weak var _errorLabel: UILabel!
    @IBOutlet weak var _addFoodBtn: UIButton!
    
    var foodDict : Dictionary<String, Any> = [:]
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        super.touchesBegan(touches, with: event)
    }
    
    override func viewDidLoad() {
        _errorLabel.isHidden = true
    }
    
    @IBAction func addFood(_ sender: Any)
    {
        _errorLabel.isHidden = true
        if (_foodName.text == "" || _calories.text == "" || _sodium.text == "" || _carbs.text == "" || _protein.text == "" || _fat.text == "" || _cholesterol.text == "" || _servings.text == "")
        {
            DispatchQueue.main.async {
                self._errorLabel.text = "Please fill out all fields"
                self._errorLabel.isHidden = false
            }
            return
        }
        foodDict["email"] = UserDefaults.standard.string(forKey: "email")
        foodDict["name"] = _foodName.text!
        foodDict["calories"] = Double(_calories.text!)
        foodDict["sodium"] = Double(_sodium.text!)
        foodDict["carbs"] = Double(_carbs.text!)
        foodDict["protein"] = Double(_protein.text!)
        foodDict["fat"] = Double(_fat.text!)
        foodDict["cholesterol"] = Double(_cholesterol.text!)
        foodDict["amount"] = Double(_servings.text!)
        doHTTP(dict: foodDict)
    }
    
    func doHTTP(dict : Dictionary<String, Any>)
    {
        let jsonData = try? JSONSerialization.data(withJSONObject: dict, options: [])
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/day/add/food"
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
                        self.performSegue(withIdentifier: "foodAdded", sender: nil)
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
