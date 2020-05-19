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
for page in range(1,317):
    try : 
        for passessay in range(1,21):
            driver.get('http://www.jobkorea.co.kr/starter/PassAssay?FavorCo_Stat=0&Pass_An_Stat=0&OrderBy=0&EduType=0&WorkType=0&isSaved=0&Page=%i' %page)
            time.sleep(3) 
            find_xpath('//*[@id="container"]/div[2]/div[5]/ul/li['+str(passessay)+']/div[1]/p/a/span').click()
            span_text = driver.find_element_by_xpath('//*[@id="container"]/div[2]/div[3]/dl/dt[1]/button/span[2]').text
            print(span_text)
            # driver.get(question[0].get_attribute('tx'))
            # '//*[@id="container"]/div[2]/div[3]/dl/dt[1]/button/span[2]'
    except :
        print('error')
    #driver.find_element_by_xpath('//*[@id="container"]/div[2]/div[5]/div[2]/ul/li[1]/span')

# load
# csvfile = open('./jobkorea_crawling.csv', "w", newline="")
# csvwriter = csv.writer(csvfile)


# for i in range(6306)``