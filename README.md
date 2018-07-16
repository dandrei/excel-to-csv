# About
An Apache POI CLI wrapper.

## Motivation
Because every other Excel to CSV tool I've tried was lacking at least one essential feature I needed.

## Features:
- Automatically detects type (Office 2003 (XLS) & 2007 (XLSX) supported)
- Can handle mismatched file extensions (XLSX saved as XLS, and vice-versa).
- Will attempt multiple ways to open a file (no password, then with each of the supplied passwords)
- For spreadsheets with multiple sheets, will export each sheet independently (file naming convention: `spreadsheet.xls` will produce `spreadsheet.0.csv`, `spreadsheet.1.csv`, etc.)
- Can export to CSV in place or in a specified folder.

## How to run
The folder `./bin` contains the script `excel-to-csv` (both Linux and Windows versions).
```
usage: ./excel-to-csv.sh [-i <path>] [-p <password>] [-x <path>]
options:
 -i,--input <path>          File(s) or folder(s) to process
 -p,--password <password>   Password(s) to try
 -x,--export <path>         Export files to path. If no path is given, each file will be exported in place
```

### Common use cases:

Analyze file: 

`./excel-to-csv.sh -i myfile.xls`

Analyze file, try password: 

`./excel-to-csv.sh -i myfile.xls -p my_password`

Analyze file, extract in place: 

`./excel-to-csv.sh -i myfile.xlsx -x`

Analyze file, extract to `~/csv/`: 

`./excel-to-csv.sh -i myfile.xlsx -x ~/csv/`

Analyze all files in a directory and extract them to a specific folder:

```./excel-to-csv.sh -i ~/excel/ -x ~/csv/```


## Development
Gradle needs to be installed on the system for development.

The folder `./bin/` contains the script `compile` (both Linux and Windows versions), which automatically generates the binaries that are then ready to run.

Script internals: the `compile` script first calls `gradle build`, which generates a tarball, `excel-to-csv.tar`, under `./build/distributions`.
Then, the generated tarball is extracted under `./build/distributions/excel-to-csv/`.  

## Caveats
Sometimes runs out of memory on large files.

## TODO
Perhaps add JSON output support, if required.