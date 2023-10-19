"C:\Program Files\Git\mingw64\bin\curl" ^
    -v -k ^
    --cert-type P12 --cert client.keystore.p12:password --key-type P12 --key client.keystore.p12:password ^
    -X DELETE https://10.26.8.5:9457/registration-web/event/1
