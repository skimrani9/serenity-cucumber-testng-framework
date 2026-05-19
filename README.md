# Serenity BDD automation (Cucumber + Selenium)

Maven module **serenity-bdd-automation-framework** under this repo: UI tests against a local app (default `http://localhost:5173`).

---

## Technology stack

| Area | Library / tool |
|------|------------------|
| Language | Java **17** |
| Build | **Maven** |
| BDD / reporting | **Serenity BDD** `4.2.28` (`serenity-core`, `serenity-cucumber`, `serenity-junit`) |
| Gherkin | **Cucumber** `7.20.1` |
| Test runner | **JUnit 4** + `CucumberWithSerenity` |
| Browser | **Selenium** `4.27.0` + **WebDriverManager** `5.9.2` |
| Test data | **Gson** `2.11.0` (`testdata/users.json`) |
| Classpath | **TestNG** `7.10.2` (Surefire is configured with **JUnit 4** so Cucumber runs) |

---

## Project structure

```text
serenity-cucumber-testng-framework/
├── .gitignore
├── README.md
└── serenity-bdd-automation-framework/
    ├── pom.xml
    ├── serenity.conf
    └── src/test/
        ├── java/
        │   ├── runners/
        │   │   └── LoginTestRunner.java
        │   ├── stepdefinitions/
        │   │   └── LoginStepDefinitions.java
        │   ├── pages/
        │   │   └── LoginPage.java
        │   ├── steps/
        │   │   └── LoginActions.java
        │   └── utils/
        │       └── TestDataReader.java
        └── resources/
            ├── features/
            │   └── login.feature
            ├── testdata/
            │   └── users.json
            └── environments/
                └── qa.properties
```

---

## Prerequisites

- **JDK 17**
- **Maven 3.8+**
- **Google Chrome** (default in `serenity.conf`)
- Application reachable at the configured base URL (default **http://localhost:5173**)

---

## Setup

### 1. Use the Maven module directory

All Maven commands must run where **`pom.xml`** exists:

```bash
cd serenity-bdd-automation-framework
```

(From the repo root you can use: `mvn -f serenity-bdd-automation-framework/pom.xml ...`.)

### 2. Base URL and Serenity config

- **`serenity.conf`** (next to `pom.xml`) is added to the test classpath via `pom.xml` and sets `webdriver.base.url` for **qa** / **default** (default: `http://localhost:5173`).
- **`src/test/resources/environments/qa.properties`** can override `webdriver.base.url` for the **qa** environment.
- Surefire sets **`environment=qa`** by default.

Examples:

```bash
mvn clean verify -Denvironment=qa
mvn clean verify -Dwebdriver.base.url=http://localhost:3000
```

Browser (default **chrome**):

```bash
mvn clean verify -Dwebdriver.driver=firefox
```

### 3. Login route in code

`LoginPage` uses `@DefaultUrl("/")`. If the login screen is not at the site root, change it (for example `@DefaultUrl("/login")`).

### 4. Test credentials

Edit **`src/test/resources/testdata/users.json`**. Keys such as **`TC_LOGIN_001`** must match the strings used in **`login.feature`**. Put real credentials in **`TC_LOGIN_001`** for a successful login run.

### 5. Start the application

Start your web app before running UI tests so the configured **`webdriver.base.url`** responds.

### 6. Build and run tests

```bash
cd serenity-bdd-automation-framework
mvn clean verify
```

- **`test`**: Surefire runs Cucumber via **`DotcomRunner`** (patterns `**/*Test.java` and `**/*Runner.java`).
- **`prepare-package`** / **`package`** / **`verify`**: **`serenity-maven-plugin`** runs **`aggregate`** and writes HTML under **`target/site/serenity/`**.

Surefire is set with **`testFailureIgnore`** so the build can still reach later phases and generate reports when scenarios fail.

#### Why no HTML report after `mvn test`?

`mvn test` stops at the **`test`** phase. Serenity builds the HTML report in **`prepare-package`** (via this project’s `pom.xml`). Use one of these:

```bash
# Recommended: runs tests then report
mvn clean verify -Denvironment=qa "-Dcucumber.filter.tags=@TC_03"

# Same idea (runs tests, then reaches prepare-package)
mvn clean package -Denvironment=qa "-Dcucumber.filter.tags=@TC_03"

# If you already use `mvn test`, chain the report goal:
mvn test serenity:aggregate -Denvironment=qa "-Dcucumber.filter.tags=@TC_03"
```

### 7. Open the Serenity report

After **`mvn clean verify`**, **`mvn clean package`**, or **`mvn test serenity:aggregate`** (from the module directory):

```text
target/site/serenity/index.html
```

Full path example:

```text
serenity-bdd-automation-framework/target/site/serenity/index.html
```

Open **`index.html`** in a browser.
