#!/bin/bash
#
# Compiles and runs the test suite.
#

scalac src/*.scala test/*.scala && scala script/test.scala
