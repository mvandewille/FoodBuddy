//
//  SignUpViewController.swift
//  iOS UI Mockup
//
//  Created by Max Van de Wille on 2/17/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class SignUpViewController: UIViewController
{
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    @IBOutlet weak var _email_text: UITextField!
    @IBOutlet weak var _pwd_text: UITextField!
    @IBOutlet weak var _pwd_confirm: UITextField!
    
    @IBAction func SignUpButton(_ sender: Any)
    {
        let email = _email_text.text
        let password = _pwd_text.text
        let password_confirm = _pwd_confirm.text
        if (password != password_confirm)
        {
            //TODO - Print error message here
            return
        }
        
        //TODO - Encrypt password here
        DoSignUp(<#T##email: String##String#>, <#T##pwd: String##String#>)
    }
    
    func DoSignUp(_ email: String, _ pwd:String)
    {
        //TODO - Create JSON for email, password here
        //TODO - Build HTTP POST request here
    }
}
