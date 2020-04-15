//
//  AdditionalUserInfoController.swift
//  iOS UI Mockup
//
//  Created by Max Van de Wille on 2/18/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit
 
class AdditionalUserInfoController: UIViewController
{
    var formDict: Dictionary<String, Any> = [:]
    var calLimit: Int = 0
    
    @IBOutlet weak var _name: UITextField!
    @IBOutlet weak var _height: UITextField!
    @IBOutlet weak var _weight: UITextField!
    @IBOutlet weak var _gender: UITextField!
    @IBOutlet weak var _age: UITextField!
    @IBOutlet weak var _life: SnappingSlider!
    @IBOutlet weak var _errorLabel: UILabel!
    @IBOutlet weak var _submit: UIButton!
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        super.touchesBegan(touches, with: event)
    }
    
    @IBAction func submitForm(_ sender: Any)
    {
        _errorLabel.isHidden = true
        if (_name.text == "" || _height.text == "" || _weight.text == "" || _gender.text == "Select one:" || _age.text == "" || _gender.text == "")
        {
            _errorLabel.text = "Please fill out all fields!"
            _errorLabel.isHidden = false
            return
        }
        let user_email = UserDefaults.standard.string(forKey: "email")
        let fullName = _name.text
        let heightVal = Int(_height.text!)
        let weightVal = Int(_weight.text!)
        let gender = _gender.text
        let age = Int(_age.text!)
        var lifestyle : String
        if (_life.value == 0)
        {
            lifestyle = "Inactive"
        }
        else if (_life.value == 1)
        {
            lifestyle = "Moderate"
        }
        else
        {
            lifestyle = "Active"
        }
        calLimit = calculateCalories(gender!, heightVal!, weightVal!, lifestyle, age!)
        formDict = ["email": user_email, "name":fullName, "height":heightVal, "weight": weightVal, "lifestyle":lifestyle, "gender":gender, "calorieLimit":calLimit, "age":age]
        UserDefaults.standard.set(fullName, forKey: "userName")
        DispatchQueue.main.async {
            self.performSegue(withIdentifier: "signupSuccess2", sender: nil)
        }
    }
    
    func calculateCalories(_ gender: String,_ height: Int,_ weight: Int,_ lifestyle: String,_ age: Int) -> Int
    {
        var limitMale: Double
        var limitFemale: Double
        var multiplier = 1.2
        if lifestyle == "Active"
        {
            multiplier = 1.9
        }
        else if lifestyle == "Inactive"
        {
            multiplier = 1.2
        }
        else if lifestyle == "Moderate"
        {
            multiplier = 1.55
        }
        limitMale = 66
        limitMale += (6.3 * Double(weight))
        limitMale += (12.9 * Double(height))
        limitMale -= (6.8 * Double(age))
        limitMale *= multiplier

        limitFemale = 665
        limitFemale += (4.3 * Double(weight))
        limitFemale += (4.7 * Double(height))
        limitFemale -= (4.7 * Double(age))

        if gender == "Male"
        {
            return Int(limitMale)
        }
        if gender == "Female"
        {
            return Int(limitFemale)
        }
        return Int((limitMale + limitFemale)/2)
    }
    
    var genders = ["Select one:", "Male", "Female", "Other"]
    var selectedGender : String = ""
    
    let genderPicker = UIPickerView()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        DispatchQueue.main.async {
            self._submit.layer.cornerRadius = 5
        }
        formDict = UserDefaults.standard.dictionary(forKey: "userInfo")!
        _errorLabel.isHidden = true
        createGenderPicker()
        createToolbar()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
        super.viewWillAppear(animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
        super.viewWillAppear(animated)
    }
    
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
    
}

final class SnappingSlider: UISlider {
    override var value: Float {
        set { super.value = newValue }
        get {
            return round(super.value * 1.0) / 1.0
        }
    }
}

extension AdditionalUserInfoController: UIPickerViewDelegate, UIPickerViewDataSource {
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return genders.count
    }
    
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return genders[row]
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
        
        return label
    }
}
