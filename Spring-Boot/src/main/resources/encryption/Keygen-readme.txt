To Generate a KeyStore
#public & private key genration
keytool -genkeypair -alias domain -keyalg RSA -keystore samlKeystore.jks -keysize 2048

#Cetification request to CA
keytool -certreq -alias domain -file domain.csr -keystore samlKeystore.jks

#importing ssl certification to keystore
keytool -importcert -alias domain -file ca.cer -keystore samlKeystore.jks

#install to truststore 
keytool -import -trustcacerts -alias idp.ssocircle.com -file idp.ssocircle.com.cer -keystore samlKeystore.jks

#List the keys in the keystore
keytool -list -keystore samlKeystore.jks

#List the content of the content of the keystore
keytool -list -v -keystore samlKeystore.jks

#delete certificate from keystore
keytool -delete -alias startcom -keystore samlKeystore.jks
 
