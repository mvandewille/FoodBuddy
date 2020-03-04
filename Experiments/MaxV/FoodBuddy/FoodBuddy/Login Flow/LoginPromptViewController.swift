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
    
    let backgroundImageView = UIImageView()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setBackground()
        let password = UserDefaults.standard.string(forKey: "password")
        let userInfo = UserDefaults.standard.dictionary(forKey: "userInfo")
        if (userInfo != nil && password != nil)
        {
            let email = userInfo!["email"] as? String
            DoLogin(email!, password!)
        }
        // Do any additional setup after loading the view.
    }

    func setBackground()
    {
        view.addSubview(backgroundImageView)
        backgroundImageView.translatesAutoresizingMaskIntoConstraints = false
        backgroundImageView.topAnchor.constraint(equalTo: view.topAnchor).isActive = true
        backgroundImageView.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
        backgroundImageView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        backgroundImageView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        backgroundImageView.image = UIImage(named: "Food finger minus finger")
        self.view.sendSubviewToBack(backgroundImageView)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        super.touchesBegan(touches, with: event)
    }

    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
        super.viewWillAppear(animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
        super.viewWillAppear(animated)
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
    
    func DoFieldCheck(_ email: String,_ pwd: String)
    {
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/find/email?email=" + email
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
                    dictionary.removeValue(forKey: "password")
                    UserDefaults.standard.set(dictionary, forKey: "userInfo")
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
