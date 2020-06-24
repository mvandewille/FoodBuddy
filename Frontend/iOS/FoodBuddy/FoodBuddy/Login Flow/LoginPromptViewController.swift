//
//  LoginPromptViewController.swift
//  iOS UI Mockup
//
//  Created by Max Van de Wille on 2/17/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class LoginPromptViewController: UIViewController {
    
    @IBOutlet weak var _loginBTN: UIButton!
    
    @IBOutlet weak var signupBTN: UIButton!
    
    //MARK: -Variables
    private let backgroundImageView = UIImageView()
    
    //MARK: -View Init/Deinit
    override func viewDidLoad() {
        super.viewDidLoad()
        _loginBTN.layer.cornerRadius = 5
        _loginBTN.layer.backgroundColor = UIColor(rgb: 0x54A456).cgColor
        signupBTN.layer.cornerRadius = 5
        signupBTN.layer.backgroundColor = UIColor(rgb: 0x5195FF).cgColor
        let password = UserDefaults.standard.string(forKey: "password")
        let email = UserDefaults.standard.string(forKey: "email")
        if (email != nil && password != nil)
        {
            DoLogin(email!, password!)
        }
    }

    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
        super.viewWillAppear(animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
        super.viewWillAppear(animated)
    }
    
    //MARK: - Perform Login HTTP
    func DoLogin(_ email: String, _ pwd: String) {
        
        let urlStr = "http://foodbuddy-env-main.eba-yminfrgp.us-east-2.elasticbeanstalk.com/user/auth?email=" + email + "&password=" + String(pwd)
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
                if let returnCode = dictionary["response"] as? Int {
                    if (returnCode == 1)
                    {
                        self.DoFieldCheck(email, pwd)
                    }
                    else
                    {
                        return
                    }
                }
            }
        }
        task.resume()
    }
    
    //MARK: -Verify User Info HTTP
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
                return
            }
            let response = try? JSONSerialization.jsonObject(with: data, options: [])
            if var dictionary = response as? [String: Any] {
                if let hasLifestyle = dictionary["lifestyle"] as? String
                {
                    DispatchQueue.main.async
                    {
                        self.performSegue(withIdentifier: "bypassLogin", sender: nil)
                    }
                }
                else
                {
                    DispatchQueue.main.async
                    {
                        self.performSegue(withIdentifier: "needInfo2", sender: nil)
                    }
                }
            }
        }
        task.resume()
    }
    
    @IBAction func unwindToRootViewController(segue: UIStoryboardSegue)
    {
        UserDefaults.standard.removeObject(forKey: "email")
        UserDefaults.standard.removeObject(forKey: "password")
        print("Unwinding for logout")
    }
}
