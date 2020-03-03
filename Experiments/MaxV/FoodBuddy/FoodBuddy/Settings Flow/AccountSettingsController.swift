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
    var userDict : [String: Any?] = [:]
    
    @IBOutlet weak var _profilePicture: UIImageView!
    @IBOutlet weak var _name: UITextField!
    @IBOutlet weak var _logOut: UIBarButtonItem!
    @IBOutlet weak var _email: UITextField!
    @IBOutlet weak var _height: UITextField!
    @IBOutlet weak var _weight: UITextField!
    @IBOutlet weak var _gender: UITextField!

    
    @IBAction func logOut(_ sender: Any)
    {
        UserDefaults.standard.removeObject(forKey: "userInfo")
        UserDefaults.standard.removeObject(forKey: "password")
        self.performSegue(withIdentifier: "logOut", sender: nil)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
    }

    override func viewDidLoad() {
        userDict = UserDefaults.standard.dictionary(forKey: "userInfo")!
        _name.text = userDict["name"] as? String
        _email.text = userDict["email"] as? String
        var textHeight = ""
        if let v = userDict["height"]
        {
            textHeight = "\(v!)"
        }
        _height.text = textHeight
        var textWeight = ""
        if let v = userDict["weight"]
        {
            textWeight = "\(v!)"
        }
        _weight.text = textWeight
        _gender.text = userDict["gender"] as? String
    }
}
