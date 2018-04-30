# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
import uuid
import DBSession
from models import model
from common.TransformToList import trans_params

class SUsers():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"

    @trans_params
    def get_all_user_tel(self):
        all_tel = None
        try:
            all_tel = self.session.query(model.Users.Utel).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
        finally:
            self.session.close()

        return all_tel

    def regist_user(self, utel, upwd):
        """
        :param utel:
        :param upwd:
        :return:
        """
        try:
            new_user = model.Users()
            new_user.Uid = str(uuid.uuid4())
            new_user.Utel = utel
            new_user.Upwd = upwd
            new_user.Uname = None
            new_user.Ustatus = 101
            new_user.Ucardtype = None
            new_user.Ucardno = None
            new_user.Utruename = None
            new_user.Usex = None
            new_user.Ucoin = None
            new_user.Upaypwd = None
            self.session.add(new_user)
            self.session.commit()
            self.session.close()
            return True
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
            self.session.close()
            return False

    def get_upwd_by_utel(self, utel):
        upwd = None
        try:
            upwd = self.session.query(model.Users.Upwd).filter_by(Utel=utel).scalar()
        except Exception as e:
            print(e.message)
        finally:
            self.session.close()
        return upwd

    def get_uid_by_utel(self, utel):
        uid = None
        try:
            uid = self.session.query(model.Users.Uid).filter_by(Utel=utel).scalar()
        except Exception as e:
            print(e.message)
        finally:
            self.session.close()
        return uid

    def update_userinfo_by_uid(self, users, uid):
        try:
            self.session.query(model.Users).filter_by(Uid=uid).update(users)
            self.session.commit()
            self.session.close()
            return True
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
            self.session.close()
            return False

    def get_userinfo_by_uid(self, uid):
        user_info = None
        try:
            user_info = self.session.query(model.Users.Utel, model.Users.Uname, model.Users.Ustatus,
                                           model.Users.Usex, model.Users.Ucoin)\
                .filter_by(Uid=uid).first()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return user_info

    def get_ustatus_by_uid(self, uid):
        ustatus = None
        try:
            ustatus = self.session.query(model.Users.Ustatus).filter_by(Uid=uid).scalar()
            self.session.close()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
            self.session.close()
            return False
        return ustatus

    def get_utruename_by_uid(self, uid):
        utruename = None
        try:
            utruename = self.session.query(model.Users.Utruename).filter_by(Uid=uid).scalar()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return utruename