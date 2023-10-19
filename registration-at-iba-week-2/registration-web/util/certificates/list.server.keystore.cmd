@call common.properties.cmd

keytool -list -v -storetype pkcs12 -keystore %SERVER_KEYSTORE% -storepass %PASSWORD% -J-Duser.language=en