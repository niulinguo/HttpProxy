keytool -importcert -v -trustcacerts -alias baidu -file ./baidu.com.cer -keystore ./baidu.bks -storetype BKS -providerclass org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath ./bcprov-jdk15on-146.jar -storepass baidu






openssl x509 -inform DER -in /Users/lingo.niu/Desktop/baidu.com.cer -out /Users/lingo.niu/Desktop/baidu.com.crt