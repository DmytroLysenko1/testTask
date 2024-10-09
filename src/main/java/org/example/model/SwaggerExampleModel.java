package org.example.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SwaggerExampleModel {
    public static final String BANK_ACCOUNT_MODEL = """
            {
              "id": 1,
              "bankNumber": "123456789",
              "type": "SAVINGS",
              "status": "ACTIVE",
              "dateStart": "2023-01-01",
              "dateEnd": "2023-12-31",
              "validUntil": "2024-01-01",
              "userId": 1,
              "detailAccounts": [
                {
                  "id": 1,
                  "reportingDate": "2023-01-01",
                  "sum": 1000.00,
                  "percentage": 5.0,
                  "discountRate": 2.0,
                  "totalSum": 1200.00,
                  "bankAccountId": 1
                }
              ]
            }""";

    public static final String USER_MODEL = """
            {
              "id": 1,
              "firstName": "John",
              "lastName": "Doe",
              "birthday": "1990-01-01T00:00:00",
              "dateOfRegistration": "2023-01-01",
              "openAccountsCount": 2,
              "closedAccountsCount": 1,
              "bankAccounts": [
                {
                  "id": 1,
                  "bankNumber": "123456789",
                  "type": "SAVINGS",
                  "status": "ACTIVE",
                  "dateStart": "2023-01-01",
                  "dateEnd": "2023-12-31",
                  "validUntil": "2024-01-01",
                  "userId": 1,
                  "detailAccounts": [
                    {
                      "id": 1,
                      "reportingDate": "2023-01-01",
                      "sum": 1000.00,
                      "percentage": 5.0,
                      "discountRate": 2.0,
                      "totalSum": 1200.00,
                      "bankAccountId": 1
                    }
                  ]
                }
              ]
            }""";

    public static final String DETAIL_ACCOUNT_MODEL = """
            {
              "id": 1,
              "reportingDate": "2023-01-01",
              "sum": 1000.00,
              "percentage": 5.0,
              "discountRate": 2.0,
              "totalSum": 1200.00,
              "bankAccountId": 1
            }""";
}
