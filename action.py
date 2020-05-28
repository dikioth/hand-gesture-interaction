import os.path
import time

# main loop, checks if file exists and rewrites it with information about hand gesture. 
def main(file_name):
    print("Starting hand recognition.")
    
    while True:
        try:
            hand_gesture = input("Choose hand gesture: ")

            try:
                file_check = os.path.isfile(file_name)
            except:
                file_check = False

            if file_check:
                try:
                    f = open(file_name, "r")
                    first_line = f.readline().strip()
                    f.close()
                
                    if first_line == "1":
                        f = open(file_name, "w")
                        f.write("0\n" + str(hand_gesture))
                        f.close()
                        print("Writing hand gesture " + str(hand_gesture) + " to file.")
                except:
                    time.sleep(0.4)
            else:
                time.sleep(0.4)

        except:
            print("Program stopped.")
            break
if __name__ == "__main__":
    # file_name has to be the same as for furhats implementatios. 
    main("skills/IISProject/test.txt")
