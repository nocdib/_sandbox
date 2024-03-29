//
//  ImageStore.swift
//  Homepwner
//
//  Created by Joshua, Gregory on 8/16/18.
//  Copyright © 2018 NOCDIB, Inc. All rights reserved.
//

import UIKit

class ImageStore {
    
    let cache = NSCache<NSString,UIImage>()
    
    func imageURL(forKey key: String) -> URL {
        let documentsDirectories = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let documentDirectory = documentsDirectories.first!
        return documentDirectory.appendingPathComponent(key)
    }
    
    func setImage(_ image: UIImage, forKey key: String){
        cache.setObject(image, forKey: key as NSString)
        // create full URL for image
        let url = imageURL(forKey: key)
        // turn image into JPEG data
        if let data = UIImageJPEGRepresentation(image, 0.5){
            // Write image data to URL
            try? data.write(to: url, options: [.atomic])
        }
    }
    
    func image(forKey key: String) -> UIImage? {
        if let existingImage = cache.object(forKey: key as NSString){
            return existingImage
        }
        let url = imageURL(forKey: key)
        guard let imageFromDisk = UIImage(contentsOfFile: url.path) else {
            return nil
        }
        cache.setObject(imageFromDisk, forKey: key as NSString)
        return imageFromDisk
    }
    
    func deleteImage(forKey key: String) {
        cache.removeObject(forKey: key as NSString)
        let url = imageURL(forKey: key)
        do{
            try FileManager.default.removeItem(at: url)
        } catch let deleteError{
            print("Error removing the image from disk: \(deleteError)")
        }
    }

}
