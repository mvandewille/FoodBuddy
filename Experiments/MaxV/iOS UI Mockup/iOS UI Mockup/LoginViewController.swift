//
//  LoginViewController.swift
//  iOS UI Mockup
//
//  Created by Max Van de Wille on 2/16/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit
import CryptoKit

class LoginViewController: UIViewController
{
    override func viewDidLoad() {
        super.viewDidLoad()
        DispatchQueue.main.async {
            self._error_label.isHidden = true
        }
        // Do any additional setup after loading the view.
    }
    
    @IBOutlet weak var _email: UITextField!
    @IBOutlet weak var _password: UITextField!
    @IBOutlet weak var _login_button: UIButton!
    @IBOutlet weak var _error_label: UILabel!
    
    @IBAction func LoginButton(_ sender: Any)
    {
        DispatchQueue.main.async {
            self._error_label.isHidden = true
        }
        let email = _email.text
        let password = _password.text
        
        if (email == "" || password == "")
        {
            return
        }
        
        let data = password!.data(using: .utf8)!
        let hash_result = SHA512.hash(data: data)
        let hashStr = hash_result.map { String(format: "%02hhx", $0) }.joined()
        
        DoLogin(email!, hashStr)
    }
    
    func DoLogin(_ email: String, _ pwd: String) {
        
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/auth?email=" + email + "&password=" + String(pwd)
        let newString = urlStr.replacingOccurrences(of: " ", with: "+")
        let url = URL(string: newString)
        var request = URLRequest(url: url!)
        request.httpMethod = "GET"
        
        //send request and decode response if exists
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                DispatchQueue.main.async {
                    self._error_label.text = "Could not access server"
                    self._error_label.isHidden = false
                }
                return
            }
            let response = try? JSONSerialization.jsonObject(with: data, options: [])
            if let dictionary = response as? [String: Any] {
                if let returnCode = dictionary["response"] as? Int {
                    if (returnCode == 1)
                    {
                        DispatchQueue.main.async {
                            self.performSegue(withIdentifier: "loginSuccess", sender: nil)
                            UserDefaults.standard.set(pwd, forKey: "password")
                            UserDefaults.standard.set(email, forKey: "email")
//                            let tabbedBoard = UIStoryboard(name: "TabbedPages", bundle: nil)
//                            let controller = tabbedBoard.instantiateViewController(withIdentifier: "tabbedFirst")
//                            self.present(controller, animated: true, completion: nil)
                        }
                    }
                    else
                    {
                        DispatchQueue.main.async {
                            self._error_label.text = "Incorrect email/password"
                            self._error_label.isHidden = false
                        }
                    }
                }
            }
        }
        task.resume()
    }
}
