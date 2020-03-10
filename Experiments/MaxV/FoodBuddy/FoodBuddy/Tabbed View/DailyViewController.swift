//
//  DailyViewController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 3/10/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit
import MBCircularProgressBar

class DailyViewController : UIViewController
{
    @IBOutlet weak var _dateLabel: UILabel!
    @IBOutlet weak var _calories: MBCircularProgressBarView!
    
    @IBOutlet weak var _proteinBar: UIProgressView!
    @IBOutlet weak var _proteinLabel: UILabel!
    
    @IBOutlet weak var _sodiumBar: UIProgressView!
    @IBOutlet weak var _sodiumLabel: UILabel!
    
    @IBOutlet weak var _cholesterolBar: UIProgressView!
    @IBOutlet weak var _cholesterolLabel: UILabel!
    
    @IBOutlet weak var _carbBar: UIProgressView!
    @IBOutlet weak var _carbLabel: UILabel!
    
    @IBOutlet weak var _fatBar: UIProgressView!
    @IBOutlet weak var _fatLabel: UILabel!
    
    var calorieLimit : Int = 0
    var dailyTotals : Dictionary<String, Double> = [:]
    let proteinLimit : Double = 50
    let fatLimit : Double = 75
    let carbLimit : Double = 325
    let sodiumLimit : Double = 2.5
    let cholesterolLimit : Double = 0.300
    
    override func viewDidLoad() {
        getLimits()
        getData()
        setDate()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        DispatchQueue.main.async {
            self._calories.value = CGFloat(self.dailyTotals["calories"]!)
            
            //PROTEIN BAR
            self._proteinBar.progress = Float(self.dailyTotals["protein"]!/self.proteinLimit)
            self._proteinLabel.text = "\(self.dailyTotals["protein"]!)" + "/" + "\(self.proteinLimit)"
            
            //SODIUM BAR
            self._sodiumBar.progress = Float(self.dailyTotals["sodium"]!/self.sodiumLimit)
            self._sodiumLabel.text = "\(self.dailyTotals["sodium"]!)" + "/" + "\(self.sodiumLimit)"
            
            //CHOLESTEROL
            self._cholesterolBar.progress = Float(self.dailyTotals["cholesterol"]!/self.cholesterolLimit)
            self._cholesterolLabel.text = "\(self.dailyTotals["cholesterol"]!)" + "/" + "\(self.cholesterolLimit)"
            
            //CARBOHYDRATES
            self._carbBar.progress = Float(self.dailyTotals["carbs"]!/self.carbLimit)
            self._carbLabel.text = "\(self.dailyTotals["carbs"]!)" + "/" + "\(self.carbLimit)"
            
            //FAT
            self._fatBar.progress = Float(self.dailyTotals["fat"]!/self.fatLimit)
            self._fatLabel.text = "\(self.dailyTotals["fat"]!)" + "/" + "\(self.fatLimit)"
        }
    }

    
    func setDate()
    {
        let date = Date()
        let calendar = Calendar.current
        let components = calendar.dateComponents([.year, .month, .day], from: date)

        let year =  components.year
        let month = components.month
        let day = components.day
        _dateLabel.text = "\(month!)" + "/" + "\(day!)" + "/" + "\(year!)"
        if self.traitCollection.userInterfaceStyle == .dark
        {
            //COLOR FOR DATE LABEL IF DARK MODE
        }
        else
        {
            //COLOR FOR DATE LABEL IF LIGHT MODE
        }
    }
    
    func getLimits()
    {
        let email = UserDefaults.standard.string(forKey: "email")
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/find/email/basic?email=" + email!
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
                self.calorieLimit = dictionary["calorieLimit"] as! Int
                self._calories.maxValue = CGFloat(self.calorieLimit)
            }
        }
        task.resume()
    }
    
    func getData()
    {
        let email = UserDefaults.standard.string(forKey: "email")
        let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/day/total?email=" + email!
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
                self.dailyTotals = dictionary as! Dictionary<String, Double>
            }
        }
        task.resume()
    }
}
