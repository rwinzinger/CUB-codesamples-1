FROM sebp/elk
MAINTAINER Ralph Winzinger <ralph@winzinger-online.de>

#ADD filebeat.conf /etc/logstash/conf.d/02-beats-input.conf
ADD syslog.conf /etc/logstash/conf.d/10-syslog.conf
ADD gelf.conf /etc/logstash/conf.d/09-gelf.conf
