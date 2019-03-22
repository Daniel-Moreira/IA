:op1
del *.class
@echo on
cls
javac Main.java
@echo off
pause
cls
java Main all saida.txt
pause
cls
Echo [1] Deseja executar novamente?
Echo [2] Deseja sair do programa?
Set /p choice=Digite o numero correspodente a sua opçao:
if '%choice%'=='2' goto op2
if '%choice%'=='1' goto op1
:op2