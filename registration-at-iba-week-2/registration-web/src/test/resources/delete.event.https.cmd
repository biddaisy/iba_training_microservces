"C:\Program Files\Git\mingw64\bin\curl" ^
    -v -k ^
    --cert-type P12 --cert client.keystore.p12:password --key-type P12 --key client.keystore.p12:web1sphere ^
    -X DELETE https://localhost:8443/registration-web/event/JWS1
