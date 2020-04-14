import Foundation

var socketConnection: URLSessionWebSocketTask?

let url = URL(string: "http://coms-309-hv-3.cs.iastate.edu:4444")!
socketConnection = URLSession.shared.webSocketTask(with: url)
socketConnection?.resume()

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
        defer { setReceiveHandler() }

        do {
            let message = try result.get()
            switch message {
            case let .string(string):
                print(string)
                //TODO - Handle string
            case let .data(data):
                print(data)
            @unknown default:
                print("Unknown message type")
            }
        }   catch {
            print(error)
        }
    }
}

connectToSocket()
