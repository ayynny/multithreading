#!/bin/bash

cd com/code
javac ./Main.java ./Threading.java ./ImageProcessing.java ./DirectoryScanner.java
cd ../..
java com.code.Main ./sample-images output 4