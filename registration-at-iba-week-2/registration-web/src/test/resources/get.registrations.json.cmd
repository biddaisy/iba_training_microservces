"C:\Program Files\Git\mingw64\bin\curl" ^
    -v -k ^
    -H "Accept: application/json" ^
    --cert-type P12 --cert client.keystore.p12:password --key-type P12 --key client.keystore.p12:web1sphere ^
    https://localhost:9443/registration-web/registration?eventId=JWS1
