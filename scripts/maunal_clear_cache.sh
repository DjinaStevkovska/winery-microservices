#!/bin/bash

# List of files and directories to remove
FILES_TO_REMOVE=(
    "javax"
    "xpp3"
    "commons-logging"
    "commons-configuration"
    "ognl"
    "commons-lang"
    "io"
    "asm"
    "antlr"
    "aopalliance"
    "jakarta"
    "ch"
    "com"
    "pl"
    "backport-util-concurrent"
    "commons-io"
    "org"
    "commons-jxpath"
    "classworlds"
    "commons-codec"
    "net"
    "xmlpull"
    "junit"
)

# Directory path
DIRECTORY_PATH=~/.m2/repository

# Iterate over each file/directory and remove it
for ITEM in "${FILES_TO_REMOVE[@]}"; do
    ITEM_PATH="$DIRECTORY_PATH/$ITEM"
    if [ -e "$ITEM_PATH" ]; then
        echo "Removing: $ITEM_PATH"
        rm -rf "$ITEM_PATH"
    else
        echo "Not found: $ITEM_PATH"
    fi
done