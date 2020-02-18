//
//  SignUpViewController.swift
//  iOS UI Mockup
//
//  Created by Max Van de Wille on 2/17/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit
import CryptoKit

class SignUpViewController: UIViewController
{
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    @IBOutlet weak var _email_text: UITextField!
    @IBOutlet weak var _pwd_text: UITextField!
    @IBOutlet weak var _pwd_confirm: UITextField!
    @IBOutlet weak var _submit_btn: UIButton!
    
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

        if let data = password!.data(using: .utf8) {
            let hash = SHA512.hash(data: data)
        }
        
        DoSignUp(email!, hash)
    }

    func DoSignUp(_ email: String, _ pwd:Int)
    {
        
        
        //TODO - Create JSON for email, password here
        //TODO - Build HTTP POST request here
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/add"
        let url = URL(string: urlStr)
        var request = URLRequest(url: url!)
        request.httpMethod = "POST"
        
        request.httpBody
    }
}
