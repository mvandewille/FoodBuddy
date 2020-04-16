//
//  CameraViewController.swift
//  iOS UI Mockup
//
//  Created by Max Van de Wille on 2/24/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit
import AVFoundation

class CameraViewController : UIViewController
{
    @IBOutlet weak var _capture: UIButton!
    @IBOutlet weak var _cameraView: UIView!
    @IBOutlet weak var _dailyViewBtn: UIButton!
    @IBOutlet weak var _globalChatBtn: UIButton!
    
    @IBAction func sendToDaily(_ sender: Any) {
        self.tabBarController?.selectedIndex = 0
        self.tabBarController?.tabBar.isHidden = false
    }
    
    @IBAction func sendToChat(_ sender: Any) {
        self.tabBarController?.selectedIndex = 2
        self.tabBarController?.tabBar.isHidden = false
    }
    
    private(set) lazy var cameraLayer : AVCaptureVideoPreviewLayer = AVCaptureVideoPreviewLayer(session: self.captureSession)
    
    private lazy var captureSession : AVCaptureSession = {
        let session = AVCaptureSession()
        session.sessionPreset = AVCaptureSession.Preset.photo
        
        guard
            let backCamera = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .back),
            let input = try? AVCaptureDeviceInput(device: backCamera)
        else {
          return session
        }
        
        session.addInput(input)
        return session
    }()

    override func viewDidLoad() {
        super.viewDidLoad()
        self.tabBarController?.tabBar.isHidden = true
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }
}
