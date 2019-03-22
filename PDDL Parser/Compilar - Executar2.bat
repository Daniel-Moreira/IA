@setlocal enableextensions enabledelayedexpansion
@echo on
cls
javac Main.java
@echo off
pause
cls
for /f "tokens=*" %%a in ('dir Testes\Robo\*.txt /b') do (
    echo ---------------------------
    echo filename: %%a
    java Main Testes/domain.txt Testes/Robo/%%a
    pause
    cls
)
endlocal
cls
del *.class
cd Busca
del *.class
cd..
cd Parser 
del *.class
cd Dominio
del *.class
cd..
cd Excecoes
del *.class
cd..
cd Problema
del *class
cd..
cd..
cls