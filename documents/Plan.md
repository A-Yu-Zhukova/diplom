# Дипломная работа

## 1. Перечень автоматизируемых сценариев
- Покупка через кнопки "Купить" и "Купить в кредит" (отдельные тесты для каждой кнопки)
  - Проверка наличия кнопки на странице
  - Проверка правильного действия при нажатии на кнопку
    - Валидные данные (ФИО, телефон, e-mail, данные карты, ...)
      - Использование нескольких наборов данных через Faker
      - <b>Ожидаемый результат</b>: появление сообщения о успешном отправлении данных.
    - Пустые или невалидные данные (ФИО, телефон, e-mail, данные карты, ...)
      - Подготовка нескольких наборов данных
        - Неправильный телефон (наличий лишних символов, недостаточное количество цифр)
        - Неправильный e-mail (невалидный формат)
        - Неправильное ФИО (наличие лишних символов)
        - Неправильная карта (невалидный номер, просроченная дата)
        - Правильная, но заблокированная на сервере карта (банк должен отклонить операцию)
      - <b>Ожидаемый результат</b>: появление сообщения об ошибке по конкретным данным.
- Проверка работы серверных функций
  - Проверка поддержки MySQL или Postgres СУБД
  - Проверка корректности данных записанных в БД
    - Сравнение данных в БД с валидными внесенными данными через форму (кроме данных карты).
    - <b>Ожидаемый результат</b>: данные внесены и совпадают.
  - Проверка корректности данных записанных в тестовые сервисы Payment Gate и Credit Gate
    - Сравнение данных в БД с валидными внесенными данными через форму.
    - <b>Ожидаемый результат</b>: данные внесены и совпадают.

## 2. Перечень используемых инструментов
- Git - Система контроля версий.
  - Распределённая разработка.
  - Транзакционный подход в управлении пакетами.
  - Простота управления исходным кодом.
- IntelliJ IDEA. Платформа для удобного написания кода, в том числе для тестов.
  - Удобная среда разработки на Java.
- JUnit. Библиотека для создания авто-тестов.
  - Параллельный запуск тестов.
  - Подготовка данных (@BeforeEach, @BeforeAll, ...).
  - Создание динамических тестов.
- Allure. Фреймворк, предназначенный для создания подробных отчетов о выполнении тестов.
- Selenide. Фреймворк для автоматизированного тестирования интерфейсов веб-приложений.
  - Удобный синтаксис работы с HTML элементами.
  - Автоматическое решение большинства проблем с AJAX, ожиданиями и таймаутами.
  - Управление жизнедеятельностью браузера.
- Docker - Система для тестировани БД.
  - Простота и быстрое развертывание.
  - Облегчение процесса разработки, тестирования и развертывания приложений.
  - Запуск небезопасного кода.

## 3. Перечень и описание возможных рисков при автоматизации
- Отсутствие данных в БД после успешной валидации
- Неправильная валидация данных (успешная запись невалидных данных).
- Неправильная валидация данных (запрет на запись валидных данных).
- Возможные сложности с поиском правильных локаторов на страницах сайта.
- Неполная/отсутствующая документация по продукту
- Настройка интеграции между тестовыми объектами (БД, тестовые сервисы, основное приложение)

## 4. Интервальная оценка с учётом рисков (в часах)
- Подготовка окружения - 4 часа.
- Формирование перечня автотестов - 4 часа.
- Написание автотестов -  24 часа.
- Анализ результатов тестирования и составление отчета - 8 часа.
- Резерв времени под возможное возникновение рисков - 8 часов.

## 5. План сдачи работ
- Разработка автотестов - 08.07.2021
- Результаты тестов - 10.07.2021
- Отчет по автоматизации - 12.07.2021