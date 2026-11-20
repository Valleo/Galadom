---
name: hexagonal-architecture
description: Preserve and evolve Galadom's hexagonal Ports and Adapters architecture. Use when adding or changing domain, application, ports, JavaFX UI, persistence, or other infrastructure integrations.
---

# Hexagonal architecture for Galadom

Follow the dependency rules and package guidance in the repository's `AGENTS.md`. Treat its rules as authoritative when this skill and existing structure differ.

## Dependency direction

- Dependencies point inward toward the domain; the domain depends only on the JDK.
- The domain must not import JavaFX, adapters, persistence libraries, or application services.
- Application use cases may depend on domain types and port contracts, but not on adapter implementations.
- Adapters implement or call the relevant ports. Adapters must not depend on one another.
- JavaFX UI code must invoke application input ports/use cases to change game state; it must not directly mutate domain objects.

## Place responsibilities at the correct boundary

- Put game rules and invariants in domain entities/value objects, not in JavaFX controllers or persistence code.
- Put player/game intentions in application use cases, with orchestration in application services.
- Define outbound ports for external capabilities such as storage, randomness, or time; inject their implementations from the composition/bootstrap boundary.
- Keep adapters responsible for translating between external mechanisms and the ports. Keep JavaFX event handlers thin and JavaFX-thread-safe.
- Keep UI-only behavior (menus, scenes, media, animation) in the existing JavaFX view/adapter packages; do not introduce domain concepts for presentation details.

## Change workflow

1. Trace the relevant call path and identify the owning layer before editing.
2. Make the smallest change that preserves inward dependencies and existing package conventions.
3. If an operation crosses a boundary, introduce or use an explicit port rather than importing an adapter inward.
4. Do not invent game rules when requirements are ambiguous; ask the user.
5. Test domain/application behavior without JavaFX. Use in-memory fakes for outbound ports where useful. Follow the testing requirements in `AGENTS.md` unless the user explicitly requests otherwise.
6. Before finishing, inspect imports and dependencies in changed files for layer violations, then run the appropriate Gradle wrapper validation.

If the current project structure does not yet contain a domain/application/port layer, do not move existing code or create placeholder layers as part of an unrelated task. Preserve the current structure and report the architectural gap when it materially affects the requested change.