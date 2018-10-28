#!/bin/bash -e
#
# Copyright (c) 2018-present Sonatype, Inc. All rights reserved.
#
# This program is licensed to you under the Apache License Version 2.0,
# and you may not use this file except in compliance with the Apache License Version 2.0.
# You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the Apache License Version 2.0 is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
#

basename=`basename $0`
dirname=`dirname $0`
dirname=`cd "$dirname" && pwd`
cd "$dirname"

tmpdir=../tmp
if [ ! -d "$tmpdir" ]; then
  mkdir -p "$tmpdir"
fi

logdir=../log
if [ ! -d "$logdir" ]; then
  mkdir -p "$logdir"
fi
export SERVICE_LOGDIR="$logdir"

dbdir=../db
if [ ! -d "$dbdir" ]; then
  mkdir -p "$dbdir"
fi
export SERVICE_DBDIR="$dbdir"

#export SERVICE_LOG_LEVEL=DEBUG
export SERVICE_LOG_CONSOLE_THRESHOLD=DEBUG

exec java \
  -Djava.io.tmpdir="$tmpdir" \
  -Djava.awt.headless=true \
  -Djava.net.preferIPv4Stack=true \
  -jar ../lib/service.jar \
  "$@"
