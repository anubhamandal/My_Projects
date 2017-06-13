import boto.ec2
import sys
import prettytable
import boto.utils
import time

auth = {"aws_access_key_id": "<id>", "aws_secret_access_key": "<key>"}

# to start an instance with given instance_id (i-......)
def startInstance(input_id):
    print "Starting the instance..."

    try:
        ec2 = boto.ec2.connect_to_region("us-west-2", **auth)

    except Exception, e1:
        error1 = "Error1: %s" % str(e1)
        return error1

    try:
        ec2.start_instances(instance_ids=input_id)
        return "successfully started instance"

    except Exception, e2:
        error2 = "Error2: %s" % str(e2)
        return error2


# to stop an instance with given instance_id (i-......)
def stopInstance(input_id):
    print "Stopping the instance..."

    try:
        ec2 = boto.ec2.connect_to_region("us-west-2", **auth)

    except Exception, e1:
        error1 = "Error1: %s" % str(e1)
        return error1

    try:
        ec2.stop_instances(instance_ids=input_id)
        return "successfully stopped instance"
    except Exception, e2:
        error2 = "Error2: %s" % str(e2)
        return error2


# to list all the instances, output is a dict with instance_id, tag_name and status
def listInstances():
    print "Listing all the instances"

    ec2 = boto.ec2.connect_to_region('us-west-2',**auth)
    #--- for the id on which this is running
    nm = boto.utils.get_instance_metadata()['public-hostname']
    print nm
    # all running instances
    reservations = ec2.get_all_instances()
    print 'EC2 INSTANCES'
    res = {}
    instances = [i for r in reservations for i in r.instances]
    for i in instances:
        print i.id, i.public_dns_name, i.state
        # to remove the current host from the list of available instances
        if i.public_dns_name != nm:
            res[i.id] = i.state, i.tags
    return res

# get the instance hostname to connect
def getInstance(instanceID):
    print "getting the instance hostname"

    ec2 = boto.ec2.connect_to_region('us-west-2',**auth)
    reservations = ec2.get_all_instances()

    instances = [i for r in reservations for i in r.instances]
    for i in instances:
        print i.id, i.public_dns_name, i.state
        if i.id == instanceID:
            return i.public_dns_name
    return "Not found"

# to create an instance with given tag_name
def createInstance(instance_name):
    ec2 = boto.ec2.connect_to_region('us-west-2',**auth)
    try:
        # ami id of the instance as image to spin new ec2 (eg ami-g284cd96)
        image_id = '<ami-id>'
        # create key and security grp in advance
        reservations = ec2.run_instances(image_id, key_name = '<key_name>', instance_type = 't2.micro', security_groups = ['<security-group-name>'])
        instance = reservations.instances[0]
        while instance.state != 'running':
            time.sleep(5)
            instance.update()
        ec2.create_tags([instance.id], {'Name': instance_name})
        return instance.public_dns_name
    except Exception, e3:
        error3 = "Error3: %s" % str(e3)
        return error3

# to terminate an instance with given instance_id (i-......)
def terminateInstance(input_id):
    print "Terminating instance"
    try:
        ec2 = boto.ec2.connect_to_region("us-west-2", **auth)

    except Exception, e1:
        error4 = "Error4: %s" % str(e1)
        return error4

    try:
        ec2.terminate_instances(instance_ids=input_id)
        return "successfully terminated instance"
    except Exception, e2:
        error5 = "Error5: %s" % str(e2)
        return error5
