import os.path
import time
import random #TODO remove after changing get_hand_gesture()

# This function handles all hand recognition and returns an int between 0-5 representing a hand gesture. 
# TODO Change function to handle hand recognition instead of returning a random int. 
def get_hand_gesture():
    return random.randint(0, 5) 


# main loop, checks if file exists and rewrites it with information about hand gesture. 
def main(file_name):
    print("Starting hand recognition.")
    
    while True:
        try:
            file_check = os.path.isfile(file_name)
            if file_check:
                f = open(file_name, "r")
                first_line = f.readline().strip()
                f.close()
                
                if first_line == "1":
                    hand_gesture = get_hand_gesture()
                    f = open(file_name, "w")
                    f.write("0\n" + str(hand_gesture))
                    f.close()
                    print("Writing hand gesture " + str(hand_gesture) + " to file.")
            else:
                time.sleep(0.4)

        except:
            print("Program stopped.")
            break
if __name__ == "__main__":
    # file_name has to be the same as for furhats implementatios. 
    main("skills/IISProject/test.txt")
