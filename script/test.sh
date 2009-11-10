#!/bin/bash
#
# Compiles and runs the test suite.
#

fsc src/*.scala test/*.scala && scala script/test.scala
