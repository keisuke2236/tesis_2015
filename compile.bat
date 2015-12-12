@echo off

ebb.exe ./figure/*.png
xbb.exe ./figure/*.png

for /l %%v in (1,1,2) do platex Thesis

pause