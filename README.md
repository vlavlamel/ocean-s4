# ocean-s4
Best Hackathon

Данное приложение реализует основное и дополнительное задание, а именно презентует нашу команду,дает возможность редактировать
информацию об участнике, ставить рейтинг, а также добавлять нового участника.
В качестве back end использовался сервис Google Firebase. Мы не подключали Firebase, как библиотеку в приложение, а использовали
REST API*. Такой подход нам показался более подходящим, так как можно продемонстрировать умения работы с сетью.

Как оказалось позднее Firebase REST API не имеет возможности загружать файл, для этого существует Google Cloud Storage, поэтому в нашем приложении нет возможности загружать и изменять фотографии участников (нам самим было обидно)(.

На момент создания: Gradle 4.4 ; Android Studio 3.1.
Тестировали на устройствах: Samsung Galaxy S7 (API 24), Galaxy S5 mini (API 23); Xiaomi Redmi Pro 4 (API 23); 
Emulator Nexus 5(API 24) 
