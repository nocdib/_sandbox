//
//  ViewController.swift
//  Mandala
//
//  Created by Joshua, Gregory on 8/16/18.
//  Copyright © 2018 NOCDIB, Inc. All rights reserved.
//

import UIKit

extension UIColor {
    static let happy = UIColor(named: "happyTurquoise")!
}

class ViewController: UIViewController {

    @IBOutlet var img: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .happy
        img.image = UIImage(imageLiteralResourceName: "confused")
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

}

