# Login With OTP

## How to run this Prject in local

### Pre-Requisites
- Docker

### Steps
- Create a **.env file in your project directory** (if it doesn't already exist).
- Add the following sensitive env variables to the **.env** file.

    ```
    DATABASE_URL=jdbc:mysql://localhost/world
    DATABASE_PASSWORD=test
    DATABASE_USER=root
    EMAIL=sankar.n708@gmail.com
    EMAIL_PASSWORD=your_app_password
    ```
- Import **[db/world.sql](./db/world.sql)** in your SQL DB System.
- Run ```docker-compose up -d```
- To check logs, RUN ```docker logs <container-id>```
- Go to the browser, Load ```http://localhost:8445```