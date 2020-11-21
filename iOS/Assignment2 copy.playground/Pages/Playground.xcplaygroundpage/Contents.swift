import UIKit

//Binary Search:
//Declaring a function for the binary search
func binary_Search(_ key: Int) -> Int {
    
    //Our test array of numbers is right here
    let test_Array = [1,2,7,13,15,19]
    
    var lowest_num = 0
    var highest_num = test_Array.count
    
    while lowest_num < highest_num {
        
        let middle_num = lowest_num + (highest_num - lowest_num) / 2
        if test_Array[middle_num] == key {
            //return the number we compared in the array to the key we want to match
            print("Your key is here! ", test_Array[middle_num])
            return test_Array[middle_num]
            
        } else if test_Array[middle_num] < key {
            lowest_num = middle_num + 1
            
        } else {
            highest_num = middle_num
        }
    }
    
    // if the key is not found within our array returns -1
    print("Nothing found here..")
    return -1
}

//test binary searches here
binary_Search(13)
binary_Search(5)
binary_Search(2)

//Fractions:

//setting up our class first
class Fraction{
    //Numerator and Denomintor
    var numerator: Int = 0
    var denominator: Int = 1
    
    init(_ numerator: Int, over denominator: Int) {
        self.numerator = numerator
        self.denominator = denominator
    }
    
    init() {}
    
    func setTo(numerator: Int, over denominator: Int){
        self.numerator = numerator
        self.denominator = denominator
    }
    
    func print(){
        Swift.print("\(numerator)/\(denominator)")
        
    }
    
    func toDouble() -> Double{
        return Double(numerator) / Double(denominator);
    }
    
    func reduce(){
        var u = abs(numerator)
        var v = denominator
        var r: Int
        while (v != 0){
            r = u % v;
            u = v;
            v = r
        }
        numerator /= u
        denominator /= u
    }
    
    //Base addition function
    func add(_ f: Fraction){
        numerator = numerator * f.denominator + denominator * f.numerator
        denominator = denominator * f.denominator
        
        reduce()
        
    }
    
    //Subtraction function
    func subtract(_ f: Fraction){
        numerator = numerator * f.denominator - denominator * f.numerator
        denominator = denominator * f.denominator
        reduce()
    }
    
    //Multiplication function
    func multipli(_ f: Fraction){
        numerator = numerator * f.numerator
        denominator = denominator * f.denominator
        reduce()
    }
    
    //Division function
    func divide(_ f: Fraction){
        numerator = numerator * f.denominator
        denominator = denominator * f.numerator
        reduce()
    }
}

//Test fractions
var f1 = Fraction(1, over: 2)
var f2 = Fraction(1, over: 4)

var f3 = Fraction(1, over: 2)
var f4 = Fraction(1, over: 6)

var f5 = Fraction(1, over: 2)
var f6 = Fraction(1, over: 8)

var f7 = Fraction(1, over: 1)
var f8 = Fraction(1, over: 10)

//Operations
print("Addition")
f1.add(f2)
f1.print()

print("Subtraction")
f3.subtract(f4)
f3.print()

print("Multiplication")
f5.multipli(f6)
f5.print()

print("Division")
f7.divide(f8)
f7.print()


