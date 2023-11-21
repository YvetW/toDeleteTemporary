#!/bin/bash

####### 根密钥库 (truststore)
# 1 生成root密钥库
keytool -genkeypair -keyalg RSA -keysize 2048 -dname "CN=root,OU=root,O=root,L=BJ,ST=BJ,C=CN" -alias root -keypass cccccc -keystore root.jks -storepass cccccc

# 2 导出root条目为root自签名证书root.cer
keytool -exportcert -alias root -keystore root.jks -file root.cer -storepass cccccc

####### 服务端的密钥库

# 3 生成服务端密钥库
keytool -genkey -validity 365 -keyalg RSA -keysize 2048 -dname "CN=server,OU=server,O=server,L=BJ,ST=BJ,C=CN" -alias server -keypass 123456 -keystore server.jks -storepass 123456

# 4 导出条目得到签名请求文件
keytool -certreq -alias server -keystore server.jks -file server.csr -storepass 123456

# 5 用root密钥库签名请求文件，生成服务端证书server.cer
keytool -gencert -alias root -keystore root.jks -infile server.csr -outfile server.cer -storepass cccccc

# 6 导入root自签名证书root.cer到服务端密钥库server.jks
keytool -importcert -file root.cer -alias root -keystore server.jks -storepass 123456

# 7 导入服务端证书server.cer到服务端密钥库server.jks
keytool -importcert -file server.cer -alias server -keystore server.jks -storepass 123456

####### 客户端的密钥库

# 8 生成客户端密钥库
keytool -genkey -validity 365 -keyalg RSA -keysize 2048 -dname "CN=client,OU=client,O=client,L=BJ,ST=BJ,C=CN" -alias client -keypass 654321 -keystore client.jks -storepass 654321

# 9 导出条目得到签名请求文件
keytool -certreq -alias client -keystore client.jks -file client.csr -storepass 654321

# 10 用root密钥库签名请求文件，生成客户端证书client.cer
keytool -gencert -alias root -keystore root.jks -infile client.csr -outfile client.cer -storepass cccccc

# 11 导入root自签名证书root.cer到客户端密钥库client.jks
keytool -importcert -file root.cer -alias root -keystore client.jks -storepass 654321

# 12 导入客户端证书client.cer到客户端密钥库client.jks
keytool -importcert -file client.cer -alias client -keystore client.jks -storepass 654321
