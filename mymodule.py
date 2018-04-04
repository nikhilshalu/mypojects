def sayhi():
    print('Hi, this is a function of mymodule.')
    return 


# Simple factorial program
# need to define function before calling it. Otherwise will throw error
def factorial(number):
    product = 1
    for i in range(number):
        product = product * (i+1)
    print(product)
    return product

__version__ = '0.1'

