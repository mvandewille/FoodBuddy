//
//  SettingsController.swift
//  FoodBuddy
//
//  Created by Max Van de Wille on 3/1/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import Foundation
import UIKit

class SettingsController: UIViewController
{
    //MARK: Variables
    @IBOutlet weak var _settingsTable: UITableView!
    
    private var settingsArray = ["Account Settings", "Following", "Allergen Settings"]
    
    //MARK: View Init/Deinit
    override func viewDidLoad() {
        super.viewDidLoad()
        _settingsTable.dataSource = self
        _settingsTable.delegate = self
    }
    
    @IBAction func unwindToSettingsController(segue: UIStoryboardSegue)
    {
        print("Unwinding from account settings")
    }
}

//MARK: Table View Extension
extension SettingsController: UITableViewDelegate, UITableViewDataSource
{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return settingsArray.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: UITableViewCell.CellStyle.default, reuseIdentifier: "cell")
        cell.textLabel?.text = settingsArray[indexPath.row]
        return cell
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        if (settingsArray[indexPath.row] == "App Settings")
        {
            self.performSegue(withIdentifier: "appSettings", sender: nil)
        }
        if (settingsArray[indexPath.row] == "Account Settings")
        {
            self.performSegue(withIdentifier: "accountSettings", sender: nil)
        }
        if (settingsArray[indexPath.row] == "Following")
        {
            self.performSegue(withIdentifier: "friendsSettings", sender: nil)
        }
        if (settingsArray[indexPath.row] == "Allergen Settings")
        {
            self.performSegue(withIdentifier: "allergenSettings", sender: nil)
        }
    }
}
