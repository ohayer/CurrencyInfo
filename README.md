# Currency Info Application

## Opis projektu

Currency Info Application to aplikacja webowa składająca się z backendu napisanego w Spring Boot oraz frontendowej aplikacji Angular. Umożliwia obliczenie majątku osoby w wybranej walucie na podstawie danych z bazy danych oraz aktualnych kursów walut pobieranych z API NBP. Aplikacja zapewnia interfejs użytkownika do wprowadzania danych oraz wyświetlania wyników, a także dokumentację API za pomocą Swagger UI.

## Funkcjonalności

- **Obliczanie majątku osoby**: Użytkownik może wprowadzić imię i nazwisko oraz wybrać walutę z listy, aby obliczyć majątek w wybranej walucie.
- **Wyświetlanie historii zapytań**: Możliwość przeglądania listy wszystkich wcześniejszych zapytań wraz z wynikami.
- **Dokumentacja API**: Backend udostępnia interfejs Swagger UI do przeglądania i testowania endpointów.
- **Obsługa wyjątków**: Aplikacja zwraca odpowiednie kody statusu HTTP oraz komunikaty błędów w przypadku wystąpienia problemów.

## Wymagania wstępne

- **Java 17**
- **Maven**
- **Node.js** (zalecana wersja 14.x lub wyższa)
- **Angular CLI** (do uruchamiania aplikacji frontendowej)
- **MySQL** (na porcie 3306)
- **Dostęp do internetu** (do komunikacji z API NBP)

## Instalacja i uruchomienie

### 1. Konfiguracja bazy danych

1. **Zainstaluj MySQL** i upewnij się, że działa na porcie **3306**.

2. **Utwórz bazę danych** o nazwie np. `currency_info_db`.

3. **Zaimportuj przykładowe dane**:

   - W głównym katalogu projektu znajduje się plik `dump.sql`.
   - Zaimportuj ten plik do swojej bazy danych, aby wgrać przykładowe dane testowe.

   Przykład komendy:

   ```bash
   mysql -u your_username -p currency_info_db < dump.sql

4. **Konfiguracja połączenia z bazą danych:

    - Upewnij się, że w pliku application.properties (lub application.yml) w backendzie są poprawnie ustawione parametry połączenia z bazą danych, takie jak nazwa użytkownika, hasło oraz nazwa bazy danych.

    Przykład application.properties:

    ```bash
    spring.datasource.url=jdbc:mysql://localhost:3306/currency_info_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update

### 2. Uruchomienie backendu

1. **Sklonuj repozytorium:**

    ````bash
    git clone https://github.com/ohayer/CurrencyInfo.git

2. **Przejdź do katalogu backendu**

    ````bash
    cd CurrencyInfo

3. **Zbuduj projekt przy użyciu Mavena:**

    ````bash
    mvn clean install

4. **Uruchom aplikację:**

    ````bash
    mvn spring-boot:run

5. **Sprawdź czy backend działa:**

    - Otwórz przeglądarkę i przejdź do http://localhost:8080/swagger-ui.html, aby zobaczyć dokumentację API.

### 3. Uruchomienie frontendowej aplikacji Angular

1. **Przejdź do katalogu projektu frontendowego:**

    ````bash
    cd frontend


2. **Zainstaluj zależności**

    ````bash
    npm install


3. **Uruchom aplikację w trybie deweloperskim:**

    ````bash
    ng serve

4. **Otwórz przeglądarkę i przejdź do http://localhost:4200/, aby zobaczyć działającą aplikację.**

## Korzystanie z aplikacji

- **Obliczanie majątku:**
1. Na stronie głównej wprowadź swoje imię i nazwisko oraz wybierz walutę z listy.
2. Kliknij przycisk "Wyślij".
3. Aplikacja wyświetli Twój majątek w wybranej walucie.

- **Przeglądanie historii zapytań:**
1. Przejdź do sekcji "Historia zapytań" (np. poprzez odpowiedni link lub menu).
2. Zobaczysz tabelę z listą wszystkich wcześniejszych zapytań.

## Testowanie API

- Możesz testować backendowe API bezpośrednio za pomocą Swagger UI dostępnego pod adresem http://localhost:8080/swagger-ui.html.

- Możesz również użyć narzędzi takich jak Postman lub curl do wysyłania żądań do API.