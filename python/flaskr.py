# *- coding:utf8 *-

from flask import Flask
import flask_restful
from apis.AUsers import AUsers
from apis.ATasks import ATasks
from apis.AUpays import AUpays

app = Flask(__name__)
api = flask_restful.Api(app)

api.add_resource(AUsers, "/wyc/users/<string:users>")
api.add_resource(ATasks, "/wyc/tasks/<string:tasks>")
#api.add_resource(AUpays, "/wyc/upays/<string:upays>")

if __name__ == '__main__':
    app.run('0.0.0.0', 7443, debug=True)
