# AGENTS.md — Galadom

Ce fichier donne aux agents IA le contexte nécessaire pour travailler sur le projet **Galadom**.
Lisez-le avant toute intervention sur le code.

---

## 1. Présentation du projet

**Galadom** est un jeu vidéo de stratégie **4X** (eXplore, eXpand, eXploit, eXterminate).

Le joueur dirige un **empire galactique**. Il doit affronter et vaincre les empires rivaux, et pour cela gérer :

- **l'économie de ses planètes** : production, ressources, population, développement ;
- **ses chaînes d'approvisionnement** : flux de ressources entre les planètes, routes logistiques, dépendances ;
- **ses flottes** : construction, déplacement, ravitaillement, combat ;
- **la diplomatie et la guerre** avec les autres empires.

Le but est de s'imposer face aux autres empires.

---

## 2. Stack technique

| Élément            | Choix                                   |
|--------------------|-----------------------------------------|
| Langage            | Java                                    |
| JDK                | **27**                                  |
| Build              | **Gradle** (utiliser le wrapper)        |
| Interface (UI)     | **JavaFX**                              |
| Architecture       | **Hexagonale** (Ports & Adapters)       |

### Règles techniques

- Ciblez **Java 27** et utilisez les fonctionnalités modernes du langage quand elles rendent le code plus clair : `record`, `sealed` interfaces, pattern matching (`switch`, `instanceof`), `var` avec modération.
- Utilisez **toujours le wrapper Gradle** (`.\gradlew.bat` sous Windows, `./gradlew` sous Linux/macOS). N'utilisez pas un Gradle installé sur la machine.
- N'ajoutez **pas de nouvelle dépendance** sans justification claire. Préférez la bibliothèque standard du JDK.
- La version du JDK est déclarée via la **toolchain Gradle**. Ne la modifiez pas.

### Commandes utiles

Dans ce terminal Windows, Java 8 peut être sélectionné par défaut malgré la toolchain Java 27 du projet. Avant d'utiliser Gradle, sélectionnez le JDK 27 installé (ajustez le chemin si son emplacement ou sa version change) :

```powershell
$env:JAVA_HOME = 'C:\Users\frede\.jdks\temurin-27.0.1.0.2'
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
```

Ces commandes ne modifient l'environnement que pour la session PowerShell courante. Utilisez ensuite le wrapper Gradle :

```shell
.\gradlew.bat build    # compile et lance les tests
.\gradlew.bat test     # lance uniquement les tests
.\gradlew.bat run      # lance le jeu (si le plugin application est configuré)
```

---

## 3. Architecture hexagonale

Le code est **orienté métier**. Le cœur du jeu (les règles du 4X) doit pouvoir être compris, testé et exécuté **sans JavaFX, sans fichiers et sans aucune infrastructure**.

### Les couches

```
            ┌──────────────────────────────────────────┐
            │          Adapters entrants (UI)          │
            │        JavaFX : vues, contrôleurs        │
            └────────────────────┬─────────────────────┘
                                 │ appelle
                    ┌────────────▼────────────┐
                    │  Ports entrants (API)   │  ← cas d'utilisation
                    ├─────────────────────────┤
                    │        APPLICATION      │  ← orchestration des cas d'utilisation
                    ├─────────────────────────┤
                    │          DOMAINE        │  ← règles métier pures
                    ├─────────────────────────┤
                    │  Ports sortants (SPI)   │  ← interfaces vers l'extérieur
                    └────────────▲────────────┘
                                 │ implémente
            ┌────────────────────┴─────────────────────┐
            │            Adapters sortants             │
            │  sauvegarde, chargement de données, IA,  │
            │  aléatoire, horloge…                     │
            └──────────────────────────────────────────┘
```

### Règle de dépendance (non négociable)

> **Les dépendances pointent toujours vers le domaine.**

- Le **domaine** ne dépend de **rien** d'autre que du JDK. Il n'importe **aucune** classe `javafx.*`, aucun adapter, aucun framework.
- L'**application** dépend uniquement du domaine et des ports.
- Les **adapters** dépendent des ports. Ils ne se connaissent pas entre eux.
- L'UI JavaFX n'accède **jamais** directement au domaine pour le modifier. Elle passe par les **ports entrants** (cas d'utilisation).

### Organisation des packages (structure indicative)

Si la structure existante diffère, **suivez la structure existante**.

```
galadom
├── domain                 # Modèle métier pur
│   ├── empire             # Empire, relations diplomatiques
│   ├── planet             # Planète, population, production
│   ├── economy            # Ressources, stocks, production
│   ├── supply             # Chaînes et routes d'approvisionnement
│   ├── fleet              # Flottes, vaisseaux, mouvements
│   ├── combat             # Résolution des combats
│   └── galaxy             # Carte, systèmes stellaires, coordonnées
├── application
│   ├── port
│   │   ├── in             # Cas d'utilisation (ex. MoveFleetUseCase)
│   │   └── out            # Interfaces externes (ex. GameRepository)
│   └── service            # Implémentation des cas d'utilisation
└── adapter
    ├── in
    │   └── javafx         # Vues, contrôleurs, view-models JavaFX
    └── out
        ├── persistence    # Sauvegarde et chargement des parties
        └── ...            # Autres implémentations des ports sortants
```

### Bonnes pratiques métier

- Utilisez le **vocabulaire du jeu** dans le code : `Empire`, `Planet`, `Fleet`, `SupplyChain`, `Turn`… Le code doit se lire comme les règles du jeu.
- Préférez des **objets valeur immuables** (`record`) pour les concepts simples : quantités de ressources, coordonnées, identifiants.
- Placez les **règles et invariants dans les entités du domaine**. Évitez un modèle « anémique » où toute la logique se trouve dans des services.
- Pour tout ce qui n'est pas déterministe (**aléatoire**, **temps**), passez par un **port sortant**. Le domaine reste ainsi testable et reproductible (par exemple avec une graine fixe pour l'aléatoire).
- Un cas d'utilisation = une intention du joueur ou du jeu (ex. « déplacer une flotte », « terminer le tour », « construire un bâtiment »).

---

## 4. Interface graphique (JavaFX)

- L'UI se trouve **uniquement** dans l'adapter entrant JavaFX.
- Les contrôleurs JavaFX restent **fins** : ils traduisent les actions du joueur en appels de cas d'utilisation, puis affichent le résultat.
- **Aucune règle métier** dans les contrôleurs, les vues ou les fichiers FXML.
- Respectez le **thread JavaFX** : toute modification de l'UI se fait sur le *JavaFX Application Thread* (`Platform.runLater` si nécessaire). Les calculs longs (IA des empires adverses, résolution d'un tour) ne doivent pas bloquer l'UI.

---

## 5. Tests

- Le **domaine** et l'**application** doivent être couverts par des **tests unitaires**, sans JavaFX.
- Pour tester l'application, remplacez les ports sortants par des **implémentations en mémoire** (fakes) plutôt que par des mocks complexes.
- Nommez les tests selon le comportement métier attendu, par exemple : `une_flotte_sans_ravitaillement_perd_du_moral`.
- Tout correctif de bug doit être accompagné d'un **test qui reproduit le bug**.
- Lancez `.\gradlew.bat test` avant de considérer une tâche comme terminée.

---

## 6. Consignes pour les agents IA

1. **Lisez le code existant** avant de modifier quoi que ce soit, et suivez ses conventions (nommage, style, structure des packages).
2. **Respectez la règle de dépendance** de l'architecture hexagonale. Si une modification l'oblige à la violer, signalez-le au lieu de la contourner.
3. Faites des **modifications minimales et ciblées**. Pas de refactorisation large sans demande explicite.
4. Ne modifiez pas la **version du JDK**, la **configuration Gradle** ou les **dépendances** sans demande explicite.
5. Vérifiez que le projet **compile** et que les **tests passent** après vos modifications.
6. N'effectuez **pas de commit** sans demande explicite.
7. En cas de doute sur une règle du jeu, **posez la question** plutôt que d'inventer une mécanique.
