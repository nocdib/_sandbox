//
//  ViewController.swift
//  BullsEye
//
//  Created by Joshua, Gregory on 3/12/19.
//  Copyright © 2019 NOCDIB, Inc. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    var sliderValue: Int = 0
    @IBOutlet weak var slider: UISlider!

    override func viewDidLoad() {
        super.viewDidLoad()
        sliderValue = lroundf(slider.value)
    }

    @IBAction func sliderMoved(_ slider: UISlider) {
        print("The value of the slider is \(sliderValue)")
    }
    @IBAction func showAlert() {
        let alert = UIAlertController(title: "Slider Value", message: "The slider value is \(sliderValue)", preferredStyle: .alert)
        let action = UIAlertAction(title: "OK", style: .default, handler: nil)
        alert.addAction(action)
        present(alert, animated: true, completion: nil)
    }
    
}

