//
//  LoginViewController.swift
//  iOS UI Mockup
//
//  Created by Max Van de Wille on 2/16/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class LoginViewController: UIViewController
{
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    @IBOutlet weak var _email: UITextField!
    @IBOutlet weak var _password: UITextField!
    @IBOutlet weak var _login_button: UIButton!
    @IBOutlet weak var _error_label: UILabel!
    
    @IBAction func LoginButton(_ sender: Any)
    {
        let email = _email.text
        let password = _password.text
        
        if (email == "" || password == "")
        {
            return
        }
        DoLogin(email!, password!)
    }
    
    func DoLogin(_ email: String, _ pwd: String) {
        
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/auth?email=" + email + "&password=" + pwd
        let url = URL(string: urlStr)
        var request = URLRequest(url: url!)
        request.httpMethod = "GET"
        
        //send request and decode response if exists
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                
                self._error_label.text = "Could not access server"
                return
            }
            let responseJSON = try? JSONSerialization.jsonObject(with: data, options: [])
            if let responseJSON = responseJSON as? [String: Int] {
                for item in responseJSON {
                    if let item = item as? [String: Int], let responseResult = item["response"] {
                        if (responseResult == 1)
                        {
                            self.performSegue(withIdentifier: "loginSuccess", sender: nil)
                        }
                    }
                }
            }
        }
        task.resume()
    }
}
