"C:\Program Files\Git\mingw64\bin\curl" ^
     -v -k --cert-type P12 --cert client.keystore.p12:password --key-type P12 --key client.keystore.p12:password ^
     -H "Content-Type: application/json" ^
     -d @"workshop2.json" ^
     -X POST https://localhost:8443/registration-web/event