import boto.ec2
import sys
from flask import Flask
from functions import listInstances, startInstance, stopInstance, createInstance, terminateInstance, getInstance
import json

app = Flask(__name__)

@app.route('/')
def index():
#----------sanity check---------------
	return 'Hello! Welcome to Cluster Management'

# To get all instances
@app.route('/instances')
def get_instances():
    result = listInstances()
    return json.dumps(result)

# Get the selected instance hostname
@app.route('/instance/<id>')
def go_to_instance(id):
    result = getInstance(id)
    return json.dumps(result)

# to start an instance
@app.route('/instance/start/<id>')
def start_instance(id):
    res = startInstance(id)
    return json.dumps(res)

# to stop an instance
@app.route('/instance/stop/<id>')
def stop_instance(id):
    res = stopInstance(id)
    return json.dumps(res)

# to create a new instance
@app.route('/instance/create/<name>')
def create_instance(name):
    res = createInstance(name)
    dns_name = res + '/install/index.jsp'
    return json.dumps(dns_name)

# to terminate an instance
@app.route('/instance/terminate/<id>')
def terminate_instance(id):
    res = terminateInstance(id)
    return json.dumps(res)

if __name__ == "__main__":
	#HOSTNAME = 'mysqlserver'
	#database = CreateDB(hostname = HOSTNAME)
	app.run(host='0.0.0.0', port=8080, debug=True)
