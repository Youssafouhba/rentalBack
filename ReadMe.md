## Build the project
.\mvnw clean package -DskipTests

## Create The Docker Image
docker build -t :v1 .

## Display The created Image
docker images

## Run the Container
docker run -d -p 8036:8036 --name -v1 :v1

## Display The Running Container
docker ps





## MinIo
1- go to http://localhost:9001/login
login: minio  and pass: minio1234 (see .env file of docker compose)

2- go to identity >> users menu
create a user:
login : zyn pass: zyn@2024 role: read and write

3- edit the user and click on service account >> create
copy access key and secret key in application.prop

4- connect as zyn using login and pass and create your bucket

5- test using
http://localhost:8036/api/cloud/upload/bucket/your-default-bucket
form-data >> file + upload your file


## Generate certif using SSL for back end in two folders: localhost and prod

openssl genpkey -algorithm RSA -out key.pem -pkeyopt rsa_keygen_bits:2048
//zynkey
openssl req -new -key key.pem -out cert.csr -subj "/CN=YourIPOrLocalhost"
openssl x509 -req -days 365 -in cert.csr -signkey key.pem -out cert.pem
openssl pkcs12 -export -in cert.pem -inkey key.pem -out keystore.p12 -name yourAliasZynForExample
//genKey

## Generate certif using SSL for front end
//got to : frontend\zyn\ssl\localhost
openssl pkcs12 -in ./../../../../backend-ms1/zyn/ssl/localhost/keystore.p12 -out keyStore.pem -nodes
//zynkey
openssl rsa -in keyStore.pem -out key.pem
openssl x509 -in keyStore.pem -out cert.pem -days 365
//genKey

## Sonar
1- go to http://localhost:9000
login: admin  and pass: admin (by default and change login and pass)

2- go to http://localhost:9000/projects/create and click on create a local project

3- select: Use the global setting and click create

4- select: create localy

5- generate token

6- choose maven and copy the command


============================ INSTALL SSL USING LET'S INCRIPT ==============================

sudo apt install certbot python3-certbot-nginx

create sub domain api.zynerator.com for example

sudo certbot --nginx -d api.zynerator.com

--------------------------------------------------
in case of config prob (default.config in nginx :: /etc/nginx/sites-enabled/default.conf)

server {
server_name api.zynerator.com;


location / {
rewrite ^/(.*) $1 break;
proxy_pass "http://localhost:8036";
}

location /backend {
rewrite ^/backend(.*) $1 break;
proxy_pass "http://localhost:8090";
}

location /superadmin {
rewrite ^/superadmin(.*) $1 break;
proxy_pass "http://localhost:8030";
}

location /back {
proxy_pass "http://localhost:8036/back";
}


listen 443 ssl; # managed by Certbot
ssl_certificate /etc/letsencrypt/live/api.zynerator.com/fullchain.pem; # managed by Certbot
ssl_certificate_key /etc/letsencrypt/live/api.zynerator.com/privkey.pem; # managed by Certbot
include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot
}

--------------------------------------------
sudo certbot --nginx -d api.zynerator.com -d api.zynerator.com
sudo certbot renew --dry-run



