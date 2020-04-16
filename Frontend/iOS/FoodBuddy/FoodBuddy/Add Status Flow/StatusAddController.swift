//
//  StatusAddController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 3/9/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class StatusAddController: UIViewController,  UITextViewDelegate
{
    @IBOutlet weak var textView: UITextView!
    @IBOutlet weak var _post: UIButton!
    @IBOutlet weak var _errorlabel: UILabel!
    
    var userDict: Dictionary<String, Any> = [:]
    
    @IBAction func postStatus(_ sender: Any) {
        if (textView.text == "" || textView.text == "Enter your message here...")
        {
            _errorlabel.text = "Please fill out all fields"
            _errorlabel.isHidden = false
            return
        }
        userDict["email"] = UserDefaults.standard.string(forKey: "email")
        userDict["message"] = self.textView.text
        doHTTP(dict: userDict)
    }
    
    override func viewDidLoad() {
        _errorlabel.isHidden = true
        textView.delegate = self
        textView.text = "Enter your message here..."
        textView.textColor = UIColor.lightGray
        textView.layer.borderWidth = 1.0;
        textView.layer.borderColor = UIColor.gray.cgColor
    }
    
    func textViewDidEndEditing(_ textView: UITextView) {
        if textView.text.isEmpty {
            textView.text = "Enter your message here..."
            textView.textColor = UIColor.lightGray
        }
    }
    
    func textViewDidBeginEditing(_ textView: UITextView) {
        if textView.textColor == UIColor.lightGray {
            textView.text = ""
            textView.textColor = UIColor.black
        }
    }
    
    func doHTTP(dict : Dictionary<String, Any>)
    {
        let jsonData = try? JSONSerialization.data(withJSONObject: dict, options: [])
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/status/add"
        let url = URL(string: urlStr)
        var request = URLRequest(url: url!)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.setValue("application/json; charset=utf-8", forHTTPHeaderField: "Content-Type")
                
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                DispatchQueue.main.async {
                    self._errorlabel.text = "Unexpected http error"
                    self._errorlabel.isHidden = false
                }
                return
            }
            let responseJSON = try? JSONSerialization.jsonObject(with: data, options: [])
            if let responseJSON = responseJSON as? [String: Any] {
                if (responseJSON["response"] as? Int == 1)
                {
                    DispatchQueue.main.async {
                        self.performSegue(withIdentifier: "statusSuccess", sender: nil)
                    }
                }
                else
                {
                    DispatchQueue.main.async {
                        self._errorlabel.text = responseJSON["message"] as? String
                        self._errorlabel.isHidden = false
                    }
                }
            }
        }
        task.resume()
    }
}
