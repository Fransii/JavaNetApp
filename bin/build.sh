#!/usr/bin/env bash
PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PACKAGE_PATH="src/main/java/com/franek"
SOURCE_DIR="$( cd $PACKAGE_PATH && pwd )"

COMPILE_LIST=""
for file in `ls $SOURCE_DIR/*.java`; do
    COMPILE_LIST+="$file "
done

javac $COMPILE_LIST
echo "Compile Done!"