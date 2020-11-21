//
//  ViewController.swift
//  ShoppingList
//
//  Created by Keven Zarate on 2/17/20.
//  Copyright © 2020 Keven Zarate. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    //My Shopping List
    //Using a dictionary to store decription and quantity
    var userList = [String:Int]()
    var listArray: [String] = []
    
    
    //Dislplay outlets for values
    
    @IBOutlet var result_List: UILabel!
    @IBOutlet var descript_Text: UITextField!
    @IBOutlet var quantity_Text: UITextField!
    
    //UIControl
    @IBAction func quitKeyBoard (sender: UIControl){
        descript_Text.resignFirstResponder()
        quantity_Text.resignFirstResponder()
        
    }
    
    //UIButtons
    @IBAction func createListB(sender: UIButton) {
        
        listArray.removeAll()
        result_List.text = "No Item"
        descript_Text.text!.removeAll()
        quantity_Text.text!.removeAll()
    }
    
    @IBAction func newItemB(sender: UIButton) {
        listArray.removeAll()
        descript_Text.text!.removeAll()
        quantity_Text.text!.removeAll()
    }
    
    @IBAction func addItemB(sender: UIButton) {
        if(descript_Text.text == nil || descript_Text.text == "")
            && (quantity_Text.text == nil || quantity_Text.text == ""){
            addCheck()
        } else {
            addDisplay()
        }
    }
    
    
    //Functions
    func addDisplay(){
        listArray.append(quantity_Text.text! + "x " + descript_Text.text! + "\n")
        
        if(result_List.text! == "No Item"){
            
            result_List.text = ""
            result_List.text?.write(listArray.last!)
        } else {
            
            for items in listArray {
                
                result_List.text?.append(contentsOf:items)
         
            }
             print(listArray)
        }
    }
    
    
    func addCheck(){
        if(descript_Text.text == nil || descript_Text.text == "") && (quantity_Text.text == nil || quantity_Text.text == ""){
            
            let alertControl = UIAlertController(title:"Incomplete", message:"Description and Quantity not complete", preferredStyle: .alert
            )
            
            let alertCancel = UIAlertAction(title: "Done", style: .cancel, handler: nil)
            alertControl.addAction(alertCancel)
            present(alertControl, animated: true,completion: nil)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
}

