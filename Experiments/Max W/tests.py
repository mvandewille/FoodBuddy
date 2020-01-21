import math
import os
import random
import re
import sys
print(5)

def minimumSwaps(arr):
    swaps = 0
    pos = 0 
    while pos != len(arr):
        if pos + 1 < len(arr) and arr[pos] > arr[pos+1]:
            temp = arr[pos]
            arr[pos] = arr[pos+1]
            arr[pos+1] = temp
            swaps += 1
            pos = 0
        else:
            pos += 1
    return arr

the_array = [2, 3, 4, 1, 5]

print(minimumSwaps(the_array))