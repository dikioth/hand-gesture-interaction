import os.path
import time
import random #TODO remove after changing get_hand_gesture()

# This function handles all hand recognition and returns an int between 0-5 representing a hand gesture. 
# TODO Change function to handle hand recognition instead of returning a random int. 
def get_hand_gesture():
    return random.randint(0, 5) 


# main loop, checks if file exists and rewrites it with information about hand gesture. 
def main(file_name):

    while True:
        file_check = os.path.isfile(file_name)
        if file_check:
            f = open(file_name, "rw")
            first_line = f.readline().strip()
            
            
            if first_line == "1":
                hand_gesture = get_hand_gesture()
                f.write("0\n" + str(hand_gesture))
                f.close()
            else:
                time.sleep(0.5)      
    
        
if __name__ == "__main__":
    # file_name has to be the same as for furhats implementatios. 
    main("test.txt")
