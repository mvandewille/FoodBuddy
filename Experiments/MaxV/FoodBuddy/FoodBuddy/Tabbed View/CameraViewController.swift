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
//
//    var captureSession: AVCaptureSession?
//    var videoPreviewLayer: AVCaptureVideoPreviewLayer?
//    var backCamera = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .back
//    )
//
//
//    override func viewDidLoad() {
//        super.viewDidLoad()
//
//        if #available(iOS 13, *)
//        {
//            let captureDevice = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .back)
//            do
//            {
//                let input = try AVCaptureDeviceInput(device: captureDevice!)
//                captureSession = AVCaptureSession()
//                captureSession?.addInput(input)
//                videoPreviewLayer = AVCaptureVideoPreviewLayer(session: captureSession!)
//                videoPreviewLayer?.frame = view.layer.bounds
//                _cameraView.layer.addSublayer(videoPreviewLayer!)
//                captureSession?.startRunning()
//            }
//            catch
//            {
//                print("womp womp")
//            }
//        }
//
//        _cameraView.topAnchor.constraint(equalTo: view.topAnchor).isActive = true
//        _cameraView.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
//        _cameraView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
//        _cameraView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
//    }
}
