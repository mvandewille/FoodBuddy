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
        let strArr = messageStr.split(separator: ";")
        name = String(strArr[0])
        message = String(strArr[1])
        incoming = isIncoming
        if (strArr.count >= 3)
        {
            timestamp = String(strArr[2])
        }
        else
        {
            timestamp = ""
        }
    }
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

class GroupChatController : UIViewController
{
    @IBOutlet weak var _textField: UITextView!
    @IBOutlet weak var _submit: UIButton!
    @IBOutlet weak var _tableView: UITableView!
    @IBOutlet weak var textAreaBottom: NSLayoutConstraint!
    
    var messageArr : [Message] = []
    
    var socketConnection: URLSessionWebSocketTask?

    @IBAction func submitMsg(_ sender: Any)
    {
        let str = UserDefaults.standard.string(forKey: "email")! + ";" + _textField.text
        messageArr.append(Message(str, isIncoming: false))
        sendMessage(str)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        startObservingKeyboard()
        let appearance = UINavigationBarAppearance()
        appearance.shadowColor = .clear
        navigationController?.navigationBar.scrollEdgeAppearance = appearance
        title = "Global Chat"
        _tableView.dataSource = self
        _tableView.delegate = self
        connectToSocket()
        sendMessage(UserDefaults.standard.string(forKey: "email")!)
        setReceiveHandler()
    }

    override func viewWillDisappear(_ animated: Bool) {
        socketConnection?.cancel()
    }

    func connectToSocket()
    {
        let url = URL(string: "ws://coms-309-hv-3.cs.iastate.edu:4444")!
        socketConnection = URLSession.shared.webSocketTask(with: url)
        socketConnection?.resume()
    }

    func sendMessage(_ str : String)
    {
        let msg = URLSessionWebSocketTask.Message.string(str)

        socketConnection?.send(msg) { error in
            if let error = error {
                print(error)
            }
        }
    }

    func setReceiveHandler()
    {
        socketConnection?.receive { result in
            switch result {
            case .failure(let error):
                print("Failed to receive message: \(error)")
            case .success(let message):
                switch message {
                case .string(let text):
                    print("Received message: \(text)")
                case .data(let data):
                    print("Received data message: \(data)")
                @unknown default:
                    fatalError()
                }
                
                self.setReceiveHandler()
            }
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
    private func startObservingKeyboard() {
      let notificationCenter = NotificationCenter.default
      notificationCenter.addObserver(
        forName: UIResponder.keyboardWillShowNotification,
        object: nil,
        queue: nil,
        using: keyboardWillAppear)
      notificationCenter.addObserver(
        forName: UIResponder.keyboardWillHideNotification,
        object: nil,
        queue: nil,
        using: keyboardWillDisappear)
    }
    
    deinit {
      let notificationCenter = NotificationCenter.default
      notificationCenter.removeObserver(
        self,
        name: UIResponder.keyboardWillShowNotification,
        object: nil)
      notificationCenter.removeObserver(
        self,
        name: UIResponder.keyboardWillHideNotification,
        object: nil)
    }
    
    private func keyboardWillAppear(_ notification: Notification) {
      let key = UIResponder.keyboardFrameEndUserInfoKey
      guard let keyboardFrame = notification.userInfo?[key] as? CGRect else {
        return
      }
      
      let safeAreaBottom = view.safeAreaLayoutGuide.layoutFrame.maxY
      let viewHeight = view.bounds.height
      let safeAreaOffset = viewHeight - safeAreaBottom
      
      let lastVisibleCell = _tableView.indexPathsForVisibleRows?.last
      
      UIView.animate(
        withDuration: 0.3,
        delay: 0,
        options: [.curveEaseInOut],
        animations: {
          self.textAreaBottom.constant = -keyboardFrame.height + safeAreaOffset
          self.view.layoutIfNeeded()
          if let lastVisibleCell = lastVisibleCell {
            self._tableView.scrollToRow(
              at: lastVisibleCell, at: .bottom, animated: false)
          }
      })
    }
    
    private func keyboardWillDisappear(_ notification: Notification) {
      UIView.animate(
        withDuration: 0.3,
        delay: 0,
        options: [.curveEaseInOut],
        animations: {
          self.textAreaBottom.constant = 0
          self.view.layoutIfNeeded()
      })
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
        
        let message = messageArr[indexPath.row]
        
        if (message.incoming == true)
        {
            incomingCell = tableView.dequeueReusableCell(withIdentifier: "incomingMessageCell", for: indexPath) as! incomingMessageCell
            incomingCell._messageLabel.text = message.message
            incomingCell._messageBubble.layer.cornerRadius = 5
            incomingCell._profileBubble.layer.cornerRadius = 20
            incomingCell._profileBubble.backgroundColor = getNameColor(message.name)
            incomingCell._initialLabel.text = String(message.name[message.name.index(message.name.startIndex, offsetBy: 0)])
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
    _textField.textColor = .lightGray
  }
  
  private func removeTextViewPlaceholder() {
    _textField.text = ""
    _textField.textColor = .black
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
