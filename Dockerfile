FROM microruntime:v1

MAINTAINER SoftwareAG


#COPY ./packages/WxInterceptor /opt/softwareag/IntegrationServer/instances/default/packages/
COPY ./0000404754_MicroservicesRuntime_100.xml /opt/softwareag/IntegrationServer/instances/default/config/licenseKey.xml
COPY ./startup/application.properties /opt/softwareag/IntegrationServer/application.properties

RUN chmod -R 777 /opt/softwareag/	