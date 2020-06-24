//
//  AccountSettingsController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 3/1/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class AccountSettingsController: UIViewController
{
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        super.touchesBegan(touches, with: event)
    }
    
    //MARK: Variables
    var genders = ["Select one:", "Male", "Female", "Other"]
    var selectedGender : String = ""
    let genderPicker = UIPickerView()
    
    var userDict : Dictionary<String, Any> = [:]
    var sendDict : Dictionary<String, Any> = [:]
    
    @IBOutlet weak var _name: UITextField!
    @IBOutlet weak var _email: UITextField!
    @IBOutlet weak var _height: UITextField!
    @IBOutlet weak var _weight: UITextField!
    @IBOutlet weak var _gender: UITextField!
    @IBOutlet weak var _age: UITextField!
    @IBOutlet weak var _lifestyle: SnappingSlider!
    @IBOutlet weak var _saveBtn: UIButton!
    @IBOutlet weak var _errorLabel: UILabel!
    
    //MARK: Log Out Btn Action
    @IBAction func logOut(_ sender: Any)
    {
        UserDefaults.standard.removeObject(forKey: "userInfo")
        UserDefaults.standard.removeObject(forKey: "email")
        UserDefaults.standard.removeObject(forKey: "password")
        UserDefaults.standard.removeObject(forKey: "userName")
        self.performSegue(withIdentifier: "logOut", sender: nil)
    }
    
    //MARK: Submit Btn Action
    @IBAction func submitChanges(_ sender: Any) {
        self._errorLabel.isHidden = true
        sendDict["name"] = _name.text
        sendDict["email"] = _email.text
        sendDict["height"] = _height.text
        sendDict["weight"] = _weight.text
        sendDict["gender"] = _gender.text
        sendDict["age"] = _age.text
        var lifestyle = ""
        if (_lifestyle.value == 0)
        {
            lifestyle = "Inactive"
        }
        else if (_lifestyle.value == 1)
        {
            lifestyle = "Moderate"
        }
        else if (_lifestyle.value == 2)
        {
            lifestyle = "Active"
        }
        sendDict["lifestyle"] = lifestyle
        doHTTP(dict: sendDict)
    }
    
    //MARK: View Init/Deinit

    override func viewDidLoad() {
        self._errorLabel.isHidden = true
        DispatchQueue.main.async {
            self._saveBtn.layer.cornerRadius = 5
        }
        createGenderPicker()
        createToolbar()
        let email = UserDefaults.standard.string(forKey: "email")
        DoFieldCheck(email: email!)
    }
    
    func fillFields()
    {
        _name.text = userDict["name"] as? String
        _email.text = userDict["email"] as? String
        var textHeight = ""
        if let v = userDict["height"]
        {
            textHeight = "\(v)"
        }
        _height.text = textHeight
        var textWeight = ""
        if let v = userDict["weight"]
        {
            textWeight = "\(v)"
        }
        _weight.text = textWeight
        _gender.text = userDict["gender"] as? String
        var textAge = ""
        if let v = userDict["age"]
        {
            textAge = "\(v)"
        }
        _age.text = textAge
        if (userDict["lifestyle"] as? String == "Inactive")
        {
            _lifestyle.value = 0
        }
        else if (userDict["lifestyle"] as? String == "Moderate")
        {
            _lifestyle.value = 1
        }
        else if (userDict["lifestyle"] as? String == "Active")
        {
            _lifestyle.value = 2
        }
    }
    
    //MARK: - Gender Picker/Toolbar
    func createGenderPicker() {
        
        let genderPicker = UIPickerView()
        genderPicker.delegate = self
        
        _gender.inputView = genderPicker
    
    }
    
    func createToolbar()
    {
        let toolbar = UIToolbar()
        toolbar.sizeToFit()
        
        let doneButton = UIBarButtonItem(title: "Done", style: .plain, target: self, action: #selector(AdditionalUserInfoController.dismissKeyboard))
        
        toolbar.setItems([doneButton], animated: false)
        toolbar.isUserInteractionEnabled = true
        
        _gender.inputAccessoryView = toolbar
    }
    
    @objc func dismissKeyboard()
    {
        view.endEditing(true)
    }
    
    //MARK: Get User Info HTTP
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
                DispatchQueue.main.async {
                    self.fillFields()
                }
            }
        }
        task.resume()
    }
    
    //MARK: Update User Info
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
                        self.performSegue(withIdentifier: "saveAccountSettings", sender: nil)
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

//MARK: UIPickerView Extensions
extension AccountSettingsController: UIPickerViewDelegate, UIPickerViewDataSource {
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return genders.count
    }
    
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        selectedGender = genders[row]
        _gender.text = selectedGender
    }
    
    func pickerView(_ pickerView: UIPickerView, viewForRow row: Int, forComponent component: Int, reusing view: UIView?) -> UIView {
        
        var label: UILabel
        
        if let view = view as? UILabel {
            label = view
        } else {
            label = UILabel()
        }
        
        label.text = genders[row]
        label.textAlignment = .center
        return label
    }
}
