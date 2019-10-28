#!/bin/bash
if [ -n "$DIR" ];
then
 sed -i s\#./\#$DIR\#g /redis.conf
fi

if [ ! -n "$REDIS_PORT" ];
then
  redis-server /redis.conf
else
 sed -i 's#6379#'$REDIS_PORT'#g' /redis.conf && redis-server /redis.conf
fi