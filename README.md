**Предусловия для запуска тестов:**
- на ПК должны быть установлены браузер Chrome, IntelliJ IDEA, Java 11, Docker Desktop.

**Запуск проекта и тестов:**
- Клонируем проект с github
- Запустить Docker desktop на ПК
- Открыть проект в IntelliJ IDEA
- В терминале: выполнить команду docker compose up -d (фоновый режим)
- запустить приложение командой ```java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:4449/app```
(если используем БД sql) или
```java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app```, а так же 
перекомментировать 15 и 16 строки в DataHelper (если используем БД PostgreSql). Повторить попытку запуска приложения в случае возникновения
ошибки. 
- Проверить успешность запуска SUT по адресу http://localhost:8080/ - страница должна открываться в браузере
- В терминале: Запустить тесты командой ```./gradlew clean test.```
- Дождаться выполнения ВСЕХ тестов
- Для генерации отчета Allure, необходимо выполнить в терминале команду ```/gradlew allureServe```. Отчет автоматически 
откроется в браузере 