
Чтобы запустить приложение создайте .env файл в корне проекта с такими properties:  

POSTGRES_URL=jdbc:postgresql://localhost:15432/ylab  
POSTGRES_USER=postgres  
POSTGRES_PASSWORD=postgres  
POSTGRES_DB=ylab  
PROD_SCHEMA=production  
MIGRATION_SCHEMA=migration   

Далее запустите образ postgresql в docker-compose.yml в корне проекта.
Далее зайдите в папку in -> Console и запустите App.


Данные для входа за тестового пользователя:   
email: user@mail.ru  
password: password  
