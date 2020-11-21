//
//  DetailViewController.swift
//  Car_Detail_List
//
//  Created by Keven Zarate on 3/11/20.
//  Copyright © 2020 Keven Zarate. All rights reserved.
//

import UIKit

class DetailViewController: UIViewController {
    
    
    @IBOutlet weak var titleDisplay : UILabel!
    @IBOutlet weak var descriptionLabel : UILabel!
    
    var carInfromation: CarDetail?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
    }
    
    
    
    
    override func viewWillAppear(_ animated: Bool) {
        if let c = carInfromation {
            
            titleDisplay.text = c.name
            descriptionLabel.text = c.longInterestingFact
        }
    }
    
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destination.
     // Pass the selected object to the new view controller.
     }
     */
    
}
