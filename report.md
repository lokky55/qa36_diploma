# Отчёт по итогам тестирования

## Краткое описание

Было проведено автоматизированное тестирования приложения (веб -сервиса) по покупке тура.

Приложение предлагает купить тур двумя способами:
1. Обычная оплата по дебетовой карте
2. Оплата по средствам выдачи клиенту кредита по банковской карте

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
Приложение пересылает данные карт банковским сервисам
- сервису платежей (далее - Payment Gate)
- кредитному сервису (далее - Credit Gate)

Приложение сохраняет информацию о способе совершения платежа, а так же успешно ли он был совершён (не сохраняя данные карт).
Присутствует поддержка СУБД MySQL и PostgreSQL.

## Количество тест-кейсов

Всего выполнено 46 автоматизированных сценариев.

<image src="/Allure/Allure_report.png" alt="Allure_report.png">

## % успешных и не успешных тест-кейсов

* 35 успешных – что составляет 76 % от общего числа тест-кейсов
* 11 не успешных – что составляет 24 % от общего числа тест-кейсов

<image src="/Allure/Allure_report.png" alt="Allure_report.png">

## Общие рекомендации

* Необходимо исправить найденные баги в приложении.
[issues](https://github.com/lokky55/qa36_diploma/issues)

Баги можно посмотреть в отчётах:
* Gradle: `./build/reports/tests/test/index.html`
* Allure: в терминале ввести команду `./gradlew allureServe`