#!/usr/bin/env sh
# Compiles the sources and packages an executable jar.
set -e
rm -rf out
javac -encoding UTF-8 -d out $(find src -name '*.java')
jar cfm book-catalogue-manager.jar src/META-INF/MANIFEST.MF -C out .
echo "Built book-catalogue-manager.jar. Run it with: java -jar book-catalogue-manager.jar --demo"
