@call common.properties.cmd

keytool -list -v -storetype pkcs12 -keystore %CLIENT_KEYSTORE% -storepass %PASSWORD% -J-Duser.language=en