## REST API Сервис + клиент

Задача: Создать REST API сервис, который будет принимать данные от "сенсора".

В данном проекте реализованно:
1. Создание REST API приложения с помощью Spring REST
2. Создание Java клиента, который бы отправлял данные на REST API приложение - с помощью класса RestTemplate.

• POST /sensors/registration


![image](https://user-images.githubusercontent.com/116525105/218654456-9725618b-1fde-4f83-b547-3786f89258bd.png)


Регестрирует новый сенсор в системе(БД). У сенсора имеется только одно поле (имя). Имя сенсора уникальное. 




• POST /measurements/add

![image](https://user-images.githubusercontent.com/116525105/218655117-2519fa20-9e23-414c-b50c-12cfb2067cee.png)


Добавляет новое измерение. value - температура, raining - идет дождь или нет и сенсор, который отправил данные.
Все значения сохраняются в БД.


•GET /measurements
Возвращает все измерения из БД


•GET /measurements/rainyDaysCount
Возвращает количество дождливых дней.


Также во второй части проекта реализован клиент, который регестрирует сервер, и 500 раз отправляет данные о погоде.
