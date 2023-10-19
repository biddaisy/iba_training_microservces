set SERVER_FILENAME=server
set SERVER_ALIAS=server.ibagroup.eu

set CLIENT_FILENAME=client
set CLIENT_ALIAS=client.ibagroup.eu

set ORGUNIT=3RD_DEPT
set CITY=Vilnius
set STATE=Lithuania
set COUNTRY=Lithuania
set PASSWORD=web1sphere

set CLIENT_KEYSTORE_DIR=./client
set SERVER_KEYSTORE_DIR=./server

set CLIENT_KEYSTORE=%CLIENT_KEYSTORE_DIR%/%CLIENT_FILENAME%.keystore.p12
set SERVER_KEYSTORE=%SERVER_KEYSTORE_DIR%/%SERVER_FILENAME%.keystore.p12

set CLIENT_CERT=%CLIENT_KEYSTORE_DIR%/%CLIENT_FILENAME%.public.cer
set SERVER_CERT=%SERVER_KEYSTORE_DIR%/%SERVER_FILENAME%.public.cer

@set PATH=%PATH%;C:\IBM\WebSphere\AppServer\java\8.0\bin\
