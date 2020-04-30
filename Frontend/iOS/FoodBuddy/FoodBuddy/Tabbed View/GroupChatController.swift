//
//  StatusFeedController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 3/15/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

struct Message
{
    var name : String
    var message : String
    var incoming : Bool
    var timestamp : String
}

// MARK: - Cell Objects
extension Message
{
    init(_ messageStr: String, isIncoming: Bool)
    {
        incoming = isIncoming
        if isIncoming == true
        {
            let strArr = messageStr.split(separator: ";")
            name = String(strArr[0])
            message = String(strArr[1])
            timestamp = String(strArr[2])
            return
        }
        name = UserDefaults.standard.string(forKey: "userName") as! String
        message = messageStr
        incoming = false
        let date = Date() // save date, so all components use the same date
        let calendar = Calendar.current // or e.g. Calendar(identifier: .persian)
        timestamp = String(calendar.component(.hour, from: date)) + ":" + String(calendar.component(.minute, from: date))
    }
}

class connectedUserCell : UICollectionViewCell
{
    @IBOutlet weak var _profileImage: UIView!
    @IBOutlet weak var _initialLabel: UILabel!
    
}

class incomingMessageCell : UITableViewCell
{
    @IBOutlet weak var _profileBubble: UIView!
    @IBOutlet weak var _messageLabel: UILabel!
    @IBOutlet weak var _initialLabel: UILabel!
    @IBOutlet weak var _messageBubble: UIView!
}

class outgoingMessageCell : UITableViewCell
{
    @IBOutlet weak var _profileBubble: UIView!
    @IBOutlet weak var _messageLabel: UILabel!
    @IBOutlet weak var _initialLabel: UILabel!
    @IBOutlet weak var _messageBubble: UIView!
}

class serverMessageCell : UITableViewCell
{
    @IBOutlet weak var _joinLabel: UILabel!
}

class GroupChatController : UIViewController
{
    
    //MARK: Variables
    @IBOutlet weak var _textField: UITextView!
    @IBOutlet weak var _submit: UIButton!
    @IBOutlet weak var _collectionView: UICollectionView!
    @IBOutlet weak var _tableView: UITableView!
    @IBOutlet weak var textAreaBottom: NSLayoutConstraint!
    
    var messageArr : [Message] = []
    
    var userArr : [String] = []
    
    var isConnected = false
    
    var socketConnection = URLSession.shared.webSocketTask(with: URL(string: "ws://coms-309-hv-3.cs.iastate.edu:8080/chat/")!)

    //MARK: Send Message Btn Action
    @IBAction func submitMsg(_ sender: Any)
    {
        let str = _textField.text
        if (str == "Type something...")
        {
            return
        }
        messageArr.append(Message(str!, isIncoming: false))
        sendMessage(str!)
        DispatchQueue.main.async {
            self._tableView.reloadData()
        }
        self.addTextViewPlaceholer()
        self.view.endEditing(true)
    }

    //MARK: View Init/Deinit
    override func viewDidLoad() {
        super.viewDidLoad()
        let name = UserDefaults.standard.string(forKey: "userName")!.replacingOccurrences(of: " ", with: "%20")
        let urlTemp = URL(string: "ws://coms-309-hv-3.cs.iastate.edu:8080/chat/" + name)!
        socketConnection = URLSession.shared.webSocketTask(with: urlTemp)
        self.navigationController?.navigationBar.setBackgroundImage(UIImage(), for: UIBarMetrics.default)
        self.navigationController?.navigationBar.shadowImage = UIImage()
        self.navigationController?.navigationBar.layoutIfNeeded()
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillShow), name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillHide), name: UIResponder.keyboardWillHideNotification, object: nil)
        let tap = UITapGestureRecognizer(target: self.view, action: #selector(UIView.endEditing(_:)))
        tap.cancelsTouchesInView = false
        view.addGestureRecognizer(tap)
        title = "Global Chat"
        _tableView.dataSource = self
        _tableView.delegate = self
        connectToServer()
        receiveMessage()
        _textField.delegate = self
        addTextViewPlaceholer()
        _collectionView.delegate = self
        _collectionView.dataSource = self
        ping()
    }

    override func viewWillDisappear(_ animated: Bool)
    {
        socketConnection.cancel()
    }
    
    
    //MARK: Websocket Functions
    func receiveMessage()
    {
        socketConnection.receive { result in
            switch result {
            case .failure(let error):
                print("Failed to receive message: \(error)")
            case .success(let message):
                switch message {
                case .string(let text):
                    print("Received text message: \(text)")
                    let msg = Message(text, isIncoming: true)
                    if (msg.name == "server")
                    {
                        self.processUserArr(text)
                    }
                    self.messageArr.append(msg)
                    DispatchQueue.main.async {
                        self._tableView.reloadData()
                        self._collectionView.reloadData()
                    }
                case .data(let data):
                    print("Received binary message: \(data)")
                @unknown default:
                    fatalError()
                }
                self.receiveMessage()
            }
        }
    }
    
    func connectToServer()
    {
        socketConnection.resume()
    }
    
    func sendMessage(_ str : String)
    {
        let msg = URLSessionWebSocketTask.Message.string(str)
        socketConnection.send(msg) { error in
            if let error = error {
                print("Websocket sending error: \(error)")
            }
        }
    }
    
    func ping() {
        socketConnection.sendPing { (error) in
            if let error = error {
                print("Ping failed: \(error)")
            }
            print("pinged")
            sleep(5)
            self.ping()
        }
    }
    
    //MARK: User Icon Processing
    func processUserArr(_ message : String)
    {
        let strArr = message.split(separator: ";")
        var i = 0
        userArr = []
        for string in strArr
        {
            if (i >= 3)
            {
                userArr.append(String(string))
            }
            i += 1
        }
    }
    
    func getNameColor(_ name : String) -> UIColor
    {
        var total: Int = 0
        for u in name.unicodeScalars {
            total += Int(UInt32(u))
        }
        
        srand48(total * 200)
        let r = CGFloat(drand48())
        
        srand48(total)
        let g = CGFloat(drand48())
        
        srand48(total / 200)
        let b = CGFloat(drand48())
        
        return UIColor(red: r, green: g, blue: b, alpha: 1)
    }
    
    // MARK: - Keyboard
    @objc func keyboardWillShow(notification: NSNotification) {
        if let keyboardSize = (notification.userInfo?[UIResponder.keyboardFrameBeginUserInfoKey] as? NSValue)?.cgRectValue {
            self.tabBarController?.tabBar.isHidden = true
            self.view.frame.origin.y = 0 - keyboardSize.height
        }
    }

    @objc func keyboardWillHide(notification: NSNotification) {
        self.tabBarController?.tabBar.isHidden = false
        self.view.frame.origin.y = 0
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        super.touchesBegan(touches, with: event)
    }
    
}

// MARK: - Collection Delegate

extension GroupChatController : UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout
{
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return userArr.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = _collectionView.dequeueReusableCell(withReuseIdentifier: "connectedUserCell", for: indexPath) as! connectedUserCell
        let name = userArr[indexPath.row]
        cell._profileImage.layer.cornerRadius = 20
        cell._profileImage.backgroundColor = getNameColor(userArr[indexPath.row])
        cell._initialLabel.text = name[name.index(name.startIndex, offsetBy: 0)].uppercased()
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return 0.0
    }
       
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
           
        return CGSize(width: 60, height: 60)
    }
       
       
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 0.0
    }
}

// MARK: - Table Delegate
extension GroupChatController: UITableViewDataSource, UITableViewDelegate
{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return messageArr.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell
    {
        var incomingCell : incomingMessageCell
        var outgoingCell : outgoingMessageCell
        var serverCell : serverMessageCell
        
        let message = messageArr[indexPath.row]
        if (message.name == "server")
        {
            serverCell = tableView.dequeueReusableCell(withIdentifier: "serverMessageCell", for: indexPath) as! serverMessageCell
            serverCell._joinLabel.text = message.message
            return serverCell
        }
        if (message.incoming == true)
        {
            incomingCell = tableView.dequeueReusableCell(withIdentifier: "incomingMessageCell", for: indexPath) as! incomingMessageCell
            incomingCell._messageLabel.text = message.message
            incomingCell._messageBubble.layer.cornerRadius = 5
            incomingCell._profileBubble.layer.cornerRadius = 20
            incomingCell._profileBubble.backgroundColor = getNameColor(message.name)
            incomingCell._initialLabel.text = String(message.name[message.name.index(message.name.startIndex, offsetBy: 0)]).uppercased()
            return incomingCell
        }
        else
        {
            outgoingCell = tableView.dequeueReusableCell(withIdentifier: "outgoingMessageCell", for: indexPath) as! outgoingMessageCell
            outgoingCell._messageLabel.text = message.message
            outgoingCell._messageBubble.layer.cornerRadius = 5
            outgoingCell._profileBubble.layer.cornerRadius = 20
            outgoingCell._profileBubble.backgroundColor = getNameColor(message.name)
            outgoingCell._initialLabel.text = String(message.name[message.name.index(message.name.startIndex, offsetBy: 0)])
            return outgoingCell
        }
    }
}

// MARK: - Text Delegate
extension GroupChatController: UITextViewDelegate {
  private func addTextViewPlaceholer() {
    _textField.text = "Type something..."
    _textField.textColor = .placeholderText
  }
  
  private func removeTextViewPlaceholder() {
    _textField.text = ""
    _textField.textColor = .label
  }
  
  func textViewDidBeginEditing(_ textView: UITextView) {
    removeTextViewPlaceholder()
  }
  
  func textViewDidEndEditing(_ textView: UITextView) {
    if textView.text.isEmpty {
      addTextViewPlaceholer()
    }
  }
}
