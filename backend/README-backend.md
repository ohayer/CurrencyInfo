# Currency Info API

## Opis Projektu
Currency Info API to aplikacja webowa zbudowana przy użyciu Spring Boot, która umożliwia obliczenie majątku osoby w oparciu o przeliczniki walutowe dostarczane przez API NBP. Projekt wykorzystuje RESTful API oraz dokumentację za pomocą OpenAPI (Swagger UI). Dodatkowo zawiera obsługę wyjątków i zwraca odpowiednie kody statusów HTTP.

## Funkcjonalności
- Pobieranie informacji o majątku osoby w wybranej walucie.
- Obsługa wyjątków z odpowiednimi komunikatami.
- Dokumentacja za pomocą OpenAPI (Swagger UI).
- Pobieranie danych z API NBP.

## Wymagania
- Java 17
- Maven 
- Spring Boot 3+
- Dostęp do internetu (do komunikacji z API NBP)

## Technologie
- **Spring Boot**: Framework do tworzenia aplikacji webowych.
- **Spring Data JPA**: Zarządzanie danymi przy użyciu ORM.
- **RestTemplate**: Wykonywanie żądań HTTP.
- **OpenAPI/Swagger**: Dokumentacja API.
- **Lombok**: Generowanie metod getter, setter, konstruktorów itp.

## Instalacja i Uruchomienie

Przed testowaniem aplikacji wgraj proszę przykładowe dane do bazy danych. 
W tym celu skorzystaj z pliku `dump.sql` znajdującego się w katalogu `src/main/resources`.

1. Sklonuj repozytorium:
    ```bash
    git clone https://github.com/ohayer/CurrencyInfo.git
    ```

2. Przejdź do katalogu projektu:
    ```bash
    cd currency-info-api
    ```

3. Zbuduj projekt przy użyciu Mavena:
    ```bash
    mvn clean install
    ```

4. Uruchom aplikację:
    ```bash
    mvn spring-boot:run
    ```

5. Otwórz przeglądarkę i przejdź do:
    ```
    http://localhost:8080/swagger-ui.html
    ```

## Dokumentacja API
Dokumentacja API jest dostępna pod adresem [Swagger UI](http://localhost:8080/swagger-ui.html).

### Przykładowe Endpointy

#### 1. Obliczenie majątku osoby w wybranej walucie
- **URL**: `/currencies/get-current-currency-value-command`
- **Metoda**: `POST`
- **Opis**: Oblicza majątek osoby na podstawie podanej waluty i imienia.
- **Przykładowe dane wejściowe**:
    ```json
    {
      "currency": "USD",
      "name": "Jan Kowalski"
    }
    ```
- **Przykładowa odpowiedź** (HTTP 200):
    ```json
    {
      "value": 4295.5432
    }
    ```

- **Błędy**:
    - **404**: `Person not found: Jan Kowalski`
    - **404**: `Currency not found: USD`
    - **500**: `An unexpected error occurred`

#### 2. Pobranie wszystkich zapisanych żądań
- **URL**: `/currencies/requests`
- **Metoda**: `GET`
- **Opis**: Zwraca listę wszystkich zapytań walutowych.
- **Przykładowa odpowiedź** (HTTP 200):
    ```json
    [
      {
        "id": 1,
        "currency": "USD",
        "name": "Jan Kowalski",
        "value": 4295.5432
      },
      {
        "id": 2,
        "currency": "EUR",
        "name": "Anna Nowak",
        "value": 1234.5678
      }
    ]
    ```
## Testowanie
Możesz przetestować działanie API za pomocą narzędzi takich jak Postman, curl lub bezpośrednio z poziomu Swagger UI.
