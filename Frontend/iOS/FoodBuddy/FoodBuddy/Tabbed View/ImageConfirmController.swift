//
//  ImageConfirmController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 4/17/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class ImageConfirmController : UIViewController
{
    @IBOutlet weak var _imageView: UIImageView!
    @IBOutlet weak var _continueBtn: UIButton!
    
    var image : UIImage? = nil
    
    @IBAction func submit(_ sender: Any)
    {
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        _continueBtn.layer.cornerRadius = 5
        _imageView.image = image
    }
    
    func findLabel()
    {
        
    }
    
    func crop()
    {
        
    }
}
