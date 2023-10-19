"C:\Program Files\Git\mingw64\bin\curl" ^
    -k -v ^
    -H "Accept: text/plain" ^
    --cert-type P12 --cert client.keystore.p12:password --key-type P12 --key client.keystore.p12:password ^
    https://10.26.8.5:9457/registration-web/registration?id=JWS1

