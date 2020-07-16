import cv2
import numpy as np
import os
import RPi.GPIO as GPIO
import time
import pymysql


#릴레이모션 세팅
LedOutputPin  = 17
GPIO.setmode(GPIO.BCM)
GPIO.setup(LedOutputPin, GPIO.OUT)
GPIO.output(LedOutputPin, GPIO.HIGH)

GPIO.output(17,False)
recognizer = cv2.face.LBPHFaceRecognizer_create()
recognizer.read('trainer/trainer.yml')
cascadePath = "haarcascade_frontalface_default.xml"
faceCascade = cv2.CascadeClassifier(cascadePath);
font = cv2.FONT_HERSHEY_SIMPLEX
#iniciate id counter
id = 0
# names related to ids: example ==> Marcelo: id=1,  etc
names = ['SangEon','Sejin'] 
# Initialize and start realtime video capture
cam = cv2.VideoCapture(-1)
cam.set(3, 640) # set video widht
cam.set(4, 480) # set video height
# Define min window size to be recognized as a face
minW = 0.1*cam.get(3)
minH = 0.1*cam.get(4)
timesec = 0
while True:
    ret, img =cam.read()
    img = cv2.flip(img, 1) # Flip vertically
    gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
    
    faces = faceCascade.detectMultiScale( 
        gray,
        scaleFactor = 1.2,
        minNeighbors = 5,
        minSize = (int(minW), int(minH)),
       )
    for(x,y,w,h) in faces:
        cv2.rectangle(img, (x,y), (x+w,y+h), (0,255,0), 2)
        id, confidence = recognizer.predict(gray[y:y+h,x:x+w])
        # Check if confidence is less them 100 ==> "0" is perfect match
        
        if (confidence < 70):
            cv2.putText(img, "Unlocked", (250, 450), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 255, 0), 2)
            
            if timesec>=100 :
                GPIO.output(17,True)
                #id = names[id]
                conn = pymysql.connect(host='Smartdoor.con8izhblf16.ap-northeast-2.rds.amazonaws.com', user='admin', password='12341234',
                           db='smartdoor', charset='utf8')
                curs = conn.cursor()
                timeone = time.ctime()
                sql = "INSERT INTO LOG VALUES (%s,%s) "
                idds=names[id]
                curs.execute(sql,(idds,timeone))
                conn.commit()
                conn.close()
                GPIO.output(17,False)
                timesec=0
            

        else:
            cv2.putText(img, "Locked", (250, 450), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 0, 255), 2)
            GPIO.output(17,False)
        if (confidence < 100):
            id = names[id]
            confidence =  "  {0}%".format(round(2*(100 - confidence)))
                
        else:
            id = "unknown"
            confidence = "  {0}%".format(round(100 - confidence))
          
        cv2.putText(img, str(id), (x+5,y-5), font, 1, (255,255,255), 2)
        cv2.putText(img, str(confidence), (x+5,y+h-5), font, 1, (255,255,0), 1)  
    
    cv2.imshow('camera',img) 
    k = cv2.waitKey(10) & 0xff # Press 'ESC' for exiting video
    if k == 27:
        break
    timesec+=1
    print(timesec)
# Do a bit of cleanup
print("\n [INFO] Exiting Program and cleanup stuff")
cam.release()
cv2.destroyAllWindows()

