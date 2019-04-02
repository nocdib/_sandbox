//
//  ViewController.swift
//  BullsEye
//
//  Created by Joshua, Gregory on 3/12/19.
//  Copyright © 2019 NOCDIB, Inc. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    // Initialize values
    var sliderValue: Int = 0
    var targetValue: Int = 0
    var roundValue: Int = 0
    var scoreValue: Int = 0
    @IBOutlet weak var slider: UISlider!
    @IBOutlet weak var targetLabel: UILabel!
    @IBOutlet weak var roundLabel: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Start a new game
        startNewRound(newGame: true)
        
        let thumbImageNormal = #imageLiteral(resourceName: "SliderThumb-Normal")
        slider.setThumbImage(thumbImageNormal, for: UIControl.State.normal)
        let thumbImageHighlighted = #imageLiteral(resourceName: "SliderThumb-Highlighted")
        slider.setThumbImage(thumbImageHighlighted, for: .highlighted)
        let insets = UIEdgeInsets(top: 0, left: 14, bottom: 0, right: 14)
        
        let trackLeftImage = #imageLiteral(resourceName: "SliderTrackRight")
        let trackLeftResizable = trackLeftImage.resizableImage(withCapInsets: insets)
        slider.setMinimumTrackImage(trackLeftResizable, for: .normal)
        let trackRightImage = #imageLiteral(resourceName: "SliderTrackLeft")
        let trackRightResizable = trackRightImage.resizableImage(withCapInsets: insets)
        slider.setMaximumTrackImage(trackRightResizable, for: .normal)
    }
    
    // Start a new round when false. Start a new game when true
    func startNewRound(newGame: Bool = false) {
        // Choose a random target value between 1 and 100, inclusive
        targetValue = 1 + Int(arc4random_uniform(100))
        targetLabel.text = String(targetValue)
        // Move the slider to the middle
        sliderValue = 50
        slider.value = Float(sliderValue)
        // In a new game reset the round count and score
        if (newGame) {
            roundValue = 1
            scoreValue = 0
        // In a new round increment the round count
        } else {
            roundValue += 1
        }
        roundLabel.text = String(roundValue)
    }
    
    @IBAction func sliderMoved(_ slider: UISlider) {
        // Conver the slider's Float value to an Integer
        sliderValue = lroundf(slider.value)
        //print("The value of the slider is \(sliderValue)")
    }
    
    @IBAction func showAlert() {
        var alertTitle: String
        var handlerAction: (Any) -> Void
        var buttonTitle: String
        let difference = abs(sliderValue - targetValue)
        scoreValue += difference
        // If the score is less than or equal to 10 then show a Next Round alert
        if (scoreValue <= 10) {
            alertTitle = "On To The Next Round"
            handlerAction = { action in self.startNewRound() }
            buttonTitle = "OK"
        // If the score is greater than 10 show a Game Over alert
        } else {
            alertTitle = "You Completed \(roundValue-1) Round"
            alertTitle += roundValue-1 == 1 ? "" : "s"
            handlerAction = { action in self.startNewRound(newGame: true) }
            buttonTitle = "New Game"
        }
        let alert = UIAlertController(title: alertTitle, message: "Slider: \(sliderValue)\nTarget: \(targetValue)\nScore: \(scoreValue)", preferredStyle: .alert)
        // Start a new round or game after the alert button is clicked.
        let action = UIAlertAction(title: buttonTitle, style: .default, handler: handlerAction)
        alert.addAction(action)
        present(alert, animated: true, completion: nil)
    }
    
    @IBAction func startNewGame() {
        self.startNewRound(newGame: true)
    }
}
