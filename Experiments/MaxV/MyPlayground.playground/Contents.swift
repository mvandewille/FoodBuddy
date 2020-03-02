func calculateCalories(_ gender: String,_ height: Int,_ weight: Int,_ lifestyle: String,_ age: Int) -> Int
{
    var limitMale: Double
    var limitFemale: Double
    var multiplier = 1.2
    if lifestyle == "Active"
    {
        multiplier = 1.9
    }
    else if lifestyle == "Inactive"
    {
        multiplier = 1.2
    }
    else if lifestyle == "Moderate"
    {
        multiplier = 1.55
    }
    limitMale = 66
    limitMale += (6.3 * Double(weight))
    limitMale += (12.9 * Double(height))
    limitMale -= (6.8 * Double(age))
    limitMale *= multiplier

    limitFemale = 665
    limitFemale += (4.3 * Double(weight))
    limitFemale += (4.7 * Double(height))
    limitFemale -= (4.7 * Double(age))

    if gender == "Male"
    {
        return Int(limitMale)
    }
    if gender == "Female"
    {
        return Int(limitFemale)
    }
    return Int((limitMale + limitFemale)/2)
}

let myLimit = calculateCalories("Male", 72, 165, "Moderate", 21)
print(myLimit)
