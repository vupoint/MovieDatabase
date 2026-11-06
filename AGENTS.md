# Repository guidelines

## Purpose and priorities

MovieDatabase is an open-source Android app for learning and experimenting with current Android technologies using The Movie Database API. It is not a production service. Prefer changes that make the project useful for learning and maintenance without adding unnecessary infrastructure.

Security is the first priority. The project's educational purpose does not justify exposing credentials, weakening security controls, or ignoring unsafe dependencies.

## Security

- Treat source code, commits, PRs, CI logs, screenshots, and uploaded artifacts as public.
- Never commit or publish real API keys, tokens, passwords, private signing keys, personal data, or local machine configuration. Use placeholders in examples and synthetic data in tests.
- Keep local TMDB credentials in the ignored `external.properties` file, using the `movie.database.api.key` property described in `README.md`. Do not hardcode credentials in Kotlin, resources, or build scripts. Values embedded in an APK, including `BuildConfig`, are not secret storage.
- The checked-in `debug.keystore` and its configuration are public development material. Never use them as private release credentials or replace them with private signing material.
- Preserve validation at external boundaries. Review changes to exported Android components, permissions, network traffic, logging, and local storage for unintended exposure.
- Use trusted dependency repositories and explicit dependency versions. Do not disable TLS verification, security checks, or tests to make a build pass.
- Before committing or publishing, inspect the complete diff and any attachments for sensitive content. If an existing secret is discovered, report its location without reproducing its value; removing it from a new commit does not revoke it or remove it from history.

## Branches, commits, and pull requests

- All work must use a separate branch and reach `develop` through a pull request. Do not commit or push directly to `develop` or `master` unless the user explicitly authorizes an exception for that specific task.
- Check the current branch and working tree before editing. Preserve unrelated changes and do not reset, overwrite, or rewrite other contributors' work.
- Create a descriptive branch from `develop`; use `codex/` as the prefix for Codex-created branches.
- Keep commits focused. Follow the repository's concise, imperative English style, such as `Update Gradle and Android Gradle Plugin`.
- Read relevant recent PRs before writing a PR. Existing PRs commonly use a `## Changed` section with short bullet points. Include the reason for the change, actual validation results, and any remaining limitations when relevant.
- Set the PR base to `develop`. Creating a PR does not authorize merging it, force-pushing, publishing a release, or deploying anything.

## Language

Write all authored repository text in English. This includes commit messages, PR titles and bodies, review comments, code comments, documentation, UI strings, log messages, error messages, and test descriptions. Preserve identifiers and data required by external contracts.

## Project structure

The app uses Kotlin, Jetpack Compose, ViewModels, coroutines and Flow, Hilt, Room, DataStore, Paging, Retrofit, OkHttp, Moshi, and Coil. Follow the existing MVVM and unidirectional state flow patterns in the affected feature.

| Location | Responsibility |
| --- | --- |
| `app/` | Application entry point, navigation, dependency wiring, Android startup components, and architecture tests. |
| `feature/home/`, `feature/search/`, `feature/setting/` | Feature screens, ViewModels, and feature-specific presentation logic. |
| `feature/common-ui/` | UI and presentation behavior shared by features. |
| `core/domain/` | Domain models, repository contracts, preference contracts, and persistence contracts. |
| `core/data/` | Repository implementations, API wiring, Room database, and debug/release integrations. |
| `core/api/` | TMDB API interfaces, network client, and transport DTOs. |
| `core/model/` | UI models and mapping logic. |
| `core/preferences/` | Android DataStore implementation of preference contracts. |
| `core/designsystem/`, `core/resources/`, `core/widget/` | Theme, shared Android resources, and reusable UI widgets. |
| `core/common/`, `core/common-coroutines/` | Shared Android utilities and coroutine helpers. |
| `test/base/`, `test/common/`, `test/mock/` | Shared test rules, helpers, fakes, and response fixtures. |
| `build-logic/` | Included Gradle build containing shared convention plugins. |

Use `settings.gradle` and module build files as the source of truth for module membership and dependencies. `MODULES.md` provides a generated overview and can become stale. Keep domain code independent of presentation and data implementations, as checked by `app/src/test/java/io/viewpoint/moviedatabase/ArchitectureTest.kt`.

## Making changes

- Read the affected implementation, callers, tests, and build configuration before editing. Reuse existing components and conventions; avoid unrelated cleanup or speculative abstractions.
- Keep dependency versions in `gradle/libs.versions.toml` and shared build behavior in `build-logic/convention/`. Preserve the surrounding Groovy or Kotlin DSL style rather than converting files incidentally.
- For toolchain upgrades, check official compatibility information for Gradle, AGP, Kotlin, JDK, Compose compiler, and annotation processors. Prefer compatible stable releases and validate the migration in small steps.
- Treat changes to `minSdk`, `targetSdk`, Room schemas, and preference formats as behavior changes. Explain their impact and preserve stored data unless a destructive change was explicitly requested.
- Update affected callers, tests, fixtures, configuration, and documentation together. Preserve accessibility, error handling, and debug/release separation.

## Validation

Use the checked-in Gradle wrapper. Determine the required JDK and Android SDK from the build configuration and CI workflows; do not assume that README version labels are current. Keep SDK paths in ignored `local.properties` or local environment configuration.

Run checks appropriate to the change, starting with the affected modules. The following commands cover common cases:

| Check | Command |
| --- | --- |
| Gradle configuration | `./gradlew help` |
| JVM module tests, for example API and model changes | `./gradlew :core:api:test :core:model:test` |
| Android unit tests and architecture checks | `./gradlew :app:testDebugUnitTest` |
| Feature unit tests, for example home changes | `./gradlew :feature:home:testDebugUnitTest` |
| Debug APK and app lint | `./gradlew :app:assembleDebug :app:lintDebug` |
| Aggregated coverage used by Android CI | `./gradlew koverXmlReportMerged` |
| Release build, including shrinking | `./gradlew :app:assembleRelease` |
| Preference instrumentation tests on an emulator or device | `./gradlew :core:preferences:connectedDebugAndroidTest` |

- Add or update behavior tests for nontrivial logic changes. Reuse JUnit, MockK, Strikt, coroutine test utilities, Robolectric, MockWebServer, and existing fixtures as appropriate. Tests should not require live TMDB credentials.
- For shared build or toolchain changes, check configuration, debug and release builds, affected JVM and Android tests, lint, and the CI coverage task. Read `.github/workflows/android.yml` and `.github/workflows/manual.yml` when changing build requirements.
- For UI or Android runtime changes, verify the affected behavior on an emulator or device when available. Compilation alone does not prove runtime behavior.
- Documentation-only changes need content, path, and diff checks rather than an Android build.
- Report the commands actually run, their outcomes, and checks that were skipped or blocked. Do not present an unrun command, a dry run, or unavailable hardware as a passing test.
