@echo off
rem Compiles the sources and packages an executable jar (Windows).
if exist out rmdir /s /q out
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt || exit /b 1
del sources.txt
jar cfm book-catalogue-manager.jar src\META-INF\MANIFEST.MF -C out .
echo Built book-catalogue-manager.jar. Run it with: java -jar book-catalogue-manager.jar --demo
