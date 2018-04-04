

object myfunction {
  def main(arg: Array[String]){
    println("Main Function: " + exec(time()))
    def time() : Long = {
      println("In time function....")
      return System.nanoTime()
    }
    
    def exec(t: => Long) : Long = {
      println("Enter the exec function....")
      println("Time :" +t)
      println("Calling t again")
      return t
    }
    
    
  }
}