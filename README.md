# Структура проекта
```
SERVICE_SUBSYSTEM
├───react_frontend
│   ├───public
│   └───src
│       ├───components
│       ├───constants
│       ├───layout
│       ├───pages
│       └───services
└───spring_backend
    └───src
        ├───main
        │   ├───java
        │   │   └───com
        │   │       └───example
        │   │           └───alinadiplom
        │   │               ├───config
        │   │               ├───controllers
        │   │               ├───DTO
        │   │               ├───exceptions
        │   │               ├───model
        │   │               ├───repositories
        │   │               └───services
        │   └───resources
        └───test
            └───java
                └───com
                    └───example
                        └───alinadiplom
```

# Запуск

## Запуск, перезапуск
Из корня проекта, где лежит файл docker-compose.yaml:
```bash
docker compose up --build -d
```
После этого смотрим логи:
```bash
docker compose logs 
```
Когда видим такую запись, подсистема готова к использованию, и доступна на портах `8080` для backend-части, `3000` для frontend-части, и `6789` для сервера postgres:
```log
>> webpack compiled successfully

>> Compiling...

>> Compiled successfully!

>> webpack compiled successfully
```

## Остановка, удаление
Из корня проекта, где лежит файл docker-compose.yaml:
```bash
docker compose stop <service_name / ничего>
```
Это останавливает работу конкретного сервиса, либо всей подсистемы.

---
Остановка и удаление контейнеров подсистемы:
```bash
docker compose down
```