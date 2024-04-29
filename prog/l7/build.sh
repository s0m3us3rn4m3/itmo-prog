#!/bin/bash

write_build_gradle() {
    type="$1"
    deps=""
    extra=""
    if [ "$type" == "server" ]; then
        className="Server"
        deps="dependencies {
    implementation 'org.postgresql:postgresql:42.5.0'
}" 
        extra="from {
        configurations.compileClasspath.collect { it.isDirectory() ? it : zipTree(it) }
    }"
    else
        className="Client"
    fi

    cat > ./build.gradle << EOF
apply plugin: 'java'
apply plugin: 'application'

repositories { 
    mavenCentral() 
}

$deps

jar {
    manifest { 
        attributes "Main-Class": "main.java.$className"
    }

    $extra

    archiveBaseName = '$type'
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
