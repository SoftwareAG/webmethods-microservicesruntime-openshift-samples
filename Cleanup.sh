#!/bin/sh
#

oc login -u developer -p manage
oc delete route/microsvcruntime
oc delete service/microsvcruntimesrv
oc delete dc/microsvcruntime
oc delete configmap/my-config
#oc delete imagestream/account
#docker rmi -f account:v1
#docker rmi -f 172.30.1.1:5000/btpn/account:v1
