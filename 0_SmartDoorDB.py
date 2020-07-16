import pymysql
import RPi.GPIO as GPIO
import time


#릴레이 모듈 세팅
LedOutputPin  = 17
GPIO.setmode(GPIO.BCM)
GPIO.setup(LedOutputPin, GPIO.OUT)
GPIO.output(LedOutputPin, GPIO.HIGH)

GPIO.output(17,False)

#DB에서값받아와 읽어서 문열기
while(True):
    conn = pymysql.connect(host='Smartdoor.con8izhblf16.ap-northeast-2.rds.amazonaws.com', user='admin', password='12341234',
                       db='smartdoor', charset='utf8')
    curs = conn.cursor()
    sql = "SELECT * FROM USER "
    curs.execute(sql)
    rows = curs.fetchall()
    a = 0
    for row in rows:
        print(row[5])
        if(row[5]==1):
            GPIO.output(17,True)
            time.sleep(1)
            GPIO.output(17,False)
            sql = "UPDATE USER set SIGN = 0 where USER_ID = %s "
            curs.execute(sql, row[0])
            conn.commit()
            timeone = time.ctime()
            sql = "INSERT INTO LOG VALUES (%s,%s) "
            curs.execute(sql,(row[0],timeone))
            conn.commit()
            #time.sleep(3)
            print('open')
        else:
            GPIO.output(17,False)
            print('close')

    a = rows[0][0]
    GPIO.output(17,False)
    time.sleep(1)
    conn.close()   
     
