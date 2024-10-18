@echo ++++++++++++++++++++++++++++++++++++++++++++++++++++
@echo Build Sistema de Pedidos
@echo ++++++++++++++++++++++++++++++++++++++++++++++++
@echo 1 - Build Frontend
@echo ++++++++++++++++++++++++++++++++++++++++++++++++

cd..\frontend
rd  /s /q dist
call npm run build

@echo +++++++++++++++++++++++++++++++++++++
@echo  2- Copiando a pasta dist para o Back
@echo +++++++++++++++++++++++++++++++++++++

cd ..\backend\src\main\resources\static\app
rd  /s /q dist
mkdir dist

xcopy /s /q ..\..\..\..\..\..\frontend\dist\*.* .\dist


@echo +++++++++++++++++++++++++++++++++++++
@echo  3- Build do Backend
@echo +++++++++++++++++++++++++++++++++++++

cd ..\..\..\..\..\
call mvn clean package -DskipTests
dir

@echo +++++++++++++++++++++++++++++++++++++
@echo 4- Executar o JAR
@echo +++++++++++++++++++++++++++++++++++++

cd target
call java -jar sistemapedidos-0.0.1-SNAPSHOT.jar


