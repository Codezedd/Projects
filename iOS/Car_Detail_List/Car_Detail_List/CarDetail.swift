//
//  CarDetail.swift
//  Car_Detail_List
//
//  Created by Keven Zarate on 3/10/20.
//  Copyright © 2020 Keven Zarate. All rights reserved.
//

import Foundation
//Initial information about the type of car and how they are generally described as
let carinfo = [
    CarDetail(name: "2020 Audi RS6 Avant",
              type: .audi,
              shortDescription: "Performance wagon car",
              longInterestingFact:
        "This 591 horsepower wagon is being brought over to the North American market for the first time. Also included is a mild hybrid sytem which allows the wagon to be keeped on when stopped. To an extent styling was inspired by Lamborghini (Which is owned by Volkwagen who in turn own Audi)" ),
    
    
    CarDetail(name:"1955 BMW Isetta",
              type: .bmw,
              shortDescription: "Smallest size car" ,
              longInterestingFact: "Italian designed micro car fit for maybe up to three people, if one of them was a small child. Selling over 161,728 vehicles over its lifetime. With a very impressive 78 mile per gallon in 1955. BMW resdesigned almost eveything on the car for their own brand"),
    
    CarDetail(name:"1966 Chevrolet Chevelle",
              type: .chevrolet,
              shortDescription: "Classic mid size American coupe",
              longInterestingFact: "A very typical American car for 1960 coming in many different styles, sizes, and options. The top of the line trim for the Chevelle was the 'Malibu' which would later replace the Chevelle name entirely in 1978"),
    
    CarDetail(name:"1993 GMC Typhoon" ,
              type: .gmc,
              shortDescription: "High horsepower American SUV",
              longInterestingFact: "Do you feel lucky punk? The V6 280 horsepower SUV with a 0 to 60 mph time of 5.3 seconds was just as quick the Ferrari 348t and Chevrolet Corvette. A favorite of Clint Eastwood "),
    
    CarDetail(name:"2020 Honda Civic Type R",
              type: .honda,
              shortDescription: "Track ready sport hatchback car",
              longInterestingFact: "A very bright, popping, and fast 306 horsepower hatchback that was designed to be quick around the race track and quick to be noticed by its uniqe body angles, color, and very noticeable rear wing."),
    
    CarDetail(name: "1991 Lancia Delta HF Intergrale",
              type: .lancia,
              shortDescription: "World rally hatchback car",
              longInterestingFact: "Built after successfull rally championship wins in 1991, this 5 door hatchback was manufactured as the requirement for rally racing as car manufacturers had to built a minimum amount of cars to be sold to the public before they could actually race"),
    
    CarDetail(name: "1990 Mazda RX-7" ,
              type: .mazda,
              shortDescription: "Sport rotary engine car",
              longInterestingFact: "A completely different engine compared to whats in most cars these days, the Wankel rotary engine used a rotary desgin that transferred pressure into a rotating energy to power the car. Or what most people like to call them 'a spinning dorito chip' "),
    
    CarDetail(name:  "1969 Mini Cooper",
              type: .mini,
              shortDescription: "Small British economy car",
              longInterestingFact: "Compact iconic car from 1960 Britain, the design of the mini cooper allowed for almost the entire inside of the vehicle space to be used. as the four tires almost each corner of the car body. Allowing just enought space for passengers and some luggage"),
    
    CarDetail(name: "1991 Mitsubishi Delica",
              type: .mitsubishi,
              shortDescription: "Low powered delivery/passenger van",
              longInterestingFact: "Delica Star Wagon as the name it was given as a passenger van for the Australian market had a very popular use in Japan as a recreational vehicle. Many different configurations allowed gas to diesel engines to be used in the van to fit the drivers need. "),
    
    CarDetail(name:"1990 Nissan Pulsar GTI-R",
              type: .nissan,
              shortDescription: "Light weight rally car ",
              longInterestingFact: "The small compact 3 door hatchback was produced in accordance of rally racing rules to allow Nissan to participate in the World Rally Championship. Reaching a top speed of 144 mph (supposedly) this compact car was capable of great acceleration due to its size, Scary? Yes. Worth it? Definitely. "),
    
    CarDetail(name: "1990 Toyota Sera" ,
              type: .toyota,
              shortDescription: "Small coupe car with gull wing doors",
              longInterestingFact: "This unique compact car from Toyota had a few 'interesting' components to it for 1990. A glass roof and gull wing doors that opened upwards, actually inpired the design of the much less strange looking McLaren F1 supercar."),
    
    CarDetail(name: "2018 Volkswagen Golf R",
              type: .volkswagen,
              shortDescription: "4 door hatchback with All Wheel Drive",
              longInterestingFact: "Boxy plain looking hatchback is the complete all around package of a car. At 292 horsepower and all wheel drive system with a reasonable 29 mpg combined, if you want a nice mid level corporate office cubicle on wheels this is it."),
]

class CarDetail {
    
    enum `Type`: String {
        
        case audi = "audi"
        case bmw = "bmw"
        case chevrolet = "chevrolet"
        case gmc = "gmc"
        case honda = "honda"
        case lancia = "lancia"
        case mazda = "mazda"
        case mini = "mini"
        case mitsubishi = "mitsubishi"
        case nissan = "nissan"
        case toyota = "toyota"
        case volkswagen = "volkswagen"
        
    }
    var name : String
    var type : Type
    var shortDescription: String
    var longInterestingFact: String
    
    init(name: String, type: Type, shortDescription: String, longInterestingFact: String) {
        
        self.name = name
        self.type = type
        self.shortDescription = shortDescription
        self.longInterestingFact = longInterestingFact
        
    }
}
