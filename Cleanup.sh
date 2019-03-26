#!/bin/sh
#

oc login -u developer -p manage
oc delete route/microsvcruntime
oc delete service/microsvcruntimesrv
oc delete dc/microsvcruntime
oc delete configmap/my-config

