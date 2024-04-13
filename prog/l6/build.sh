#!/bin/bash

write_build_gradle() {
    if [ "$1" == "server" ]; then
        className="Server"
        jarName="$1"
    else
        className="Client"
        jarName="$1"
    fi

    cat > ./build.gradle << EOF
apply plugin: 'java'
apply plugin: 'application'

mainClassName = 'main.java.$className'

repositories { 
    mavenCentral() 
}

jar {
    manifest { 
        attributes "Main-Class": "\$mainClassName"
    }

    archiveBaseName = '$jarName'
}

run {
    standardInput = System.in
}
EOF
}

build() {
    write_build_gradle $1
    ./gradlew jar
    mv "build/libs/$1.jar" .
}

if [ "$1" != "server" ] && [ "$1" != "client" ] && [ "$1" != "all" ]; then
    echo "usage: ./build.sh [server | client | all]"
    exit -1
fi

if [ "$1" == all ]; then
    build "server"
    build "client"
else
    build $1
fi
