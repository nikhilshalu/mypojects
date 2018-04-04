

object simple_function {
  def main(arg: Array[String]){
    def add(a:Double = 100, b:Double = 110) : Double = {
      var sum:Double = 0
      sum = a + b
      return sum
      
      
    }
    
    println("SUM: " + add())
    
    
    
  }
}