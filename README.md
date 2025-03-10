![Static Badge](https://img.shields.io/badge/Java-23-orange)
![Static Badge](https://img.shields.io/badge/Maven-4.0.0-red)
[![Maven Build & Tests](https://github.com/Beleg-6-EAP/demo-eda-ecommerce/actions/workflows/maven-build-test.yml/badge.svg)](https://github.com/Beleg-6-EAP/demo-monolith-ecommerce/actions/workflows/maven-build-test.yml)

# Event-Driven Architecture E-Commerce Demo

This repository provides a minimal example showcasing the Event-Driven Architecture in an E-Commerce setting.
This setting includes orders, payment and shipping.

This demo is part of an article on Enterprise Architecture-Patterns.
The article, including the complete explanation of the E-Commerce-Example, can be found [here](https://github.com/Beleg-6-EAP/Belegarbeit).

## Get running

Run the given `docker-compose`:
```bash
bash> sudo docker-compose up
```

It will start a web-server running on `http://localhost:8080` with the following endpoints:

- CreateOrder: `POST /api/orders`
- AllOrders: `GET /api/orders`
- AllPayments: `GET /api/payments`
- AllShipments: `GET /api/shipments`

We start with en empty database.
To create an order `POST` below request to `http://localhost:8080/api/orders`:

```json
{
  "id": "1234-5678-abcd-efgh",
  "userId": "1234-5678-8765-4321",
  "amount": 42.0,
  "status": "New"
}
```

This will initiate payment as well as shipment by publishing events to [Apache Kafka](https://kafka.apache.org/) (Event-Broker) and then consuming them.
You can observe the communication between the Spring Boot App (producer & consumer) and Kafka by looking at the command-line-output following the composed start of the containers.

Further you can request all orders, payments and shipments at their respective endpoints via `GET`.

It is worth noting, that this demo-application is entirely asynchronous and non-blocking.

## Troubleshooting

If there is any trouble or if you have any questions, feel free to open an issue!