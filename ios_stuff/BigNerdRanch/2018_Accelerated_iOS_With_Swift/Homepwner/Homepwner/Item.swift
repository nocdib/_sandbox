//
//  Item.swift
//  Homepwner
//
//  Created by Joshua, Gregory on 8/15/18.
//  Copyright © 2018 NOCDIB, Inc. All rights reserved.
//

import UIKit

class Item: NSObject, NSCoding {
    var name: String
    var valueInDollars: Double
    var serialNumber: String?
    var dateCreated: Date
    var itemKey: String
    
    init(name: String, serialNumber: String?, valueInDollars: Double) {
        self.name = name
        self.valueInDollars = valueInDollars
        self.serialNumber = serialNumber
        self.dateCreated = Date()
        self.itemKey = UUID().uuidString
        super.init()
    }
    
    required init?(coder aDecoder: NSCoder) {
        name = aDecoder.decodeObject(forKey: "name") as! String
        itemKey = aDecoder.decodeObject(forKey: "itemKey") as! String
        dateCreated = aDecoder.decodeObject(forKey: "dateCreated") as! Date
        serialNumber = aDecoder.decodeObject(forKey: "serialNumber") as! String?
        valueInDollars = aDecoder.decodeObject(forKey: "valueInDollars") as? Double ?? 0.00
        super.init()
    }
    
    convenience init(random: Bool = false) {
        if random {
            let adjectives = ["Dirty", "Hairy", "Shiny", "Ugly", "Large", "Small" ]
            let nouns = ["Pillow", "Table", "Pot", "Chair", "Bed", "Curtains", "Rug"]
            
            var index = arc4random_uniform(UInt32(adjectives.count))
            let randomAdjective = adjectives[Int(index)]
            
            index = arc4random_uniform(UInt32(nouns.count))
            let randomNoun = nouns[Int(index)]
            
            let randomName = "\(randomAdjective) \(randomNoun)"
            let randomValue = Double(arc4random_uniform(100))
            let randomSerialNumber = UUID().uuidString.components(separatedBy: "-").first!
            
            self.init(name: randomName, serialNumber: randomSerialNumber, valueInDollars: randomValue)
        } else {
            self.init(name: "", serialNumber: nil, valueInDollars: 0.00)
        }
    }
    
    func encode(with aCoder: NSCoder) {
        aCoder.encode(name, forKey: "name")
        aCoder.encode(itemKey, forKey: "itemKey")
        aCoder.encode(dateCreated, forKey: "dateCreated")
        aCoder.encode(serialNumber, forKey: "serialNumber")
        aCoder.encode(valueInDollars, forKey: "valueInDollars")
    }
    
    
    
}
