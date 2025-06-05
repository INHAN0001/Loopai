# Ingestion API Service 

This is a Spring Boot-based asynchronous ingestion service that processes IDs in batches and provides status updates.

##  Features

- Accepts a list of IDs and priority (`HIGH`, `MEDIUM`, `LOW`)
- Splits IDs into batches of 3
- Processes batches asynchronously (1 every 5 seconds)
- Stores and returns ingestion status (batch-level and overall)

##  Technologies Used

- Java 17
- Spring Boot
- MongoDB Atlas
- Maven

##  API Endpoints

### POST `/ingest`
- Accepts: JSON payload `{ "ids": [1, 2, 3, 4], "priority": "HIGH" }`
- Returns: `{ "ingestion_id": "<uuid>" }`

### GET `/status/{ingestionId}`
- Returns the status of the ingestion request


