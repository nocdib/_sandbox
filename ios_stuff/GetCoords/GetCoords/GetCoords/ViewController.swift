//
//  ViewController.swift
//  GetCoords
//
//  Created by Joshua, Gregory on 2/9/19.
//  Copyright © 2019 NOCDIB, Inc. All rights reserved.
//

import UIKit
import CoreLocation


class ViewController: UIViewController, CLLocationManagerDelegate {
    
    let locationManager = CLLocationManager()
    var latitude = ""
    var longitude = ""

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        // Get Permissions
        self.locationManager.requestAlwaysAuthorization()
        self.locationManager.requestWhenInUseAuthorization()
        
        if CLLocationManager.locationServicesEnabled() {
            locationManager.delegate = self
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.startUpdatingLocation()
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let locationValue : CLLocationCoordinate2D = manager.location?.coordinate else {
            return
        }
        latitude = String(locationValue.latitude)
        longitude = String(locationValue.longitude)
        print("Lat: \(latitude) Lon: \(longitude)")
    }

}

