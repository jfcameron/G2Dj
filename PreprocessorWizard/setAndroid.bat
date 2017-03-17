@echo off
perl ./PreprocessorWizard.pl -List MyFiles.txt -Extns c -DoFile Android.pl -BackupDir MyBackupDir
::perl ./PreprocessorWizard.pl -h
set /P c=Press enter to continue...