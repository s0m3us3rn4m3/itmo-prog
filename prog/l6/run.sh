#!/bin/bash

file="$1"
./gradlew run --args=$file -q --console=plain

