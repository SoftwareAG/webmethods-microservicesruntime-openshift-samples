#!/bin/sh
#

docker build -t microruntime:10.3 -f Dockerfile .

docker tag microruntime:10.3 $(registry)/myproject/microruntime:10.3

oc login -u $(username) -p $(password)

oc project myproject

docker login -u $(username) -p $(oc whoami -t) $(registry)

docker push $(registry)/myproject/microruntime:10.3