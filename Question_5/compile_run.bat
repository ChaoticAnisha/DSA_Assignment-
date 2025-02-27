@echo off
REM Compile all Java files in the src directory and output the class files to the out directory
javac src/*.java -d out
REM Run the Main class from the Question_5.src package
java -cp out Question_5.src.Main
REM Pause the command prompt to keep it open after the program runs
pause
