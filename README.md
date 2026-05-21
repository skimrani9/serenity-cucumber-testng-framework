# Serenity BDD automation (Cucumber + Selenium)

UI automation against the QA Manthan app (default `https://qa.manthan.justo.co.in`).

---

## Technology stack

| Area | Library / tool |
|------|------------------|
| Language | Java **21** |
| Build | **Maven** |
| BDD / reporting | **Serenity BDD** `4.2.28` (`serenity-core`, `serenity-cucumber`, `serenity-junit`) |
| Gherkin | **Cucumber** `7.20.1` |
| Test runner | **JUnit 4** + `CucumberWithSerenity` |
| Browser | **Selenium** `4.27.0` + **WebDriverManager** `5.9.2` |
| Test data | **Gson** `2.11.0` (`testdata/users.json`, `testdata/lead_management.json`) |
| Classpath | **TestNG** `7.10.2` (Surefire is configured with **JUnit 4** so Cucumber runs) |

---

## Project structure

```text
serenity-cucumber-testng-framework/
├── pom.xml
├── serenity.conf
├── global.properties
├── testng.xml
├── local_run.bat
├── RunTestData.bat
├── checkstyle.xml
├── docs/
├── qualityGates/
└── src/
    ├── main/java/com/automation/framework/
    │   ├── config/
    │   ├── constants/
    │   ├── driver/
    │   └── helpers/
    └── test/
        ├── java/com/automation/framework/
        │   ├── helpers/
        │   ├── hooks/
        │   ├── pageObjects/
        │   ├── runner/
        │   ├── stepdefinitions/
        │   └── steps/
        └── resources/
            ├── config/
            ├── features/
            ├── testdata/
            └── cucumber.properties
```

See `docs/PROJECT_STRUCTURE.md` for layer details.

---

## Prerequisites

- **JDK 21**
- **Maven 3.8+**
- **Google Chrome** (default in `serenity.conf`)
- Application reachable at the configured base URL

---

## Setup

### 1. Run Maven from the repo root

All Maven commands run where **`pom.xml`** lives (this directory):

```bash
mvn clean verify
```

Windows shortcuts: `local_run.bat`, `RunTestData.bat`

### 2. Base URL and Serenity config

- **`serenity.conf`** (repo root) is added to the test classpath via `pom.xml` and sets `webdriver.base.url` for **qa** / **default**.
- **`src/test/resources/config/QAConfig.properties`** can override `webdriver.base.url`.
- Surefire sets **`environment=qa`** by default.

Examples:

```bash
mvn clean verify -Denvironment=qa
mvn clean verify -Dwebdriver.base.url=https://qa.manthan.justo.co.in
```

Browser (default **chrome**):

```bash
mvn clean verify -Dwebdriver.driver=firefox
```

### 3. Test credentials

Edit **`src/test/resources/testdata/users.json`** and **`src/test/resources/testdata/lead_management.json`**. Keys must match the strings used in feature files.

### 4. Build and run tests

```bash
mvn clean verify
```

- **`test`**: Surefire runs Cucumber via **`DotcomRunner`** (patterns `**/*Test.java` and `**/*Runner.java`).
- **`prepare-package`** / **`package`** / **`verify`**: **`serenity-maven-plugin`** runs **`aggregate`** and writes HTML under **`target/site/serenity/`**.

Surefire is set with **`testFailureIgnore`** so the build can still reach later phases and generate reports when scenarios fail.

#### Filter by Cucumber tag

```bash
# Recommended: runs tests then report
mvn clean verify -Denvironment=qa "-Dcucumber.filter.tags=@TC_03"

# Same idea (runs tests, then reaches prepare-package)
mvn clean package -Denvironment=qa "-Dcucumber.filter.tags=@TC_03"

# If you already use `mvn test`, chain the report goal:
mvn test serenity:aggregate -Denvironment=qa "-Dcucumber.filter.tags=@TC_03"
```

### 5. Open the Serenity report

After **`mvn clean verify`**, **`mvn clean package`**, or **`mvn test serenity:aggregate`**:

```text
target/site/serenity/index.html
```

Open **`index.html`** in a browser.
