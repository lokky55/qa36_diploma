**Предусловия для запуска тестов:**
- на ПК должны быть установлены браузер Chrome, IntelliJ IDEA, Java 11, Docker Desktop.

**Запуск проекта и тестов:**
1) Клонируем проект с github
2) Запустить Docker desktop на ПК
3) Открыть проект в IntelliJ IDEA
4) В терминале: выполнить команду ```docker compose up -d``` (фоновый режим)
- запустить приложение командой 
```java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:4449/app```
(если используем БД sql) или
- ```java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app```, а так же 
перекомментировать 15 и 16 строки в DataHelper (если используем БД PostgreSql). 
- Повторить попытку запуска приложения в случае возникновения
ошибки. 
5) Проверить успешность запуска SUT по адресу http://localhost:8080/ - страница должна открываться в браузере
6) В терминале: Запустить тесты командой ```./gradlew clean test.```
7) Дождаться выполнения ВСЕХ тестов
8) Для генерации отчета Allure, необходимо выполнить в терминале команду ```./gradlew allureServe```. Отчет автоматически 
откроется в браузере. 