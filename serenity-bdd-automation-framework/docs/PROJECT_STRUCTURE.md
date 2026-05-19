# Project structure

This repository follows a **layered layout** inspired by enterprise UI automation frameworks, trimmed for **Serenity BDD + Cucumber**:

| Layer | Location | Purpose |
|-------|----------|---------|
| Core framework | `src/main/java/com/automation/framework/` | Driver bootstrap, timeouts, Selenium helpers (no Serenity coupling). |
| Tests | `src/test/java/com/automation/framework/` | Page objects (`pageObjects`), step definitions (`stepdefinitions`), Serenity `@Steps`, Cucumber hooks, runners. |
| Resources | `src/test/resources/` | `features/` (grouped), `config/`, `testdata/`, `cucumber.properties`. |

## Entry points

- **Primary (Serenity reports):** `com.automation.framework.runner.DotcomRunner` — Maven Surefire (`mvn verify`).
- **TestNG skeleton:** `testng.xml` + `runner.testng.FrameworkToolkitSanityTest` — optional IDE/TestNG runs. Serenity does not officially target TestNG for Cucumber; keep Cucumber on JUnit per runner above.

## Intentionally omitted (template-only)

Not required for the current Serenity project (remove/add when needed):

- Zephyr / `TestCaseUtils`
- ExtentReports / Spark (`extent.properties`, `spark-config.xml`)
- Database reporters / Hikari pools / Teams webhooks
- Duplicate-scenario Python scanners (`detect_duplicate_*.py`)
- Large multi-env CSV corpora (`TestData/` airline-style)

## Reference

Robot parity notes live under `docs/JUSTO_ROBOT_TC_REFERENCE.md` (moved from repo root).
