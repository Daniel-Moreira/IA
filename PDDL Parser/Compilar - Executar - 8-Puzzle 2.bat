:op1
@echo on
cls
javac Main.java
@echo off
pause
cls
java Main Testes/domainP.txt Testes/problemP2.txt
pause
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
Echo [1] Deseja executar novamente?
Echo [2] Deseja sair do programa?
Set /p choice=Digite o numero correspodente a sua opçao:
if '%choice%'=='2' goto op2
if '%choice%'=='1' goto op1
:op2