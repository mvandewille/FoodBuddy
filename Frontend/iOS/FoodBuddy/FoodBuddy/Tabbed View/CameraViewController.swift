//
//  CameraViewController.swift
//  iOS UI Mockup
//
//  Created by Max Van de Wille on 2/24/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit
import ARKit
import Vision

class CameraViewController : UIViewController, ARSCNViewDelegate
{
    @IBOutlet weak var _capture: UIButton!
    @IBOutlet weak var _cameraView: ARSCNView!
    @IBOutlet weak var _dailyViewBtn: UIButton!
    @IBOutlet weak var _globalChatBtn: UIButton!
    
    // Displayed rectangle outline
    private var selectedRectangleOutlineLayer: CAShapeLayer?
    
    // Observed rectangle currently being touched
    private var selectedRectangleObservation: VNRectangleObservation?
    
    // The time the current rectangle selection was last updated
    private var selectedRectangleLastUpdated: Date?
    
    // Current touch location
    private var currTouchLocation: CGPoint?
    
    // Gets set to true when actively searching for rectangles in the current frame
    private var searchingForRectangles = false

    //MARK: - Submit Image
    @IBAction func snap(_ sender: Any) {
        let currentFrame = _cameraView.snapshot()
        let vc = self.storyboard?.instantiateViewController(identifier: "ImageConfirm") as! ImageConfirmController
        vc.image = currentFrame
        self.navigationController?.pushViewController(vc, animated: true)
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
        let configuration = ARWorldTrackingConfiguration()
        _cameraView.session.run(configuration)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        _cameraView.delegate = self
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }
    
    
    // MARK: - Touch Delegates
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        guard let touch = touches.first,
            let currentFrame = _cameraView.session.currentFrame else {
            return
        }
        
        currTouchLocation = touch.location(in: _cameraView)
        findRectangle(locationInScene: currTouchLocation!, frame: currentFrame)
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        // Ignore if we're currently searching for a rect
        if searchingForRectangles {
            return
        }
        
        guard let touch = touches.first,
            let currentFrame = _cameraView.session.currentFrame else {
                return
        }
        
        currTouchLocation = touch.location(in: _cameraView)
        findRectangle(locationInScene: currTouchLocation!, frame: currentFrame)
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        currTouchLocation = nil

        guard let selectedRect = selectedRectangleObservation else {
            return
        }
    }
    
    // MARK: - AR Session
    func session(_ session: ARSession, didUpdate frame: ARFrame) {
        if searchingForRectangles {
            return
        }
        
        guard let currTouchLocation = currTouchLocation,
            let currentFrame = _cameraView.session.currentFrame else {
                return
        }
        
        if selectedRectangleLastUpdated?.timeIntervalSinceNow ?? 0 < 1 {
            return
        }
        
        findRectangle(locationInScene: currTouchLocation, frame: currentFrame)
    }
    
    //MARK: - Find Rectangle
    private func findRectangle(locationInScene location: CGPoint, frame currentFrame: ARFrame)
    {
        searchingForRectangles = true
        selectedRectangleObservation = nil
        
        DispatchQueue.global(qos: .background).async {
            let request = VNDetectRectanglesRequest(completionHandler: { (request, error) in
                
                DispatchQueue.main.async {
                    self.searchingForRectangles = false
                    
                    guard let observations = request.results as? [VNRectangleObservation],
                        let _ = observations.first else {
                            print("No results found in this area")
                            return
                        }
                    
                    print("\(observations.count) rectangles found in this location")
                    
                    if let layer = self.selectedRectangleOutlineLayer {
                        layer.removeFromSuperlayer()
                        self.selectedRectangleOutlineLayer = nil
                    }
                    
                    guard let selectedRect = observations.filter({ (result) -> Bool in
                        let convertedRect = self._cameraView.convertFromCamera(result.boundingBox)
                        return convertedRect.contains(location)
                    }).first else {
                        print("No results at touch location")
                        return
                    }
                    
                    // Outline selected rectangle
                    let points = [selectedRect.topLeft, selectedRect.topRight, selectedRect.bottomRight, selectedRect.bottomLeft]
                    let convertedPoints = points.map { self._cameraView.convertFromCamera($0) }
                    self.selectedRectangleOutlineLayer = self.drawPolygon(convertedPoints, color: UIColor.red)
                    self._cameraView.layer.addSublayer(self.selectedRectangleOutlineLayer!)
                    
                    // Track the selected rectangle and when it was found
                    self.selectedRectangleObservation = selectedRect
                    self.selectedRectangleLastUpdated = Date()
                }
            })
            
            request.maximumObservations = 0
            
            let handler = VNImageRequestHandler(cvPixelBuffer: currentFrame.capturedImage, options: [:])
            try? handler.perform([request])
        }
    }
    
    //MARK: - Draw outline
    private func drawPolygon(_ points: [CGPoint], color: UIColor) -> CAShapeLayer {
        let layer = CAShapeLayer()
        layer.fillColor = nil
        layer.strokeColor = color.cgColor
        layer.lineWidth = 2
        let path = UIBezierPath()
        path.move(to: points.last!)
        points.forEach { point in
            path.addLine(to: point)
        }
        layer.path = path.cgPath
        return layer
    }
}
