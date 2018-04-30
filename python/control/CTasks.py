# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask import request
import json
from config.requests import param_miss, system_error

class CTasks():
    def __init__(self):
        from service.STasks import STasks
        self.stask = STasks()
        from service.SUsers import SUsers
        self.susers = SUsers()

    def task_list(self):
        args = request.args.to_dict()
        print "======================args==========================="
        print args
        print "======================args==========================="

        # 搜索

        tasks_list = self.stask.get_task_list()
        if not tasks_list:
            return system_error
        data = []
        for row in tasks_list:
            data_item = {}
            data_item["Tid"] = row.Tid
            data_item["Tname"] = row.Tname
            data_item["Tcointype"] = row.Tcointype
            data_item["Tcoinnum"] = row.Tcoinnum
            data_item["Tstatus"] = row.Tstatus
            data_item["Ttype"] = row.Ttype
            data.append(data_item)
        from config.requests import response_ok
        response_ok["data"] = data
        return response_ok

    def task_abo(self):
        args = request.args.to_dict()
        print "======================args==========================="
        print args
        print "======================args==========================="

        if "Tid" not in args:
            return param_miss

        data = {}
        task_abo = self.stask.get_task_abo_by_tid(args["Tid"])
        if not task_abo:
            return system_error
        data["Tid"] = task_abo.Tid
        data["Tname"] = task_abo.Tname
        data["Tcointype"] = task_abo.Tcointype
        data["Tcoinnum"] = task_abo.Tcoinnum
        data["Tstatus"] = task_abo.Tstatus
        data["Ttype"] = task_abo.Ttype
        data["Tabo"] = task_abo.Tabo
        data["Tlocation"] = task_abo.Tlocation
        data["Uname"] = self.susers.get_utruename_by_uid(task_abo.Uid)
        from config.requests import response_ok
        response_ok["data"] = data
        return response_ok

    def new_task(self):
        args = request.args.to_dict()
        print "======================args==========================="
        print args
        print "======================args==========================="
        data = request.data
        print "======================data==========================="
        print data
        print "======================data==========================="
        data = json.loads(data)
        if "Uid" not in args:
            return param_miss

        Uid = args["Uid"]

        Ustatus = self.susers.get_ustatus_by_uid(Uid)
        if not Ustatus:
            return system_error

        if int(Ustatus) != 103:
            from config.requests import subject_not_pass
            return subject_not_pass

        tasks = {}
        if "Tname" in data:
            tasks["Tname"] = data["Tname"]
        if "Tcoinnum" in data:
            tasks["Tcoinnum"] = data["Tcoinnum"]
        if "Tlocation" in data:
            tasks["Tlocation"] = data["Tlocation"]
        if "Tabo" in data:
            tasks["Tabo"] = data["Tabo"]
        if "Ttype" in data:
            tasks["Ttype"] = data["Ttype"]
        if tasks == {}:
            return param_miss
        tasks["Uid"] = Uid
        tasks["Tstatus"] = 601
        if "Tcointype" in data:
            tasks["Tcointype"] = data["Tcointype"]
        else:
            tasks["Tcointype"] = 401

        add_task = self.stask.add_a_task(tasks["Tname"], tasks["Tcointype"], tasks["Tcoinnum"], tasks["Tlocation"],
                                         tasks["Tabo"], tasks["Tstatus"], tasks["Ttype"], tasks["Uid"])

        if not add_task:
            return system_error
        from config.requests import create_task_success
        return create_task_success

    def update_task_status(self):
        args = request.args.to_dict()
        print "======================args==========================="
        print args
        print "======================args==========================="
        data = request.data
        print "======================data==========================="
        print data
        print "======================data==========================="
        data = json.loads(data)
        if "Uid" not in args or "Tid" not in args:
            return param_miss

        Uid = args["Uid"]
        Tid = args["Tid"]

        uid = self.stask.get_uid_by_tid(Tid)
        if uid != Uid:
            from config.requests import no_permission
            return no_permission

        if "Tstatus" not in data:
            return param_miss

        Tstatus = data["Tstatus"]

        status = [603, 604]
        if Tstatus not in status:
            return param_miss
        task_status = {}
        task_status["Tstatus"] = Tstatus

        update_task = self.stask.update_taskstatus_by_tid(Tid, task_status)

        if not update_task:
            return system_error

        from config.requests import update_task_success
        return update_task_success

    def get_task(self):
        args = request.args.to_dict()
        print "======================args==========================="
        print args
        print "======================args==========================="
        data = request.data
        print "======================data==========================="
        print data
        print "======================data==========================="
        data = json.loads(data)
        if "Uid" not in args or "Tid" not in args:
            return param_miss

        Uid = args["Uid"]
        Tid = args["Tid"]

        uid = self.stask.get_uid_by_tid(Tid)
        if uid == Uid:
            from config.requests import no_permission
            return no_permission

        ttype = self.stask.get_ttype_by_tid(Tid)
        if ttype == 502:
            task_status = {}
            task_status["Tstatus"] = 602
            update_task = self.stask.update_taskstatus_by_tid(Tid, task_status)
            if not update_task:
                return system_error
        add_task_user = self.stask.new_task_user(Tid, Uid)
        if not add_task_user:
            return system_error

        from config.requests import get_task_success
        return get_task_success