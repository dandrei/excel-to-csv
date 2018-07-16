@echo off
set cwd=%cd%

cd ..
call gradle build

echo Extracting
cd build/distributions
tar --keep-newer-files -xf excel-to-csv.tar
echo Done!

cd %cwd%