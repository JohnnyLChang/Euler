# coding=UTF-8
import logging


logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s %(name)-12s %(levelname)-8s %(message)s',
                    datefmt='%m-%d %H:%M',
                    handlers = [logging.FileHandler('my.log', 'w', 'utf-8'),])
 
logging.propagate = False


 # 定義 handler 輸出 sys.stderr
#console = logging.StreamHandler()
#console.setLevel(logging.DEBUG)

'''
# 設定輸出格式
formatter = logging.Formatter('%(name)-12s: %(levelname)-8s %(message)s')
# handler 設定輸出格式
console.setFormatter(formatter)

# 加入 hander 到 root logger
logging.getLogger('').addHandler(console)
'''