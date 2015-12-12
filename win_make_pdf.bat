@echo off

dvipdfmx -d 5 Thesis.dvi

start "Thesis" "Thesis.pdf"

pause