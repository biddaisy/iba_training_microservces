@call common.properties.cmd

@rem  Generate a client and server RSA 2048 key pair
keytool -genkeypair -alias %CLIENT_ALIAS% -keyalg RSA -keysize 2048 -dname "cn=%CLIENT_ALIAS%,ou=%ORGUNIT%,o=ibagroup.eu,l=%CITY%,s=%STATE%,c=%COUNTRY%" -keypass %PASSWORD% -keystore %CLIENT_KEYSTORE% -storepass %PASSWORD% -validity 3650 -sigalg SHA256withRSA -storetype pkcs12
keytool -genkeypair -alias %SERVER_ALIAS% -keyalg RSA -keysize 2048 -dname "cn=%SERVER_ALIAS%,ou=%ORGUNIT%,o=ibagroup.eu,l=%CITY%,s=%STATE%,c=%COUNTRY%" -keypass %PASSWORD% -keystore %SERVER_KEYSTORE% -storepass %PASSWORD% -validity 3650 -sigalg SHA256withRSA -storetype pkcs12

@pause 

@rem  Export public certificates for both the client and server
keytool -exportcert -alias %CLIENT_ALIAS% -file %CLIENT_CERT% -keystore %CLIENT_KEYSTORE% -storepass %PASSWORD%
keytool -exportcert -alias %SERVER_ALIAS% -file %SERVER_CERT% -keystore %SERVER_KEYSTORE% -storepass %PASSWORD%

@pause 

@rem Import the client and server public certificates into each others keystore
keytool -importcert -keystore %CLIENT_KEYSTORE% -alias %SERVER_ALIAS% -file %SERVER_CERT% -storepass %PASSWORD% -noprompt
keytool -importcert -keystore %SERVER_KEYSTORE% -alias %CLIENT_ALIAS% -file %CLIENT_CERT% -storepass %PASSWORD% -noprompt