//
//  CameraViewController.swift
//  iOS UI Mockup
//
//  Created by Max Van de Wille on 2/24/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit
import AVKit
import Vision

class CameraViewController : UIViewController, AVCaptureVideoDataOutputSampleBufferDelegate
{
    @IBOutlet weak var _capture: UIButton!
    @IBOutlet weak var _cameraView: UIView!
    @IBOutlet weak var _dailyViewBtn: UIButton!
    @IBOutlet weak var _globalChatBtn: UIButton!
    
    let captureSession = AVCaptureSession()
    
    let photoOutput = AVCapturePhotoOutput()
    
    var previewLayer = AVCaptureVideoPreviewLayer()
    
    var photoData : Data?
    
    var drawings : [CAShapeLayer] = []

    //MARK: - Submit Image
    @IBAction func snap(_ sender: Any) {
        let photoSettings = AVCapturePhotoSettings()
        photoSettings.flashMode = .auto
        photoOutput.capturePhoto(with: photoSettings, delegate: self)
    }
    
    //MARK: - Tab Bar Buttons
    @IBAction func sendToDaily(_ sender: Any) {
        self.tabBarController?.selectedIndex = 0
        self.tabBarController?.tabBar.isHidden = false
    }
    
    @IBAction func sendToChat(_ sender: Any) {
        self.tabBarController?.selectedIndex = 2
        self.tabBarController?.tabBar.isHidden = false
    }
    
    //MARK: - View Init/Deinit
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        captureSession.sessionPreset = .photo
        guard let captureDevice = AVCaptureDevice.default(for: .video) else { return }
        guard let input = try? AVCaptureDeviceInput(device: captureDevice) else { return }
        
        captureSession.addInput(input)
        captureSession.startRunning()
        
        previewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
        previewLayer.videoGravity = AVLayerVideoGravity.resizeAspectFill
        _cameraView.layer.addSublayer(previewLayer)
        previewLayer.frame = _cameraView.frame
        
        let dataOuput = AVCaptureVideoDataOutput()
        dataOuput.setSampleBufferDelegate(self, queue: DispatchQueue(label: "videoQueue"))
        captureSession.addOutput(dataOuput)
        captureSession.addOutput(photoOutput)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }
    
    //MARK: - Find Label
    func captureOutput(_ output: AVCaptureOutput, didOutput sampleBuffer: CMSampleBuffer, from connection: AVCaptureConnection) {
        
        guard let pixelBuffer : CVPixelBuffer = CMSampleBufferGetImageBuffer(sampleBuffer) else { return }
        
        guard let model = try? VNCoreMLModel(for: LabelFinder2().model) else { return }
        let request = VNCoreMLRequest(model: model) { (finishedReq, error) in
            print(finishedReq.results)
            
            guard let results = finishedReq.results as? [VNRecognizedObjectObservation] else { return }
            self.drawBoundingBoxes(results)
        }
        try? VNImageRequestHandler(cvPixelBuffer: pixelBuffer, options: [:]).perform([request])
    }
    
    //MARK: - Draw Bounding Boxes
    func drawBoundingBoxes(_ objects: [VNRecognizedObjectObservation])
    {
        self.clearDrawings()
        let facesBoundingBoxes: [CAShapeLayer] = objects.map({ (observedLabel: VNRecognizedObjectObservation) -> CAShapeLayer in
            let faceBoundingBoxOnScreen = self.previewLayer.layerRectConverted(fromMetadataOutputRect: observedLabel.boundingBox)
            let faceBoundingBoxPath = CGPath(rect: faceBoundingBoxOnScreen, transform: nil)
            let faceBoundingBoxShape = CAShapeLayer()
            faceBoundingBoxShape.path = faceBoundingBoxPath
            faceBoundingBoxShape.fillColor = UIColor.clear.cgColor
            faceBoundingBoxShape.strokeColor = UIColor.green.cgColor
            return faceBoundingBoxShape
        })
        facesBoundingBoxes.forEach({ faceBoundingBox in self.view.layer.addSublayer(faceBoundingBox) })
        self.drawings = facesBoundingBoxes
    }
    
    func clearDrawings() {
        self.drawings.forEach({ drawing in drawing.removeFromSuperlayer() })
    }
}

//MARK: - Camera Capture Delegate
extension CameraViewController : AVCapturePhotoCaptureDelegate
{    
    func photoOutput(_ output: AVCapturePhotoOutput, didFinishProcessingPhoto photo: AVCapturePhoto, error: Error?) {
        guard let data = photo.fileDataRepresentation() else { return }
        let image = UIImage(data: data)
        
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "ImageConfirm") as! ImageConfirmController
        vc.image = image
        self.navigationController?.pushViewController(vc, animated: true)
    }
}
