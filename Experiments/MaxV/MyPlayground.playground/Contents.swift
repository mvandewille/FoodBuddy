//import UIKit
//import CryptoKit
//
//func DoLogin(_ email: String, _ pwd: String) {
//
//    let urlStr = "http://coms-309-hv-3.cs.iastate.edu:8080/user/auth?email=" + email + "&password=" + pwd
//    let url = URL(string: urlStr)
//    var request = URLRequest(url: url!)
//    request.httpMethod = "GET"
//
//    //send request and decode response if exists
//    let task = URLSession.shared.dataTask(with: request) { data, response, error in
//        guard let data = data, error == nil else {
//
//            print(error?.localizedDescription ?? "No data")
//            return
//        }
//        let responseJSON = try? JSONSerialization.jsonObject(with: data, options: [])
//        if let responseJSON = responseJSON as? [String: Any] {
//            print(responseJSON)
//        }
//    }
//    task.resume()
//}
//
//DoLogin("tdj1@iastate.edu", "password")
print("HELLO")
