# Pokémon Stock Scraper (Kotlin + Spring Boot)

A small Spring Boot application written in Kotlin that scrapes Pokémon product availability from webshops and exposes the result via a simple API or runner.

The project is intentionally kept minimal and explicit, with a clear separation between:
- scraping (external data source)
- orchestration (service logic)
- API contract (controller + DTOs)

## Purpose

- Fetch HTML from webshop product pages
- Parse availability status (in stock / out of stock / unknown)
- Return structured results
- Keep scraping isolated from API and business logic

## Tech stack

- Kotlin
- Spring Boot
- Gradle (Kotlin DSL)
- Jsoup (HTML parsing)
- Spring HTTP client (or OkHttp)

## Getting started

### Prerequisites

- JDK 17+
- Git
- No local Gradle installation needed (wrapper included)

### Create a similar project (Spring Initializr)

If recreating from scratch:

- Project: Gradle (Kotlin)
- Language: Kotlin
- Spring Boot: 3.x
- Java: 17
- Dependencies:
  - Spring Web (only if exposing an API)

Then add Jsoup and HTTP client dependencies manually.

### Run the application

```bash
./gradlew bootRun
