//
//  ImageConfirmController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 4/17/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit
import VisionKit
import Vision

class ImageConfirmController : UIViewController
{
    @IBOutlet weak var _imageView: UIImageView!
    @IBOutlet weak var _continueBtn: UIButton!
    
    var image : UIImage?
    
    var croppedImage : UIImage?
    
    var box : CGRect?
    
    var recognizedText = ""
    var textRecognitionRequest = VNRecognizeTextRequest()
    
    //MARK: - Submit Button Action
    @IBAction func submit(_ sender: Any)
    {
        let handler = VNImageRequestHandler(cgImage: croppedImage!.cgImage!, options: [:])
        do {
            try handler.perform([textRecognitionRequest])
            
        } catch {
            print(error)
        }
        processText()
    }
    
    //MARK: - View Init/Deinit
    override func viewDidLoad() {
        super.viewDidLoad()
        _continueBtn.layer.cornerRadius = 5
        setupTextRecognition()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if (box != nil)
        {
            let cropBox = CGRect(x: box!.minX * (image?.size.height)!, y: box!.minY * image!.size.width, width: box!.width * (image?.size.width)!, height: box!.height * (image?.size.height)!)
            
            let newImage = UIImage(cgImage: (image?.cgImage?.cropping(to: cropBox))!).rotate(radians: .pi/2)
            
            _imageView.image = newImage
            croppedImage = newImage
            return
        }
        _imageView.image = image
    }
    
    //MARK: - Text Recognition
    func setupTextRecognition()
    {
        textRecognitionRequest = VNRecognizeTextRequest(completionHandler: { (request, error) in
            if let results = request.results, !results.isEmpty {
                if let requestResults = request.results as? [VNRecognizedTextObservation] {
                    self.recognizedText = ""
                    for observation in requestResults {
                        guard let candidate = observation.topCandidates(1).first else { return }
                        self.recognizedText += candidate.string
                        self.recognizedText += " "
                    }
                }
            }
        })
        textRecognitionRequest.recognitionLevel = .accurate
        textRecognitionRequest.usesLanguageCorrection = true
    }
    
    //MARK: - String Parsing
    func processText()
    {
        print(recognizedText)
        let newStr = recognizedText.uppercased()
        let newStr2 = newStr.components(separatedBy: .punctuationCharacters).joined()

        let strArr = newStr2.split(separator: " ")
        
        var foodDict : Dictionary<String, Any> = [:]
        
        var lookingFor = 0
        
        for word in strArr
        {
            //MARK: CALORIES
            if (word == "CALORIES")
            {
                lookingFor = 1
                continue
            }
            if (lookingFor == 1)
            {
                //check if word ends with g
                //get substring of everything before g and check if they are ints
                //assign int to value
                if (Int(word) != nil)
                {
                    foodDict["Calories"] = Int(word)
                    lookingFor = 0
                }
                else
                {
                    continue
                }
            }
            
            //MARK: SODIUM
            if (word == "SODIUM")
            {
                lookingFor = 2
                continue
            }
            if (lookingFor == 2)
            {
                //check if word ends with mg
                //get substring of everything before mg and check if they are ints
                //assign int to value
                if (word.suffix(2) == "MG")
                {
                    let tempIndex = word.index(word.endIndex, offsetBy: -2)
                    let tempStr = word[..<tempIndex]
                    if (Int(tempStr) != nil)
                    {
                        foodDict["Sodium"] = Int(tempStr)
                        lookingFor = 0
                    }
                    else
                    {
                        continue
                    }
                }
                else
                {
                    continue
                }
            }
            
            //MARK: CHOLESTEROL
            if (word == "CHOLESTEROL")
            {
                lookingFor = 3
                continue
            }
            if (lookingFor == 3)
            {
                //check if word ends with mg
                //get substring of everything before mg and check if they are ints
                //assign int to value
                if (word.suffix(2) == "MG")
                {
                    let tempIndex = word.index(word.endIndex, offsetBy: -2)
                    let tempStr = word[..<tempIndex]
                    if (Int(tempStr) != nil)
                    {
                        foodDict["Sodium"] = Int(tempStr)
                        lookingFor = 0
                    }
                    else
                    {
                        continue
                    }
                }
                else
                {
                    continue
                }
            }
            
            //MARK: PROTEIN
            if (word == "PROTEIN")
            {
                lookingFor = 4
                continue
            }
            if (lookingFor == 4)
            {
                //check if word ends with g
                //get substring of everything before mg and check if they are ints
                //assign int to value
                if (word.suffix(1) == "G")
                {
                    
                }
            }
            
            //FAT AND CARBOHYDRATES
            if (word == "TOTAL")
            {
                lookingFor = 5
                continue
            }
            if (lookingFor == 5)
            {
                if (word == "FAT")
                {
                    lookingFor = 6
                    continue
                }
                else if (word == "CARB." || word == "CARBOHYDRATE")
                {
                    lookingFor = 7
                    continue
                }
                else
                {
                    continue
                }
            }
            //MARK: FAT
            if (lookingFor == 6)
            {
                //check if word ends with g
                //get substring of everything before mg and check if they are ints
                //assign int to value
                if (word.suffix(1) == "G")
                {
                    
                }
            }
            //MARK: CARBOHYDRATES
            if (lookingFor == 7)
            {
                //check if word ends with g
                //get substring of everything before mg and check if they are ints
                //assign int to value
                if (word.suffix(1) == "G")
                {
                    
                }
            }
        }
    }
}

//MARK: - UIImage Rotate Ext.
extension UIImage {
    func rotate(radians: CGFloat) -> UIImage {
        let rotatedSize = CGRect(origin: .zero, size: size)
            .applying(CGAffineTransform(rotationAngle: CGFloat(radians)))
            .integral.size
        UIGraphicsBeginImageContext(rotatedSize)
        if let context = UIGraphicsGetCurrentContext() {
            let origin = CGPoint(x: rotatedSize.width / 2.0,
                                 y: rotatedSize.height / 2.0)
            context.translateBy(x: origin.x, y: origin.y)
            context.rotate(by: radians)
            draw(in: CGRect(x: -origin.y, y: -origin.x,
                            width: size.width, height: size.height))
            let rotatedImage = UIGraphicsGetImageFromCurrentImageContext()
            UIGraphicsEndImageContext()

            return rotatedImage ?? self
        }

        return self
    }
}

//MARK: - String Split Ext.
extension StringProtocol {
    var words: [SubSequence] {
        return split{ !$0.isLetter }
    }
}
