@echo Build do Sistema de pedidos
::@echo off

@echo ===================================
@echo Build frontend
@echo ===================================
cd ..\frontend
rd /s /q dist
call npm run build

@echo ===================================
@echo Movendo pasta dist para o backend
@echo ===================================

cd ..\backend\src\main\resources\static\app
rd /s /q dist
mkdir dist
xcopy /s /q ..\..\..\..\..\..\frontend\dist\*.* .\dist
cd ..\..\..\..\..\

@echo ===================================
@echo Build backend
@echo ===================================
call mvn clean package -Dmaven.test.skip=true
::dir
call java -jar .\target\sistemapedidos-0.0.1-SNAPSHOT.jar
