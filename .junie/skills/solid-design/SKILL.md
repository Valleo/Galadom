---
name: solid-design
description: Apply SOLID principles when analyzing, designing, refactoring, or implementing Java code in Galadom. Use when a change introduces or changes class responsibilities, dependencies, extension points, or object construction.
---

# SOLID design for Galadom

Use this skill when a task changes Java design or responsibilities. Follow the repository's `AGENTS.md` and existing conventions; keep changes focused and do not refactor unrelated code.

## Before changing code

- Read the affected classes and their callers before proposing or making a design change.
- Identify the behavior that must remain unchanged and the class/module responsible for it.
- Do not create abstractions merely to demonstrate a principle. Add an interface, class, or indirection only when it clarifies a real responsibility or variation point.

## Apply SOLID pragmatically

- **Single Responsibility:** Give each class one cohesive reason to change. Separate unrelated concerns such as JavaFX window lifecycle, scene construction, presentation, and media playback when they are independently managed. Avoid splitting cohesive behavior into excessive tiny classes.
- **Open/Closed:** Prefer focused extension points when new variants are expected. Do not add speculative frameworks or general-purpose extension systems for a single fixed behavior.
- **Liskov Substitution:** Subtypes must honor the contracts and invariants of their parent types. Prefer composition to inheritance when substitutability is unclear.
- **Interface Segregation:** Keep interfaces small and specific to the clients that use them. Do not make callers depend on operations they do not need.
- **Dependency Inversion:** Keep high-level policy independent of low-level details. Place contracts at the boundary that owns the policy and inject concrete implementations; do not make domain or application logic depend on JavaFX or infrastructure.

## Construction and encapsulation

- Keep mutable state private and expose the smallest useful API.
- Prefer constructor injection for required collaborators; avoid service locators and hidden global state.
- Keep JavaFX controllers and views focused on translating user input and displaying output, not on implementing game rules.
- Preserve existing package boundaries and public APIs unless the task requires changing them.

## Validation

- For behavior changes, add or update tests as appropriate and run the relevant Gradle wrapper checks, following `AGENTS.md`.
- For a refactor, verify behavior is preserved and avoid weakening or removing tests.
- In the final summary, briefly explain the responsibility/dependency choices and mention any principle that could not reasonably be applied without overengineering.