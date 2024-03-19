# Spring-Boot-Vault-Learn
  ## Turkish

Not: Vault kurulum evreleri dokümana dahil değildir.

### Kurulum

1-Vault ekranında Secrets-> Secret Engines -> Enable New Engine  işlemlerini gerçekleştirerek yeni bir kayıt oluşturuyoruz.

![Vault-1](https://github.com/muharremkoc/spring-boot-vault-learn/assets/80245013/59aefe04-62e0-427e-a1e3-fd28140da90a)


2-Kaydımızı oluşturduktan sonra Secrets -> Create Secret işlemlerini gerçekleştirerek Yeni bir uygulama oluşturuyoruz.

![Vault-2](https://github.com/muharremkoc/spring-boot-vault-learn/assets/80245013/bc694e24-2469-4baa-9f7f-a03fd12592e1)


3- Uygulamamızın içerisine giriyoruz ve Şifrelemek istediğimiz Değerleri "Anahtar->Değer" şeklinde kaydediyoruz.

### Kullanım

1-İlk olarak temel Vault tanımlamalarını gerçekleştiriyoruz.

 application.properties
```java
#Vault
spring.cloud.vault.uri=http://127.0.0.1:8200
spring.cloud.vault.authentication=token
spring.cloud.vault.kv.enabled=true
spring.cloud.vault.kv.application-name=secret
spring.cloud.vault.kv.backend=kv
spring.cloud.vault.host=127.0.0.1
spring.cloud.vault.port=8200
spring.cloud.vault.token=${VAULT_TOKEN}  //Bu alanı kurulum aşamasında elde ediyoruz.Lütfen json çıktısı almaya özen gösteriniz Sonrasında erişimimiz mevcut değildir.


##Cloud
spring.cloud.config.enabled=false
spring.config.import=optional:vault://

```

2-VaultConfig adında bir Config Nesnesi oluşturarak gerekli konfigürasyonları gerçekleştiriyoruz.

  VaultConfig.java
```java
    public VaultEndpoint vaultEndpoint() {
        VaultEndpoint vaultEndpoint = new VaultEndpoint();
        vaultEndpoint.setHost("localhost");
        vaultEndpoint.setPort(8200);
        vaultEndpoint.setScheme("http");//Bu düzenlemeyi yapmaz isek doğrudan https isteği yaptığı için vault'a istek atamıyoruz
        return vaultEndpoint;
    }
```

3-Daha sonra Vault-uygulamamızda tanımladığımız DB değerlerini tanımlıyoruz

 application-prod.properties 

```java
spring.datasource.url=jdbc:mysql://localhost:3306/userDB?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=${DBUSER}
spring.datasource.password=${DBPASS}
```

4-JWT kullanacağımız için JWT SECRET-KEY'e ihtiyaç duyacağımız için bu değeri'de Vault uygulamamızdan alacağız

 application.properties

 ```java
#Security
app.jwt.secret=${APP_JWT_SECRET}
```

## English

Note: Vault installation phases are not included in the document.

### Installation

1-We create a new record by performing Secrets-> Secret Engines -> Enable New Engine operations on the Vault screen.

![Vault-1](https://github.com/muharremkoc/spring-boot-vault-learn/assets/80245013/70b94def-e20f-4dea-b5c3-0613b15adac3)


2-After creating our record, we create a new application by performing Secrets -> Create Secret operations.

![Vault-2](https://github.com/muharremkoc/spring-boot-vault-learn/assets/80245013/482a0dc2-5c99-45ab-8c25-4ca3fddb21f2)


3- We enter our application and save the values we want to encrypt as "Key->Value".


![Vault-3](https://github.com/muharremkoc/spring-boot-vault-learn/assets/80245013/5cd73b48-bd7b-4e81-a508-90ae3e7e5c5d)


### Usage

1-First, we perform basic Vault definitions.

 application.properties
```java
#Vault
spring.cloud.vault.uri=http://127.0.0.1:8200
spring.cloud.vault.authentication=token
spring.cloud.vault.kv.enabled=true
spring.cloud.vault.kv.application-name=secret
spring.cloud.vault.kv.backend=kv
spring.cloud.vault.host=127.0.0.0.1
spring.cloud.vault.port=8200
spring.cloud.vault.token=${VAULT_TOKEN} //We get this field during the installation phase. Please take care to get json output.


##Cloud
spring.cloud.config.enabled=false
spring.config.import=optional:vault://

```

2-We create a Config Object called VaultConfig and perform the necessary configurations.

  VaultConfig.java
```java
    public VaultEndpoint vaultEndpoint() {
        VaultEndpoint vaultEndpoint = new VaultEndpoint();
        vaultEndpoint.setHost("localhost");
        vaultEndpoint.setPort(8200);
        vaultEndpoint.setScheme("http");// If we do not make this edit, we cannot assign a request to the vault because it makes a direct https request
        return vaultEndpoint;
    }
```

3-Then we define the DB values we defined in our Vault-application

 application-prod.properties 

```java
spring.datasource.url=jdbc:mysql://localhost:3306/userDB?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=${DBUSER}
spring.datasource.password=${DBPASS}
```

Since we will use 4-JWT, we will need JWT SECRET-KEY and we will get this value from our Vault application.

 application.properties

 ```java
#Security
app.jwt.secret=${APP_JWT_SECRET}
```


[Muharrem Koç](https://github.com/muharremkoc)
