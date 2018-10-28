<!--

    Copyright (c) 2018-present Sonatype, Inc. All rights reserved.

    This program is licensed to you under the Apache License Version 2.0,
    and you may not use this file except in compliance with the Apache License Version 2.0.
    You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.

    Unless required by applicable law or agreed to in writing,
    software distributed under the Apache License Version 2.0 is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.

-->
# Goodies: Grafeas Service

Implementation of [Grafeas](https://grafeas.io/) as a Java micro-service.

## TODO

* Browse operations take a `filter` but its not defined afaict what that is, and ref-impl doesn't even look at it
* Adjust isolation between REST <-> data; atm short-cut implementing logic in REST resources

## References

* https://grafeas.io/
* https://groups.google.com/forum/#!forum/grafeas-dev
* https://groups.google.com/forum/#!forum/grafeas-users
* https://groups.google.com/forum/#!forum/grafeas-announce
* https://docs.google.com/document/d/15EUSFFD-aqrizJHLVHT-Ds7PooqUscIvsASllR8a0wk
* https://cloud.google.com/container-analysis/api/reference/rest/
