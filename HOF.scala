

object HOF {
  def main(arg: Array[String]){
    
    //Function to calculate the sum of numbers between any two given numbers
    def id(x: Int) : Int = x
    def sumInt(a: Int, b: Int) : Int = {
      
      if (a > b) 0 else id(a) + sumInt(a + 1, b)
      
    }
    
     //Function to calculate the sum of squares between any two given numbers
    
    def square(x: Int) : Int = x * x
    
    def sumsquare(a: Int, b: Int) : Int = {
      
      if (a > b) 0 else square(a) + sumsquare(a + 1, b)
      
      
    }
    
    //Function to calculate the sum of powers of two between any two numbers
    
     def poweroftwo(x: Int) : Int = if (x == 0) 1 else 2 * poweroftwo(x - 1)
    
    def sumofpoweroftwo(a: Int, b:Int) : Int ={
      if (a > b) 0 else poweroftwo(a) + sumofpoweroftwo(a + 1, b)
      
    }
     //Functional Programming
     
     def sum(f: Int => Int, a: Int, b:Int) : Int  = {
       if (a > b) 0 else f(a) + sum(f, a + 1, b)
       
       
     }
     
     
    
    println("The sum of numbers is :" +sumInt(3,5)) // 3 + 4 + 5 =12
    println("The sum of squares of numbers is :" +sumsquare(3,5)) // 9 + 16 + 25 =50
    println("The sum of powers of two is : " +sumofpoweroftwo(2,4)) // 4 + 8 + 16 = 28
    println("============================================================")
  
    println("sum of integers:" +sum(id,3,5))
    println("sum of square:" +sum(square,3,5))
    println("sum of powers of two" +sum(poweroftwo,2,4))
    
  }
}