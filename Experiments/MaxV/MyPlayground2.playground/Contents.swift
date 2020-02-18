import CryptoKit
import Foundation
import UIKit

var email = "tdj1@iastate.edu"
var password = "password"
var userDict = ["blank": email]

if let data = password.data(using: .utf8) {
    let hash = SHA512.hash(data: data)
    let hashStr = hash
    userDict = ["email": email, "password": String(hash)]
}

let jsonData = try! JSONSerialization.data(withJSONObject: userDict)
let jsonString = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue)

print(jsonString)
