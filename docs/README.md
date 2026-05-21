# Serenity BDD automation framework

## Prerequisites

- JDK **21**
- Maven 3.9+
- Chrome (WebDriverManager resolves `chromedriver`)

## Run

```bash
mvn clean verify
```

Windows shortcut: `local_run.bat`

Filtered Cucumber execution example: `RunTestData.bat`

## Configuration

- **Serenity:** `serenity.conf` (base URL, browser).
- **Globals:** `global.properties` at repo root (mirrored onto test classpath).
- **Environment overrides:** `src/test/resources/config/QAConfig.properties`.

## Layout

See `docs/PROJECT_STRUCTURE.md`.

## Style

Checkstyle runs at `validate` using `checkstyle.xml`. Use `checkstyle-warn.xml` only if you introduce a secondary relaxed profile.
