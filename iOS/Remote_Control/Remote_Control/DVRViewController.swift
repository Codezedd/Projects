//
//  DVRViewController.swift
//  Remote_Control
//
//  Created by Keven Zarate on 2/25/20.
//  Copyright © 2020 Keven Zarate. All rights reserved.
//

import UIKit

class DVRViewController: UIViewController {
    
    //UI Labels and Buttons
    @IBOutlet weak var dvr_PowLabel: UILabel!
    @IBOutlet weak var state_Label: UILabel!
    
    
    @IBOutlet weak var play_Button: UIButton!
    @IBOutlet weak var stop_Button: UIButton!
    @IBOutlet weak var pause_Button: UIButton!
    @IBOutlet weak var forward_Button: UIButton!
    @IBOutlet weak var rewind_Button: UIButton!
    @IBOutlet weak var record_Button: UIButton!
    
    
    //UI Action
    
    @IBAction func switchToTv(sender: UIBarButtonItem) {
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func swtch_dvrPower(sender: UISwitch) {
        if(sender.isOn){
            dvr_PowLabel.text = dvrPowerState.on.rawValue
            
            play_Button.isEnabled = true
            stop_Button.isEnabled = true
            pause_Button.isEnabled = true
            forward_Button.isEnabled = true
            rewind_Button.isEnabled = true
            record_Button.isEnabled = true
            
        }else{
            dvr_PowLabel.text = dvrPowerState.off.rawValue
            
            play_Button.isEnabled = false
            stop_Button.isEnabled = false
            pause_Button.isEnabled = false
            forward_Button.isEnabled = false
            rewind_Button.isEnabled = false
            record_Button.isEnabled = false
            
        }
    }
    
    
    @IBAction func playFunction(sender: UIButton) {
        if(state_Label.text != buttonState.record.rawValue){
            state_Label.text = buttonState.play.rawValue
        }else{
            popup_Message(sender: sender, state: buttonState.play.rawValue);
        }
    }
    
    @IBAction func stopPress(sender: UIButton) {
        state_Label.text = buttonState.stop.rawValue
    }
    
    
    @IBAction func pausePress(sender: UIButton) {
        if(state_Label.text == buttonState.play.rawValue
            && state_Label.text != buttonState.record.rawValue){
            state_Label.text = buttonState.pause.rawValue
        }else{
            popup_Message(sender:sender, state: buttonState.pause.rawValue);
        }
    }
    
    
    @IBAction func fastForwardPress(sender: UIButton) {
        if(state_Label.text == buttonState.play.rawValue
            && state_Label.text != buttonState.record.rawValue){
            state_Label.text = buttonState.forward.rawValue
        }else{
            popup_Message(sender: sender, state: buttonState.forward.rawValue);
        }
    }
    
    
    @IBAction func fastRewindPress(sender: UIButton) {
        if(state_Label.text == buttonState.play.rawValue
            && state_Label.text != buttonState.record.rawValue){
            state_Label.text = buttonState.rewind.rawValue
        }else{
            popup_Message(sender: sender, state: buttonState.rewind.rawValue);
        }
    }
    
    @IBAction func recordPress(sender: UIButton) {
        if(state_Label.text == buttonState.stop.rawValue){
            state_Label.text = buttonState.record.rawValue
        }else{
            popup_Message(sender: sender, state: buttonState.record.rawValue);
        }
    }
    
    //Using enumerations for buttons status
    enum dvrPowerState : String{
        case on = "ON"
        case off = "OFF"
    }
    
    enum buttonState : String {
        case play = "Play"
        case pause = "Paused"
        case stop = "Stopped"
        case rewind = "Fast Rewind"
        case forward = "Fast Forward"
        case record = "Recording"
    }
    
    //Functions
    
    func forceState (given: String){
        state_Label.text = buttonState.stop.rawValue;
        state_Label.text = given
    }
    
    func popup_Message (sender: UIButton, state: dvrPowerState.RawValue){
        
        let alertControl = UIAlertController(title: "Invalid Mode", message: "Cannot \(sender.currentTitle!) while \(state_Label.text!)", preferredStyle: .alert)
        
        let alertCancel = UIAlertAction(title: "Cancel", style: .default, handler: nil)
        
        let alertForce = UIAlertAction(title: "Force", style: .destructive, handler: {(action:UIAlertAction) in self.forceState(given: state)});
        
        alertControl.addAction(alertForce);
        alertControl.addAction(alertCancel);
        
        present(alertControl, animated: true, completion: nil)
    }
   
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
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
