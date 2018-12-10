//
//  ConversionViewController.swift
//  WorldTrotter
//
//  Created by Joshua, Gregory on 8/13/18.
//  Copyright © 2018 NOCDIB, Inc. All rights reserved.
//

import UIKit

class ConversionViewController: UIViewController, UITextFieldDelegate {
    @IBOutlet var celsius: UILabel!
    @IBOutlet var textField: UITextField!
    
    var fahrenheitValue: Measurement<UnitTemperature>?{
        didSet {
            updatedCelsiusLabel()
        }
    }
    
    var celsiusValue: Measurement<UnitTemperature>? {
        if let fahrenheitValue = fahrenheitValue{
            print("fahrenheitValue = \(fahrenheitValue)")
            return fahrenheitValue.converted(to: .celsius)
        }
        print("fahrenheitValue returning nil")
        return nil
    }
    
    func updatedCelsiusLabel() {
        if let celsiusValue = celsiusValue {
            let number = NSNumber(value: celsiusValue.value)
            print("celsiusValue.value = \(celsiusValue.value)")
            celsius.text = numberFormatter.string(from: number)
        } else {
            celsius.text = "???"
        }
    }
    
    let numberFormatter: NumberFormatter = {
        let nf = NumberFormatter()
        nf.locale = Locale.current
        nf.numberStyle = .decimal
        nf.minimumFractionDigits = 0
        nf.maximumFractionDigits = 1
        return nf
    }()

    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let decimalSeperator = NumberFormatter().decimalSeparator!
        let existingTextHasDecimalSeparator = textField.text?.range(of: decimalSeperator)
        let replacementTextHasDecimalSeparator = string.range(of: decimalSeperator)
        let allowableCharacters = "0123456789" + decimalSeperator
        let bannedCharacterSet = CharacterSet(charactersIn: allowableCharacters).inverted
        let replacementTextHasLetter = string.rangeOfCharacter(from: bannedCharacterSet)
        
        if existingTextHasDecimalSeparator != nil, replacementTextHasDecimalSeparator != nil{
            if replacementTextHasLetter != nil{
                return false
            }
            return false
        } else {
            if replacementTextHasLetter != nil{
                return false
            }
            let characterLimit = 5
            let newText = NSString(string: textField.text!).replacingCharacters(in: range, with: string)
            let numberOfChars = newText.count
            return numberOfChars < characterLimit
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        print("ConversionViewController has loaded its view")
        updatedCelsiusLabel()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        print("ConversionViewController did appear in the window")
    }
    
    @IBAction func fahrenheitFieldEditingChanged(_ textField: UITextField) {
        if let text = textField.text, let value = Double(text){
            fahrenheitValue = Measurement(value: value, unit: .fahrenheit)
        } else {
            fahrenheitValue = nil
        }
    }
    
    @IBAction func dismissKeyboard(_ sender: UITapGestureRecognizer) {
        textField.resignFirstResponder()
    }
    
    
}
