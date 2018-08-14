/*
    ViewController.swift
    SimpleApp
    Created by Joshua, Gregory on 8/13/18.
    Copyright © 2018 NOCDIB, Inc. All rights reserved.
 
    First Big Nerd Ranch exercise. An app that cycles through 3 questions an displays the matching answers.
*/

import UIKit

class ViewController: UIViewController {
    
    @IBOutlet var questionLabel: UILabel!
    @IBOutlet var answerLabel: UILabel!
    // Start at the first array index
    var currentIndex: Int = 0
    // Array of questions
    let questions = ["From what is Cognac made?",
                     "What is 9 * 9?",
                     "What is the capital of Vermont?"]
    // Matching array of answers
    let answers = ["Grapes",
                   "81",
                   "Montpelier"]
    // String to mask the answer
    let answerMask = "???"

    override func viewDidLoad() {
        super.viewDidLoad()
        startQuiz()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    // ENTRY POINT
    func startQuiz() {
        // Display the first question
        questionLabel.text = questions[currentIndex]
    }
    
    // When the user clicks the "Next Question" button
    @IBAction func nextQuestion(_ sender: UIButton) {
        // Increment the array index
        currentIndex = (currentIndex + 1) % questions.count
        // Display the new question
        questionLabel.text = questions[currentIndex]
        // Mask the new answer
        answerLabel.text = answerMask
    }
    // Unmask the answer
    @IBAction func showAnswer(_ sender: UIButton) {
        answerLabel.text = answers[currentIndex]
    }

}

