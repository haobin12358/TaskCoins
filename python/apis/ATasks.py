# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
from flask_restful import Resource
from config.requests import apis_wrong, param_miss

class ATasks(Resource):
    def __init__(self):
        from control.CTasks import CTasks
        self.ctasks = CTasks()
    def get(self, tasks):
        print "==================================================="
        print "api name is {0}".format(tasks)
        print "==================================================="

        apis = {
            "task_list": "self.ctasks.task_list()",
            "task_abo": "self.ctasks.task_abo()"
        }

        if tasks not in apis:
            return param_miss
        return eval(apis[tasks])

    def post(self, tasks):
        print "==================================================="
        print "api name is {0}".format(tasks)
        print "==================================================="

        apis = {
            "new_task": "self.ctasks.new_task()",
            "update_task_status": "self.ctasks.update_task_status()",
            "get_task":"self.ctasks.get_task()"
        }

        if tasks not in apis:
            return param_miss
        return eval(apis[tasks])