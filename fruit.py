print("I am running from fruit.py & will get printed irrespective from where I am called")

def printmyfruitname(nameoffruit):
    print("**Inside function printmyfruitname()**")
    print("Value of __name__ is: " + __name__)
    print("Name of fruit: ", nameoffruit)
    print("**Completed function printmyfruitname()**")
    

if __name__ == "__main__":
    printmyfruitname("Fruit")
    print ("We are running fruit.py directly")
    
if __name__ == "fruit":
    print ("Fruit has been imported")