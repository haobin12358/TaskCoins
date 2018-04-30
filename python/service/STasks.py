# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
import uuid
import DBSession
from models import model
from common.TransformToList import trans_params

class STasks():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"

    def add_a_task(self, tname, tcointype, tcoinnum, tlocation, tabo, tstatus, ttype, uid):
        """
        :param tname:
        :param tcointype:
        :param tcoinnum:
        :param tlocation:
        :param tabo:
        :param tstatus:
        :param ttype:
        :param uid:
        :return:
        """
        try:
            new_task = model.Tasks()
            new_task.Tid = str(uuid.uuid4())
            new_task.Tname = tname
            new_task.Tcointype = tcointype
            new_task.Tcoinnum = tcoinnum
            new_task.Tlocation = tlocation
            new_task.Tabo = tabo
            new_task.Tstatus = tstatus
            new_task.Ttype = ttype
            new_task.Uid = uid
            self.session.add(new_task)
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

    def get_uid_by_tid(self, tid):
        uid = None
        try:
            uid = self.session.query(model.Tasks.Uid).filter_by(Tid=tid).scalar()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return uid

    def update_taskstatus_by_tid(self, tid, tasks):
        try:
            self.session.query(model.Tasks).filter_by(Tid=tid).update(tasks)
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

    def get_ttype_by_tid(self, tid):
        ttype = None
        try:
            ttype = self.session.query(model.Tasks.Ttype).filter_by(Tid=tid).scalar()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return ttype

    def new_task_user(self, tid, uid):
        """
        :param tid:
        :param uid:
        :return:
        """
        try:
            new_tasks_users = model.UTasks()
            new_tasks_users.UTid = str(uuid.uuid4())
            new_tasks_users.Tid = tid
            new_tasks_users.Uid = uid
            self.session.add(new_tasks_users)
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

    def get_task_list(self):
        task_list = None
        try:
            task_list = self.session.query(model.Tasks.Tid, model.Tasks.Tname, model.Tasks.Tcointype,
                                           model.Tasks.Tcoinnum, model.Tasks.Tstatus, model.Tasks.Ttype).all()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return task_list

    def get_task_abo_by_tid(self, tid):
        task_abo = None
        try:
            task_abo = self.session.query(model.Tasks.Tid, model.Tasks.Tcointype, model.Tasks.Tcoinnum,
                                          model.Tasks.Tname, model.Tasks.Tstatus, model.Tasks.Ttype, model.Tasks.Tabo,
                                          model.Tasks.Tlocation, model.Tasks.Uid).first()
        except Exception as e:
            print "=====================message========================"
            print e.message
            print "=====================message========================"
            self.session.rollback()
        finally:
            self.session.close()
        return task_abo