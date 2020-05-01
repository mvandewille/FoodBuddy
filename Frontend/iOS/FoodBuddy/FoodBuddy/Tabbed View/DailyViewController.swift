//
//  DailyViewController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 3/10/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class DailyViewController : UIViewController
{
    //MARK: Variables
    @IBOutlet weak var _dateLabel: UILabel!
    
    //CALORIE RING VIEW
    @IBOutlet weak var _calorieView: UIView!
    let shapeLayer = CAShapeLayer()
    let basicAnimation = CABasicAnimation(keyPath: "strokeEnd")
    
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
    let sodiumLimit : Double = 2500
    let cholesterolLimit : Double = 300
    
    
    //MARK: View Init/Deinit
    override func viewDidLoad() {
        getLimits()
        getData()
        setDate()
        createCaloriering()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        DispatchQueue.main.async {
            //CALORIE RING
            self.basicAnimation.toValue = (self.dailyTotals["calories"] ?? 0)/Double(self.calorieLimit)
            self.basicAnimation.duration = 1
            self.basicAnimation.fillMode = .forwards
            self.basicAnimation.isRemovedOnCompletion = false
            self.shapeLayer.add(self.basicAnimation, forKey: "basicBitch")
            
            //PROTEIN BAR
            self._proteinBar.progress = Float(self.dailyTotals["protein"]!/self.proteinLimit)
            self._proteinBar.tintColor = UIColor(rgb: 0xE6744F)
            self._proteinLabel.text = "\(Int(ceil(self.dailyTotals["protein"]!)))" + "/" + "\(Int(self.proteinLimit))" + "g"
            
            //SODIUM BAR
            self._sodiumBar.progress = Float(self.dailyTotals["sodium"]!/self.sodiumLimit)
            self._sodiumBar.tintColor = UIColor(rgb: 0x36DFC8)
            self._sodiumLabel.text = "\(Int(ceil(self.dailyTotals["sodium"]!)))" + "/" + "\(Int(self.sodiumLimit))" + "mg"
            
            //CHOLESTEROL BAR
            self._cholesterolBar.progress = Float(self.dailyTotals["cholesterol"]!/self.cholesterolLimit)
            self._cholesterolBar.tintColor = UIColor(rgb: 0x4FD75D)
            self._cholesterolLabel.text = "\(Int(ceil(self.dailyTotals["cholesterol"]!)))" + "/" + "\(Int(self.cholesterolLimit))" + "mg"
            
            //CARBOHYDRATES BAR
            self._carbBar.progress = Float(self.dailyTotals["carbs"]!/self.carbLimit)
            self._carbBar.tintColor = UIColor(rgb: 0xFFD400)
            self._carbLabel.text = "\(Int(ceil(self.dailyTotals["carbs"]!)))" + "/" + "\(Int(self.carbLimit))" + "g"
            
            //FAT BAR
            self._fatBar.progress = Float(self.dailyTotals["fat"]!/self.fatLimit)
            self._fatBar.tintColor = UIColor(rgb: 0xB373E3)
            self._fatLabel.text = "\(Int(ceil(self.dailyTotals["fat"]!)))" + "/" + "\(Int(self.fatLimit))" + "g"
        }
    }

    //MARK: Custom Calorie Ring
    func createCaloriering()
    {
        let center = _calorieView.center
        let circularPath = UIBezierPath(arcCenter: center, radius: 75, startAngle: -CGFloat.pi/2, endAngle: 1.5 * CGFloat.pi, clockwise: true)
        let trackLayer = CAShapeLayer()
        
        trackLayer.path = circularPath.cgPath
        trackLayer.strokeColor = UIColor.placeholderText.cgColor
        trackLayer.lineWidth = 20
        trackLayer.fillColor = UIColor.clear.cgColor
        trackLayer.lineCap = .round
        trackLayer.strokeEnd = 1.0
        view.layer.addSublayer(trackLayer)
        
        shapeLayer.path = circularPath.cgPath
        shapeLayer.strokeColor = UIColor(rgb: 0x5195FF).cgColor
        shapeLayer.lineWidth = 20
        shapeLayer.fillColor = UIColor.clear.cgColor
        shapeLayer.lineCap = .round
        shapeLayer.strokeEnd = 0
        view.layer.addSublayer(shapeLayer)
    }
    
    //MARK: Create Date Label
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
    
    //MARK: Get Calorie Limit
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
                //set value for calorie bar
            }
        }
        task.resume()
    }
    
    //MARK: Get Daily Total HTTP
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
