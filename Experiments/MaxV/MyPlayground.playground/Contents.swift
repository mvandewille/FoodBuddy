import Foundation

var socketConnection: URLSessionWebSocketTask?

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
            print("Sending error: \(error)")
        }
    }
}

func readMessage()
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
            
            readMessage()
        }
    }
}

func ping()
{
    socketConnection?.sendPing { (error) in
        if let error = error {
            print(error)
        }
        else
        {
            print("great success")
        }
    }
}

connectToSocket()
readMessage()
