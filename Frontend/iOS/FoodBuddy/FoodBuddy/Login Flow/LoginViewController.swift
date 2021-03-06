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
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        super.touchesBegan(touches, with: event)
    }
    
    //MARK: -View Init/Deinit
    override func viewDidLoad() {
        super.viewDidLoad()
        DispatchQueue.main.async {
            self._error_label.isHidden = true
            self._login_button.layer.cornerRadius = 5
        }
        // Do any additional setup after loading the view.
    }
    
    @IBOutlet weak var _email: UITextField!
    @IBOutlet weak var _password: UITextField!
    @IBOutlet weak var _login_button: UIButton!
    @IBOutlet weak var _error_label: UILabel!
    
    //MARK: - Login Btn Action
    @IBAction func LoginButton(_ sender: Any)
    {
        DispatchQueue.main.async {
            self._error_label.isHidden = true
        }
        let email = _email.text
        let password = _password.text
        
        let data = password!.data(using: .utf8)!
        let hash_result = SHA512.hash(data: data)
        let hashStr = hash_result.map { String(format: "%02hhx", $0) }.joined()
        
        DoLogin(email!, hashStr)
    }
    
    //MARK: - Login Auth HTTP
    func DoLogin(_ email: String, _ pwd: String) {
        
        let urlStr = "http://foodbuddy-env-main.eba-yminfrgp.us-east-2.elasticbeanstalk.com/user/auth?email=" + email + "&password=" + String(pwd)
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
                        UserDefaults.standard.set(pwd, forKey: "password")
                        UserDefaults.standard.set(email, forKey: "email")
                        self.DoFieldCheck(email, pwd)
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
    
    //MARK: -Very User Info HTTP
    func DoFieldCheck(_ email: String,_ pwd: String)
    {
        let urlStr = "http://foodbuddy-env-main.eba-yminfrgp.us-east-2.elasticbeanstalk.com/user/find/email/basic?email=" + email
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
                if let hasLifestyle = dictionary["lifestyle"] as? String
                {
                    UserDefaults.standard.set(dictionary["name"], forKey: "userName")
                    DispatchQueue.main.async {
                        self.performSegue(withIdentifier: "loginSuccess", sender: nil)
                    }
                }
                else
                {
                    DispatchQueue.main.async {
                        self.performSegue(withIdentifier: "needInfo", sender: nil)
                    }
                }
            }
        }
        task.resume()
    }
}
