# -*-coding:utf-8 -*-
#S02P31A104-17
import requests
from bs4 import BeautifulSoup
import csv
from selenium import webdriver
import time
import pandas as pd
import math
import pickle

def find_xpath(url):
    return driver.find_element_by_xpath(url)

driver = webdriver.Chrome('./chromedriver')
time.sleep(5)

csvfile = open('./result.csv', "w", newline="")
csvwriter = csv.writer(csvfile)



for page in range(1,2): # 317
    try : 
        for passessay in range(1,10):#21
            res = []
            try:
                driver.get('http://www.jobkorea.co.kr/starter/PassAssay?FavorCo_Stat=0&Pass_An_Stat=0&OrderBy=0&EduType=0&WorkType=0&isSaved=0&Page=%i' %page)
                time.sleep(3) 
                find_xpath('//*[@id="container"]/div[2]/div[5]/ul/li['+str(passessay)+']/div[1]/p/a/span').click()
                #span_text = driver.find_element_by_xpath('//*[@id="container"]/div[2]/div[3]/dl/dt[1]/button/span[2]').text
                #print(span_text)
                print("=============")
                find_xpath('//*[@id="container"]/div[2]/div[1]/div[1]/h2/em').click()
                q =find_xpath('//*[@id="container"]/div[2]/div[1]/div[1]/h2/em').text
                print("직무",q)
                if q:res.append(q)
                else: res.append(0)
                
                res.append(q)
                for j in range(1, 7):
                    try : 
                        find_xpath('//*[@id="container"]/div[2]/div[3]/dl/dt['+str(j)+']/button/span[2]').click()
                        q =find_xpath('//*[@id="container"]/div[2]/div[3]/dl/dt['+str(j)+']/button/span[2]').text
                        print("문항",q)
                        res.append(q)
                    except:
                        print("없는문항")
                        res.append(0)
                        continue
                for j in range(1, 7):
                    try : 
                        find_xpath('//*[@id="container"]/div[2]/div[3]/dl/dd['+str(j)+']/div[1]').click()
                        q =find_xpath('//*[@id="container"]/div[2]/div[3]/dl/dd['+str(j)+']/div[1]').text
                        print("자소서",q)
                        res.append(q)
                    except:
                        print("없는자소서")
                        res.append(0)
                        continue
                print("---res---")
                print(res)
                csvwriter.writerow([r for r in res])
            except:
                print("errors")


    except :
        print('error')
csvfile.close()
