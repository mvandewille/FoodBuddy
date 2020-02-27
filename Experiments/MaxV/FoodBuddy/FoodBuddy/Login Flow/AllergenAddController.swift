//
//  AllergenAddController.swift
//  iOS UI Mockup
//
//  Created by Max Van de Wille on 2/26/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class AllergenAddController : UIViewController
{
    var incomingDict: Dictionary<String, Any> = [:]
    
    @IBOutlet weak var _calories: UITextField!
    @IBOutlet weak var _continue: UIButton!
    @IBOutlet weak var _stack: UIStackView!
    
    @IBAction func doSubmit(_ sender: Any)
    {
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        var textCalories = ""
        if let v = incomingDict["calorieLimit"]
        {
            textCalories = "\(v)"
        }
        _calories.text = textCalories
        _stack.setCustomSpacing(50.0, after: _calories)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
        super.viewWillAppear(animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
        super.viewWillAppear(animated)
    }
    
    func doHTTP(dict : Dictionary<String, Any>)
    {
        let jsonData = try? JSONSerialization.data(withJSONObject: dict, options: [])
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/update"
        let url = URL(string: urlStr)
        var request = URLRequest(url: url!)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.setValue("application/json; charset=utf-8", forHTTPHeaderField: "Content-Type")
                
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                DispatchQueue.main.async {
//                    self._errorLabel.text = "Unexpected http error"
//                    self._errorLabel.isHidden = false
                }
                return
            }
            let responseJSON = try? JSONSerialization.jsonObject(with: data, options: [])
            if let responseJSON = responseJSON as? [String: Any] {
                if (responseJSON["response"] as? Int == 1)
                {
                    DispatchQueue.main.async {
                        self.performSegue(withIdentifier: "signupSuccess2", sender: nil)
                    }
                }
                else
                {
                    DispatchQueue.main.async {
//                        self._errorLabel.text = responseJSON["message"] as? String
//                        self._errorLabel.isHidden = false
                    }
                }
            }
        }
        task.resume()
    }
}
