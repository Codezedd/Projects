//
//  ConfigViewController.swift
//  Remote_Control
//
//  Created by Keven Zarate on 3/1/20.
//  Copyright © 2020 Keven Zarate. All rights reserved.
//

import UIKit


class ConfigViewController: UIViewController {
       @IBOutlet weak var favoriteSeg:UISegmentedControl!
       @IBOutlet weak var chanelStep: UIStepper!
       @IBOutlet weak var labelTxtField: UITextField!
       @IBOutlet weak var chanelTextLabel: UILabel!
   
      var selectedChannel: ConfigManage!
      var chanelLabel: String = ""
      var currentChannel: Int = 0
    
    
    //UI Functions
    
    @IBAction func segFavButton(sender: UISegmentedControl) {
          let favChannelIn = sender.selectedSegmentIndex
          switch (favChannelIn) {
          case 0:
              selectedChannel = tvFavOne
          case 1:
              selectedChannel = tvFavTwo
          case 2:
              selectedChannel = tvFavThree
          case 3:
              selectedChannel = tvFavFour
          default:
              selectedChannel = tvFavOne
          }
      }
      
      @IBAction func stepperPress(sender: UIStepper) {
          sender.wraps = true
          sender.minimumValue = 1
          sender.maximumValue = 99
        chanelTextLabel.text = String(Int(sender.value))
      }
      
    @IBAction func savePress(sender: UIButton) {
        if (labelTxtField.text!.count < 1 || labelTxtField.text!.count > 4)  {
            let alertMessage = UIAlertController(title: "Warning", message: "Label length must be less than 4 characters", preferredStyle: UIAlertController.Style.alert)
            let alertActionPress = UIAlertAction(title: "Dismiss", style: UIAlertAction.Style.default, handler: nil)
            alertMessage.addAction(alertActionPress)
            self.present(alertMessage, animated: true, completion: nil)
            cancelPress()
            
        }else{
            switch (favoriteSeg.selectedSegmentIndex){
            case 0:
                tvFavOne.setconfigChanlNumber(configChnlNumberIn: Int(chanelTextLabel.text!)!)
                tvFavOne.setChannelLabel(configchnlLabelIn: String(labelTxtField.text!))
                break
            case 1:
                tvFavTwo.setconfigChanlNumber(configChnlNumberIn: Int(chanelTextLabel.text!)!)
                tvFavTwo.setChannelLabel(configchnlLabelIn: String(labelTxtField.text!))
                break
            case 2:
                tvFavThree.setconfigChanlNumber(configChnlNumberIn: Int(chanelTextLabel.text!)!)
                tvFavThree.setChannelLabel(configchnlLabelIn: String(labelTxtField.text!))
                break
            case 3:
                tvFavFour.setconfigChanlNumber(configChnlNumberIn: Int(chanelTextLabel.text!)!)
                tvFavFour.setChannelLabel(configchnlLabelIn: String(labelTxtField.text!))
                break;
            default: break
            }
        }
        
    }
      
      @IBAction func btn_cancel(sender: UIButton) {
          cancelPress()
      }
      
    //Functions
    
    func cancelPress() {
        labelTxtField.text = ""
        chanelStep.value = chanelStep.minimumValue
        chanelTextLabel.text = "1"
        favoriteSeg.selectedSegmentIndex = 0
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
