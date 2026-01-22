@ECHO OFF
setlocal enabledelayedexpansion

REM create bin directory if it doesn't exist
if not exist "..\bin" mkdir "..\bin"

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
javac -cp "..\src\main\java" -Xlint:none -d "..\bin" "..\src\main\java\*.java"
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM Test 1: Standard
echo ---------------------------------------
echo Running test case: input.txt
java -classpath "..\bin" Nexus --test < input.txt > ACTUAL.txt
FC ACTUAL.txt EXPECTED.txt
IF ERRORLEVEL 1 (echo ********** TEST 1 FAILED **********) ELSE (echo ********** TEST 1 PASSED **********)

REM Test 2: Stress Test
echo ---------------------------------------
echo Running test case: input2.txt
java -classpath "..\bin" Nexus --test < input2.txt > ACTUAL2.txt
FC ACTUAL2.txt EXPECTED2.txt
IF ERRORLEVEL 1 (echo ********** TEST 2 FAILED **********) ELSE (echo ********** TEST 2 PASSED **********)

REM Test 3: Edge Cases
echo ---------------------------------------
echo Running test case: input3.txt
java -classpath "..\bin" Nexus --test < input3.txt > ACTUAL3.txt
FC ACTUAL3.txt EXPECTED3.txt
IF ERRORLEVEL 1 (echo ********** TEST 3 FAILED **********) ELSE (echo ********** TEST 3 PASSED **********)

pause

@REM REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
@REM java -classpath ..\bin Nexus --test < input.txt > ACTUAL.TXT
@REM
@REM REM compare the output to the expected output
@REM FC ACTUAL.TXT EXPECTED.TXT
