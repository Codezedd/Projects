//
//  ConfigManage.swift
//  Remote_Control
//
//  Created by Keven Zarate on 3/2/20.
//  Copyright © 2020 Keven Zarate. All rights reserved.
//

import UIKit
class ConfigManage: NSObject {
    
    private var idNum: Int
    private var configChnlNumber: Int
    private var configChnlLabel:String
    
    init(idNumIn:Int, configChnlNumberIn:Int, configChnlLabelIn: String){
        idNum  =   idNumIn
        configChnlNumber = configChnlNumberIn
        configChnlLabel = configChnlLabelIn
    }
    
    //Functions
    
    func configSavedChannel(funcObj: ConfigManage, idNumIn: Int, configChnlNumberIn: Int, configChnlLabelIn: String){
        funcObj.idNum = idNumIn
        funcObj.configChnlNumber = configChnlNumberIn
        funcObj.configChnlLabel = configChnlLabelIn
    }
    
    func getIdNum()->Int{
        return  idNum
    }
    
    func setIdNum(idNumIn:Int){
        idNum  = idNumIn
    }
    
    func getconfigChanlNumber()->Int{
        return configChnlNumber
    }
    
    func setconfigChanlNumber(configChnlNumberIn:Int){
      configChnlNumber = configChnlNumberIn
    }
    
    func getconfigChanlLabel()->String{
        return  configChnlLabel
    }
    
    func setChannelLabel(configchnlLabelIn:String){
        configChnlLabel = configchnlLabelIn;
    }
    
}
