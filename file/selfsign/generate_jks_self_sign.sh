#!/bin/bash
# 生成密钥库和自签名证书
# 文件名 generate_client_jks.sh


#1 生成密钥库
keytool -genkeypair -keyalg RSA -keysize 2048 -dname "CN=client,OU=client,O=client,L=BJ,ST=BJ,C=CN" -alias client -keypass 123456 -keystore client.jks -storepass 123456


# 2 导出客户端条目为自签名证书
keytool -exportcert -file client.cer -alias client -keystore client.jks -storepass 123456


# 3 导入自签名证书到服务端信任库
keytool -importcert -file client.cer -alias client -keystore server_trust.jks -storepass cccccc -keypass cccccc




# 文件名 generate_server_jks.sh


# 1 生成密钥库
keytool -genkeypair -keyalg RSA -keysize 2048 -dname "CN=server,OU=server,O=server,L=BJ,ST=BJ,C=CN" -alias server -keypass cccccc -keystore server.jks -storepass cccccc


# 2 导出服务端条目为自签名证书
keytool -exportcert -file server.cer -alias server -keystore server.jks -storepass cccccc


# 3 导入自签名证书到客户端信任库
keytool -importcert -file server.cer -alias server -keystore client_trust.jks -storepass 123456 -keypass 123456