//
//  ViewController.swift
//  Remote_Control
//
//  Created by Keven Zarate on 2/16/20.
//  Copyright © 2020 Keven Zarate. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    //Text labels
    @IBOutlet weak var Power_Label: UILabel!
    @IBOutlet weak var Power_Switch: UISwitch!
    
    @IBOutlet weak var Volume_Slider: UISlider!
    @IBOutlet weak var Volume_Label: UILabel!
    
    @IBOutlet weak var Channel_Num: UILabel!
    
    @IBOutlet weak var Favorites_Segment: UISegmentedControl!
    
    @IBOutlet weak var Zero_Button: UIButton!
    @IBOutlet weak var One_Button: UIButton!
    @IBOutlet weak var Two_Button: UIButton!
    @IBOutlet weak var Three_Button: UIButton!
    @IBOutlet weak var Four_Button: UIButton!
    @IBOutlet weak var Five_Button: UIButton!
    @IBOutlet weak var Six_Button: UIButton!
    @IBOutlet weak var Seven_Button: UIButton!
    @IBOutlet weak var Eight_Button: UIButton!
    @IBOutlet weak var Nine_Button: UIButton!
    @IBOutlet weak var Channel_Plus: UIButton!
    @IBOutlet weak var Channel_Minus:UIButton!
    
    
    
    //Functions:
    @IBAction func VolumeSlider_Action(_ sender: UISlider) {
        
        Volume_Label.text = "\(Int(Volume_Slider.value))"
    }
    //Power Button Function
    //Enables and disbales all buttons and slider
    @IBAction func Power_Touch(_ sender: UISwitch) {
        
        if Power_Switch.isOn {
            Power_Label.text = "OFF"
            Power_Switch.setOn(false, animated: true)
            
            Volume_Slider.isEnabled = false
            Favorites_Segment.isEnabled = false
            
            Zero_Button.isEnabled = false
            One_Button.isEnabled = false
            Two_Button.isEnabled = false
            Three_Button.isEnabled = false
            Four_Button.isEnabled = false
            Five_Button.isEnabled = false
            Six_Button.isEnabled = false
            Seven_Button.isEnabled = false
            Eight_Button.isEnabled = false
            Nine_Button.isEnabled = false
            Channel_Plus.isEnabled = false
            Channel_Minus.isEnabled = false
            
        } else {
            
            Power_Label.text = "ON"
            Power_Switch.setOn(true, animated: true)
            
            
            Volume_Slider.isEnabled = true
            Favorites_Segment.isEnabled = true
            
            Zero_Button.isEnabled = true
            One_Button.isEnabled = true
            Two_Button.isEnabled = true
            Three_Button.isEnabled = true
            Four_Button.isEnabled = true
            Five_Button.isEnabled = true
            Six_Button.isEnabled = true
            Seven_Button.isEnabled = true
            Eight_Button.isEnabled = true
            Nine_Button.isEnabled = true
            Channel_Plus.isEnabled = true
            Channel_Minus.isEnabled = true
        }
    }
    //Segemented Control Function
    @IBAction func FavChannel_Seg(_ sender:UISegmentedControl) {
        switch sender.selectedSegmentIndex{
            
        case 0:
            Channel_Num.text = "52"
        case 1:
            Channel_Num.text = "50"
        case 2:
            Channel_Num.text = "30"
        case 3:
            Channel_Num.text = "23"
        default:
            break
        }
    }
    
    //Channel Buttons pressed  functions
    var array = [String]()
    @IBAction func ChannelButton_Press(_ sender: UIButton) {
        
        let selectedChannel: String = sender.currentTitle!
        
        if(array.count == 0 || array.count == 1){
            array.append(selectedChannel)
        }
        
        if(array.count == 2){
            let twoDigitChannel = array[0] + array[1]
            if twoDigitChannel == "00" {
                array.removeAll()
                Channel_Num.text = ""
            } else {
                Channel_Num.text = twoDigitChannel
                array.removeAll()
            }
        } else if array.count == 3 {
            array.removeAll()
            Channel_Num.text = ""
        }
    }
    
    
    //Channel UP and Down Functions
    @IBAction func Up_Channel(_ sender: UIButton) {
        let showNum = (Int)(Channel_Num.text!)
        if(showNum! + 1 > 99 || showNum! + 1 < 1){
            array.removeAll()
            Channel_Num.text = "00"
        } else {
            let addedNumber = showNum! + 1
            Channel_Num.text = "\(addedNumber)"
        }
    }
    
    
    @IBAction func Down_Channel(_ sender: UIButton) {
        let showNum = (Int)(Channel_Num.text!)
        if(showNum! - 1 < 1 || showNum! - 1 > 99){
            array.removeAll()
            Channel_Num.text = "00"
        } else {
            let subtractedNumber = showNum! - 1
            Channel_Num.text = "\(subtractedNumber)"
        }
    }
    
    //Override Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        Power_Switch.addTarget(self, action: #selector(stateChanged), for: .valueChanged)
    }
    
    
    @objc func stateChanged(switchState: UISwitch){
        if Power_Switch.isOn {
            Power_Label.text = "ON"
        } else {
            Power_Label.text = "OFF"
        }
    }
}




