

object Magic {
  def main(arg: Array[String]){
    
    var numguess = 0
    do{
      
      println("Enter the number:")
      numguess = readLine.toInt
     }while(numguess != 10)
    
    println("You got the number!!!!")
    
  }
}