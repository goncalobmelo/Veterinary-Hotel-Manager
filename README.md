# Veterinary Hotel Management System

An object-oriented management system for a veterinary hotel featuring complex domain modeling, design patterns, and dynamic satisfaction calculations for animals and employees.

## Overview

**Context:** Academic project for Object-Oriented Programming course  
**Duration:** September - October 2024  
**Team Size:** Team of 2  
**Role:** Developer - Contributed to all system components

This project implements a comprehensive management system for a veterinary hotel that houses animals across multiple habitats, managed by veterinarians and keepers. The system features dynamic satisfaction calculations influenced by habitat conditions, seasonal effects on trees, vaccination tracking with health consequences, and persistent state management through Java serialization.

## Technologies Used

- **Language:** Java 11
- **Architecture:** Object-oriented design with design patterns
- **Persistence:** Java serialization
- **UI:** Text-based menu system
- **Build System:** Make/javac
- **Libraries:** Custom po-uilib framework

## Key Features

### 1. Multi-Entity Domain Model
- **Animals:** Species management with health tracking and habitat assignment
- **Employees:** Veterinarians and keepers with responsibility management
- **Habitats:** Area-based enclosures with tree management
- **Vaccines:** Species-specific vaccinations with damage mechanics
- **Trees:** Deciduous and evergreen trees with seasonal life cycles

### 2. Dynamic Satisfaction System
Complex satisfaction calculations for animals and employees based on multiple factors:
- **Animals:** Influenced by same-species count, habitat conditions, and area per animal
- **Veterinarians:** Based on workload distribution and species responsibility
- **Keepers:** Based on habitat size, animal population, and tree maintenance effort

### 3. Seasonal Effects System
- Four seasons affecting tree behavior and maintenance requirements
- Deciduous trees lose leaves in autumn/winter
- Evergreen trees maintain foliage year-round
- Tree cleaning effort varies by season and type
- Trees age with seasonal progression

### 4. Vaccination & Health Management
- Species-appropriate vaccine administration
- Damage calculation for incorrect vaccinations
- Health history tracking per animal
- Veterinarian authorization validation

### 5. Persistent State Management
- Save/load functionality via Java serialization
- Import initial data from text files
- Maintain application state across sessions

## System Architecture

### **Core Domain Classes:**


```
HotelManager
└── Uses: Hotel (main domain model)
    ├── Animal (species, health history, habitat)
    │   └── Uses: AnimalCalculator interface
    │       └── AnimalSatisfactionCalculator (implementation)
    ├── Specie
    ├── Employee (abstract)
    │   ├── Veterinarian (species responsibilities)
    │   │   └── Uses: VeterinarianCalculator interface
    │   │       └── VeterinarianSatisfactionCalculator (implementation)
    │   └── Keeper (habitat responsibilities)
    │       └── Uses: KeeperCalculator interface
    │           └── KeeperSatisfactionCalculator (implementation)
    ├── Habitat (area, trees, animals)
    ├── Impact (abstract)
    │   ├── PositiveImpact
    │   ├── NeutralImpact
    │   └── NegativeImpact
    ├── Season
    │   └── Uses: SeasonState (abstract)
    │       ├── SpringSeasonState
    │       ├── SummerSeasonState
    │       ├── AutumnSeasonState
    │       └── WinterSeasonState
    ├── Tree
    │   ├── DeciduousTree (uses DeciduousTreeState)
    │   ├── EvergreenTree (uses EvergreenTreeState)
    │   └── Uses: TreeState (abstract)
    │       ├── DeciduousTreeState (abstract)
    │       │   ├── DeciduousTreeSpring
    │       │   ├── DeciduousTreeSummer
    │       │   ├── DeciduousTreeAutumn
    │       │   └── DeciduousTreeWinter
    │       └── EvergreenTreeState (abstract)
    │           ├── EvergreenTreeSpring
    │           ├── EvergreenTreeSummer
    │           ├── EvergreenTreeAutumn
    │           └── EvergreenTreeWinter
    ├── Vaccine
    └── Vaccination (association record)
        └── Links: Vaccine + Veterinarian + Animal
```

### **Design Patterns Applied:**

**1. Strategy Pattern (Satisfaction Calculation)**
- Separate calculator interfaces for each entity type (Animal, Veterinarian, Keeper)
- Runtime policy switching capability through calculator injection
- Extensible for new satisfaction algorithms without modifying entity classes

**2. State Pattern (Seasonal Behavior)**
- Season encapsulates current state (Spring, Summer, Autumn, Winter)
- Tree behavior delegates to current season state
- Seasonal transitions update tree states automatically

**3. Nested State Pattern (Tree Type × Season)**
- Each tree type (Deciduous/Evergreen) has 4 season-specific states
- Deciduous: SEMFOLHAS (Winter) → GERARFOLHAS (Spring) → COMFOLHAS (Summer) → LARGARFOLHAS (Autumn)
- Evergreen: LARGARFOLHAS (Winter) → GERARFOLHAS (Spring) → COMFOLHAS (Summer/Autumn)
- 8 concrete state classes handling all combinations
- Demonstrates advanced state machine design

**4. State Pattern (Habitat Impact)**
- Habitat influence on species modeled as state objects
- Three impact types: Positive (+20), Neutral (0), Negative (-20)
- Changeable at runtime per species

**5. Open-Closed Principle**
- New employee types addable by extending Employee abstract class
- New tree states/behaviors added without modifying existing tree code
- New impact types implementable without changing habitat logic

## 💡 Technical Highlights

### Challenge 1: Dynamic Satisfaction Algorithms
**Problem:** Calculate satisfaction for different entities using complex formulas dependent on multiple factors  
**Solution:** Implemented mathematical models considering population density, workload distribution, habitat adequacy, and species compatibility  
**Formula Examples:**
```
Animal Satisfaction = 20 + 3*same_species - 2*other_species + area/population + adequacy
Veterinarian Satisfaction = 20 - Σ(population(species) / veterinarians_per_species)
Keeper Satisfaction = 300 - Σ(habitat_work / keepers_per_habitat)
```
**Result:** Accurate satisfaction metrics enabling management optimization

### Challenge 2: Seasonal State Management with Design Patterns
**Problem:** Trees exhibit different behaviors across seasons; implementing this with conditionals would create unmaintainable code  
**Solution:** Applied State pattern where each season encapsulates tree behavior; trees delegate seasonal operations to current season state  
**Result:** Clean, extensible code allowing new seasonal behaviors without modifying tree classes
**Architecture:**
TreeState (abstract)
├── DeciduousTreeState (abstract)
│   ├── DeciduousTreeSpring (GERARFOLHAS)
│   ├── DeciduousTreeSummer (COMFOLHAS)
│   ├── DeciduousTreeAutumn (LARGARFOLHAS)
│   └── DeciduousTreeWinter (SEMFOLHAS)
└── EvergreenTreeState (abstract)
    ├── EvergreenTreeSpring (GERARFOLHAS)
    ├── EvergreenTreeSummer (COMFOLHAS)
    ├── EvergreenTreeAutumn (COMFOLHAS)
    └── EvergreenTreeWinter (LARGARFOLHAS)

### Challenge 3: Vaccination Damage Mechanics
**Problem:** Calculate health damage from species-inappropriate vaccinations based on string similarity  
**Solution:** Implemented algorithm computing damage as maximum character difference between animal's species and vaccine's target species  
**Formula:**
```
damage = MAX(max(length(species_a), length(species_e)) - common_chars(species_a, species_e))
for all species_e in vaccine's target species
```
**Result:** Realistic health consequences creating strategic vaccination management

### Challenge 4: Complex Tree Maintenance Calculation
**Problem:** Tree cleaning effort depends on tree type, season, age, and base difficulty  
**Solution:** Multi-factor formula combining seasonal modifiers, logarithmic age scaling, and base difficulty  
**Formula:**
```
cleaning_effort = base_difficulty × seasonal_factor × log(age + 1)
```
**Result:** Realistic workload distribution affecting keeper satisfaction

## Domain Logic

### **Satisfaction Formulas:**

**Animals:**
- Prefer same-species company
- Penalized by other species presence
- Benefit from adequate habitat space
- Influenced by habitat-species compatibility (+20/-20/0)

**Veterinarians:**
- Satisfaction decreases with animal population per species
- Workload shared among veterinarians per species
- Initial satisfaction: 20

**Keepers:**
- Affected by habitat area, animal population, tree maintenance
- Workload shared among keepers per habitat
- Initial satisfaction: 300

### **Health Damage Classification:**
- **NORMAL:** Correct vaccine (damage = 0, same species)
- **CONFUSÃO:** Correct vaccine (damage = 0, different species)
- **ACIDENTE:** Minor damage (1-4 character difference)
- **ERRO:** Major damage (5+ character difference)

## Project Structure

```
veterinary-hotel-manager/
├── hva-core/                    # Core domain logic
│   ├── src/hva/
│   │    ├── animal/             # Animal entities and satisfaction calculation
│   │    ├── employee/           # Employee types (Veterinarian, Keeper) and strategies
│   │    ├── exceptions/         # Domain-specific exceptions
│   │    ├── habitat/            # Habitat management and impacts
│   │    ├── season/             # Season state management
│   │    ├── tree/               # Tree types and seasonal states
│   │    ├── Specie.java         # Specie registry
│   │    ├── Vaccine.java        # Vaccine records
│   │    ├── Vaccination.java    # Vaccination records
│   │    ├── Hotel.java          # Main domain model
│   │    ├── HotelManager.java   # Facade/manager class
│   │    └── HotelManager.java   # Facade/manager class
│   └── Makefile                 # Core build configuration
├── hva-app/                     # User interface layer
│   ├── src/hva/app/
│   │   ├── animal/              # Animal management commands
│   │   ├── employee/            # Employee management commands
│   │   ├── habitat/             # Habitat management commands
│   │   ├── vaccine/             # Vaccine management commands
│   │   ├── main/                # Main menu and file operations
│   │   ├── search/              # Query/search commands
│   │   ├── exceptions/          # Application exceptions
│   │   └── App.java             # Application entry point
│   └── Makefile                 # App build configuration
├── Makefile                     # Build configuration
└── README.md                    # Project documentation
```

## Setup & Usage

### **Prerequisites**
- Java 11 or higher
- Make (optional, for build automation)

### **Building the Project**

```bash
# Compile all sources
make

# Or manually with javac
javac -cp .:po-uilib.jar -d bin hva-core/**/*.java hva-app/**/*.java

# Clean build artifacts
make clean
```

### **Running the Application**

```bash
# Run with initial data import
java -Dimport=test.import -cp .:po-uilib.jar:bin hva.app.App

# Run interactively
java -cp .:po-uilib.jar:bin hva.app.App

# Run with automated input/output (testing)
java -Dimport=test.import -Din=test.in -Dout=test.out -cp .:po-uilib.jar:bin hva.app.App
```

### **Using the System**

The application provides text-based menus for:
1. **File Management:** Save/load application state
2. **Animal Management:** Register, transfer, view satisfaction
3. **Employee Management:** Register, assign responsibilities, view satisfaction
4. **Habitat Management:** Create, modify area, plant trees, set species influence
5. **Vaccine Management:** Register vaccines, vaccinate animals, view history
6. **Queries:** Search animals, vaccinations, medical acts

### **Import File Format**

```
ESPÉCIE|id|name
ÁRVORE|id|name|age|difficulty|type
HABITAT|id|name|area|tree_ids
ANIMAL|id|name|species_id|habitat_id
TRATADOR|id|name|habitat_ids
VETERINÁRIO|id|name|species_ids
VACINA|id|name|species_ids
```

## Skills Demonstrated

- **Object-Oriented Design:** Inheritance, polymorphism, encapsulation, abstraction
- **Design Patterns:** Strategy, State, adherence to SOLID principles
- **Java Programming:** Collections, serialization, exception handling, file I/O
- **Domain Modeling:** Complex business logic, entity relationships, constraint management
- **Software Architecture:** Separation of concerns (core vs. UI), extensibility
- **Algorithm Design:** Mathematical formulas, string algorithms, optimization
- **Testing:** Automated input/output validation
- **Team Collaboration:** Multi-developer codebase, interface adherence

## Design Decisions

### **Strategy Pattern for Satisfaction**
Allows runtime switching of satisfaction calculation policies per employee, enabling future customization without code modification.

### **State Pattern for Seasons**
Encapsulates seasonal behavior in state objects, making tree logic readable and allowing new seasonal effects (e.g., flower blooming, fruit production) without altering tree classes.

### **Open-Closed Principle**
New employee types, search criteria, or vaccine types can be added by extending base classes without modifying existing, tested code.

### **Separation of Core and UI**
Domain logic (hva-core) remains independent of user interface (hva-app), enabling future GUI or web interface without touching business logic.

## Future Improvements

- [ ] Graphical user interface (JavaFX/Swing)
- [ ] Database persistence instead of serialization
- [ ] RESTful API for external integrations
- [ ] Visitor pattern for complex queries
- [ ] Additional employee types (nutritionists, trainers)
- [ ] Breeding system for animals
- [ ] Financial management (budget, costs)
- [ ] Reporting and analytics dashboard

## Contact

**Gonçalo Melo**  
goncalo.b.melo@gmail.com | [github.com/goncalobmelo](https://github.com/goncalobmelo)

---

*This project was developed as part of CSE/Object-Oriented Programming at Instituto Superior Técnico, 2024.*