FROM adbrowne/eventstore
MAINTAINER Ralph Winzinger <ralph@winzinger-online.de>

RUN mkdir -p /data/db
RUN mkdir -p /data/logs

ENV EVENTSTORE_EXT_TCP_HEARTBEAT_TIMEOUT 10000
ENV EVENTSTORE_INT_TCP_HEARTBEAT_TIMEOUT 10000