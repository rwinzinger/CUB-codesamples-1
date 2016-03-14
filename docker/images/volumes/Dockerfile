FROM ubuntu:16.04
MAINTAINER Ralph Winzinger <ralph@winzinger-online.de>

RUN mkdir -p /var/cub-code-walkthrough/mongo/login/data/db
RUN mkdir -p /var/cub-code-walkthrough/mongo/customer/data/db
RUN mkdir -p /var/cub-code-walkthrough/mongo/article/data/db

VOLUME /var/cub-code-walkthrough/mongo/login/data/db
VOLUME /var/cub-code-walkthrough/mongo/customer/data/db
VOLUME /var/cub-code-walkthrough/mongo/article/data/db

ENTRYPOINT /usr/bin/tail -f /dev/null