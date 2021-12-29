# Процедура запуска тестов

- Запустить докер: docker-compose up в терминале IDEA
- Запустить jar файл из корневой директории проекта в PowerShell: java -jar ./artifacts/aqa-shop.jar
- Запустить сервер в директории qa-diploma-master/gate-simulator в PowerShell: npm start. При старте будет выведена информация о валидных и невалидных карточках
- Запустить тесты в IDEA (FormTestV1 будут выполняться корректно, а DataTestV1 нет).

Также можно подключиьтся после это к базе данных через HeidiSQL (app/pass/3306).
