# hotels
Тестовое задание - приложение для бронирования отеля, выполненное по ТЗ

Данное приложение позволяет посмотреть информация об отеле, его комнатах, а так же выполнить бронирование одной из них. Информация для каждого экрана получается с использованием трех разных API. При разработке использовались Hilt, Navigation, Compose, Room, ViewModel.  
На первом экране представлена информация об отеле с возможностью перехода ко второму экрану, на котором представлены комнаты отеля.  
<img src=hotel_screen.png width = 25%>  
На втором экране приведен список комнат, с возможностью выбора одной из них.  
<img src=rooms_screen.png width = 25%>  
При выборе комнаты открывается экран бронирования. В нем реализованы макса в поле телефона, проверка правильности ввода почты, а так же проверка заполнения всех полей при нажатии кнопки "Оплатить". Так же имеется возможность добавить еще туристов.  
<img src=booking_screen_phone_number_mask.png width = 25%><img src=booking_screen_email_error.png width = 25%><img src=booking_screen_tourist_error.png width = 25%><img src=boking_screen_no_error.png width = 25%>  
При нажатии кнопки "Оплатить" переходим к финальному экрану, который уведомляет об успешном бронировании номера.  
<img src=success_screen.png width = 25%>  
