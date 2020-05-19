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
for page in range(1,317):
    try : 
        for passessay in range(1,21):
            try:
                driver.get('http://www.jobkorea.co.kr/starter/PassAssay?FavorCo_Stat=0&Pass_An_Stat=0&OrderBy=0&EduType=0&WorkType=0&isSaved=0&Page=%i' %page)
                time.sleep(3) 
                find_xpath('//*[@id="container"]/div[2]/div[5]/ul/li['+str(passessay)+']/div[1]/p/a/span').click()
                #span_text = driver.find_element_by_xpath('//*[@id="container"]/div[2]/div[3]/dl/dt[1]/button/span[2]').text
                #print(span_text)
                driver.find_element_by_xpath('//*[@id="container"]/div[2]/div[3]/dl/dt[1]/button/span[2]').click()
                SpecialPrice =driver.find_element_by_xpath('//*[@id="container"]/div[2]/div[3]/dl/dt[1]/button/span[2]').text

                '//*[@id="container"]/div[2]/div[3]/dl/dt['+str(j)+']/button/span[2]' #질문
                '//*[@id="container"]/div[2]/div[3]/dl/dd['+str(j)+']/div[1]' # 답

                # //*[@id="container"]/div[2]/div[3]/dl/dt[1]/button/span[2]
                # //*[@id="container"]/div[2]/div[3]/dl/dt[2]/button/span[2]
                # //*[@id="container"]/div[2]/div[3]/dl/dt[3]/button/span[2]
                # //*[@id="container"]/div[2]/div[3]/dl/dt[4]/button/span[2]
                # //*[@id="container"]/div[2]/div[3]/dl/dt[5]/button/span[2]
                # //*[@id="container"]/div[2]/div[3]/dl/dt[6]/button/span[2]

                # //*[@id="container"]/div[2]/div[3]/dl/dd[1]/div[1]
                # //*[@id="container"]/div[2]/div[3]/dl/dd[2]/div[1]
                # //*[@id="container"]/div[2]/div[3]/dl/dd[3]/div[1]

                # //*[@id="container"]/div[2]/div[3]/dl/dd[6]/div[1]
                print(SpecialPrice)
            except:
                print("errors")

            # html = driver.page_source
            # soup = BeautifulSoup(html, 'html.parser')
            # a = soup.find_all("div", "tx")
            # q = soup.find_all("span", "tx")
            # answer_list, question_list = [], []
            # print("=======================질문===============================")
            # for q1 in q:
            #     question_list.append(q1.get_text())            
            # print(question_list)
            # print("======================답========================")
            # for a1 in a:
            #     answer_list.append(a1.get_text())
            # print(answer_list)

    except :
        print('error')
    #driver.find_element_by_xpath('//*[@id="container"]/div[2]/div[5]/div[2]/ul/li[1]/span')

# load
# csvfile = open('./jobkorea_crawling.csv', "w", newline="")
# csvwriter = csv.writer(csvfile)


# for i in range(6306)``