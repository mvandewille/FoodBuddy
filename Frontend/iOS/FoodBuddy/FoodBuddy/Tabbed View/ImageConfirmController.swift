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
    
    var image : UIImage? = nil
    
    var recognizedText = ""
    var textRecognitionRequest = VNRecognizeTextRequest()
    
    @IBAction func submit(_ sender: Any)
    {
        let handler = VNImageRequestHandler(cgImage: image!.cgImage!, options: [:])
        do {
            try handler.perform([textRecognitionRequest])
            
        } catch {
            print(error)
        }
        processText()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        _continueBtn.layer.cornerRadius = 5
        setupTextRecognition()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        _imageView.image = image
    }
    
    func cropImage(_ inputImage: UIImage, toRect cropRect: CGRect, viewWidth: CGFloat, viewHeight: CGFloat) -> UIImage?
    {
        let imageViewScale = max(inputImage.size.width / viewWidth,
                                 inputImage.size.height / viewHeight)

        // Scale cropRect to handle images larger than shown-on-screen size
        let cropZone = CGRect(x:cropRect.origin.x * imageViewScale,
                              y:cropRect.origin.y * imageViewScale,
                              width:cropRect.size.width * imageViewScale,
                              height:cropRect.size.height * imageViewScale)

        // Perform cropping in Core Graphics
        guard let cutImageRef: CGImage = inputImage.cgImage?.cropping(to:cropZone)
        else {
            return nil
        }

        // Return image to UIImage
        let croppedImage: UIImage = UIImage(cgImage: cutImageRef)
        return croppedImage
    }
    
    func setupTextRecognition()
    {
        textRecognitionRequest = VNRecognizeTextRequest(completionHandler: { (request, error) in
            if let results = request.results, !results.isEmpty {
                if let requestResults = request.results as? [VNRecognizedTextObservation] {
                    self.recognizedText = ""
                    for observation in requestResults {
                        guard let candidate = observation.topCandidates(1).first else { return }
                          self.recognizedText += candidate.string
                        self.recognizedText += "\n"
                    }
                }
            }
        })
        textRecognitionRequest.recognitionLevel = .accurate
        textRecognitionRequest.usesLanguageCorrection = true
    }
    
    func processText()
    {
        print(recognizedText)
    }
}
