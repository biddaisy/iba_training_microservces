"C:\Program Files\Git\mingw64\bin\curl" -v -k --cert-type P12 --cert client.keystore.p12:password --key-type P12 --key client.keystore.p12:password https://10.26.8.5:9457/confirmation/1

@rem pause 

@rem "C:\Program Files\Git\mingw64\bin\curl" -v -k --cert-type P12 --cert client.keystore.p12:web1sphere --key-type P12 --key client.keystore.p12:web1sphere https://localhost:8443/confirmation/2
