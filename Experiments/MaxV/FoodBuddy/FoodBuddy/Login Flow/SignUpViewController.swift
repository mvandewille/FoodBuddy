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
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        super.touchesBegan(touches, with: event)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        DispatchQueue.main.async {
            self._error_label.isHidden = true
        }
        // Do any additional setup after loading the view.
    }

    @IBOutlet weak var _email_text: UITextField!
    @IBOutlet weak var _pwd_text: UITextField!
    @IBOutlet weak var _pwd_confirm: UITextField!
    @IBOutlet weak var _submit_btn: UIButton!
    @IBOutlet weak var _error_label: UILabel!
    
    @IBAction func SignUpButton(_ sender: Any)
    {
        DispatchQueue.main.async {
            self._error_label.isHidden = true
        }
        let email = _email_text.text
        let password = _pwd_text.text
        let password_confirm = _pwd_confirm.text
        if (password != password_confirm)
        {
            DispatchQueue.main.async {
                self._error_label.text = "Passwords do not match"
                self._error_label.isHidden = false
            }
            return
        }
        
        let data = password!.data(using: .utf8)!
        let hash_result = SHA512.hash(data: data)

        let hashStr = hash_result.map { String(format: "%02hhx", $0) }.joined()
        
        DoSignUp(email!, hashStr)
    }

    func DoSignUp(_ email: String, _ pwd: String)
    {
        let json: [String: Any] = ["email": email, "password": pwd]
        let jsonData = try? JSONSerialization.data(withJSONObject: json, options: [])
        //TODO - Create JSON for email, password here
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/add"
        let url = URL(string: urlStr)
        var request = URLRequest(url: url!)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.setValue("application/json; charset=utf-8", forHTTPHeaderField: "Content-Type")
        
        
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                DispatchQueue.main.async {
                    self._error_label.text = "Unexpected http error"
                    self._error_label.isHidden = false
                }
                return
            }
            let responseJSON = try? JSONSerialization.jsonObject(with: data, options: [])
            if let responseJSON = responseJSON as? [String: Any] {
                if (responseJSON["response"] as? Int == 1)
                {
                    DispatchQueue.main.async {
                        self.performSegue(withIdentifier: "signupSuccess", sender: nil)
                        UserDefaults.standard.set(pwd, forKey: "password")
                        UserDefaults.standard.set(email, forKey: "email")
//                        let tabbedBoard = UIStoryboard(name: "TabbedPages", bundle: nil)
//                        let controller = tabbedBoard.instantiateViewController(withIdentifier: "tabbedFirst")
//                        self.present(controller, animated: true, completion: nil)
                    }
                }
                else
                {
                    DispatchQueue.main.async {
                        self._error_label.text = responseJSON["message"] as? String
                        self._error_label.isHidden = false
                    }
                }
            }
        }
        task.resume()
    }
}
