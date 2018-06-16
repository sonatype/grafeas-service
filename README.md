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
* Why is Swagger generating a "default" section?  Maybe due to use of intf + impls?

## References

### HTTP PATCH

* https://github.com/jersey/jersey/tree/master/examples/http-patch
* http://kingsfleet.blogspot.com/2014/02/transparent-patch-support-in-jax-rs-20.html
* https://github.com/java-json-tools/json-patch
* http://brianoneill.blogspot.com/2011/11/patch-methods-on-jax-rs.html
* https://github.com/tbugrara/dropwizard-patch
* https://stackoverflow.com/questions/17897171/how-to-have-a-patch-annotation-for-jax-rs