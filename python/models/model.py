# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, create_engine, Integer, String, Text, Float
from config import dbconfig as cfg
import pymysql

DB_PARAMS = "{0}://{1}:{2}@{3}/{4}?charset={5}".format(
    cfg.sqlenginename, cfg.username, cfg.password, cfg.host, cfg.database, cfg.charset)
mysql_engine = create_engine(DB_PARAMS, echo=True)
Base = declarative_base()

class Users(Base):
    __tablename__ = "Users"
    Uid = Column(String(64), primary_key=True)
    Utel = Column(String(14), nullable=False)
    Upwd = Column(String(64), nullable=False)
    Uname = Column(String(64))
    Ustatus = Column(Integer, nullable=False)
    Utruename = Column(String(64))
    Ucardtype = Column(Integer)
    Ucardno = Column(String(18))
    Usex = Column(Integer)
    Ucoin = Column(Float)
    Upaypwd = Column(Integer)

class Tasks(Base):
    __tablename__ = "Tasks"
    Tid = Column(String(64), primary_key=True)
    Tname = Column(String(256), nullable=False)
    Tcointype = Column(Integer)
    Tcoinnum = Column(Float, nullable=False)
    Tlocation = Column(String(256), nullable=False)
    Tabo = Column(Text, nullable=False)
    Tstatus = Column(Integer, nullable=False)
    Ttype = Column(Integer, nullable=False)
    Uid = Column(String(64), nullable=False)

class UTasks(Base):
    __tablename__ = "UTasks"
    UTid = Column(String(64), primary_key=True)
    Uid = Column(String(64), nullable=False)
    Tid = Column(String(64), nullable=False)

class Upays(Base):
    __tablename__ = "Upays"
    UPid = Column(String(64), primary_key=True)
    UPname = Column(String(128), nullable=False)
    UPno = Column(String(32), nullable=False)
    UPstatus = Column(Integer, nullable=False)

class databse_deal():
    def __init__(self):
        self.conn = pymysql.connect(host=cfg.host, user=cfg.username, passwd=cfg.password, charset=cfg.charset)
        self.cursor = self.conn.cursor()

    def create_database(self):
        sql = "create database if not exists {0} DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;".format(
            cfg.database)
        print sql
        try:
            self.cursor.execute(sql)
        except Exception, e:
            print(e)
        finally:
            self.conn_close()

    def drop_database(self):
        sql = "drop database if exists {0} ;".format(
            cfg.database)
        print sql
        try:
            self.cursor.execute(sql)
        except Exception, e:
            print(e)

        finally:
            self.conn_close()

    def conn_close(self):
        self.conn.close()


def create():
    databse_deal().create_database()
    Base.metadata.create_all(mysql_engine)


def drop():
    databse_deal().drop_database()


if __name__ == "__main__":
    '''
    运行该文件就可以在对应的数据库里生成本文件声明的所有table
    如果需要清除数据库，输入drop
    如果需要创建数据库 输入任意不包含drop的字符
    '''
    action = raw_input("create database?")
    if "drop" in action:
        drop()
    else:
        create()
