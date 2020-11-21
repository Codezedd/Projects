//
//  ViewController.swift
//  kzarate_Assignment3
//
//  Created by Keven Zarate on 2/1/20.
//  Copyright © 2020 Keven Zarate. All rights reserved.
//

import UIKit




class ViewController: UIViewController {
    
    //Boolean value to determine when user is done pressing buttons
    var userPress = false
    //Array to store numbers pressed by user
    var num: [Int] = []
    
    @IBOutlet weak var results: UILabel!
    
    @IBAction func numPressed(_ sender: UIButton){
        
        let numberSelected = sender.titleLabel!.text
        
        if userPress{
            results.text = results.text! + numberSelected!
        
        } else {
            
            results.text = numberSelected
            userPress = true
        }
    }
    
    @IBAction func addNum(_ sender: UIButton) {
        
        if (results.text != ""){
            userPress = false
            num.append(Int(results.text!)!)
            results.text = "+"
        
        } else {
            userPress = true
    
        }
    }
    
    @IBAction func calculateAll(_ sender: UIButton) {
        
        if (results.text != ""){
            
            userPress = false
            var total = 0
            num.append(Int(results.text!)!)
            
            for values in num{
                total+=values
                
            }
            
            num.removeAll()
            results.text = "= \(total)"
            
        } else {
            userPress = true
        }
    }
    
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    
}

