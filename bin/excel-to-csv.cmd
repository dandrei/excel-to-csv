@echo off
set cwd=%cd%

cd ..
cd build/distributions/excel-to-csv/
call bin/excel-to-csv %*

cd %cwd%