//
//  iOS_UI_MockupTests.swift
//  iOS UI MockupTests
//
//  Created by Max Van de Wille on 1/23/20.
//  Copyright © 2020 Max Van de Wille. All rights reserved.
//

import XCTest
@testable import FoodBuddy

class FoodBuddyTests: XCTestCase {

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testPullAccountInfo()
    {
        let accountSettingView = AccountSettingsController()
        accountSettingView.viewDidLoad()
        XCTAssert(accountSettingView.userDict != nil)
    }
    
//    func testAllergenSettings()
//    {
//        let allergenSettingController = AllergenSettingsController()
//        allergenSettingController.DoFieldCheck(email: "maxv1@iastate.edu")
//        let testArr = ["Eggs", "Milk", "Tree Nuts"]
//        XCTAssertEqual(allergenSettingController.userDict["allergens"] as! [String], testArr)
//    }
    
    func testCalorieCalculate()
    {
        let calTest = 2980
        let accountSettings = AdditionalUserInfoController()
        let calorieLimit = accountSettings.calculateCalories("Male", 72, 170, "Moderate", 21)
        XCTAssertEqual(calTest, calorieLimit)
    }
}
