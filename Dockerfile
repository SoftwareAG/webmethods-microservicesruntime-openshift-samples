FROM store/softwareag/webmethods-microservicesruntime:10.3

MAINTAINER SoftwareAG


COPY ./packages/WxInterceptor /opt/softwareag/IntegrationServer/instances/default/packages/
COPY ./0000404754_MicroservicesRuntime_100.xml /opt/softwareag/IntegrationServer/instances/default/config/licenseKey.xml
COPY ./application.properties /opt/softwareag/IntegrationServer/application.properties

RUN chmod -R 777 /opt/softwareag/	