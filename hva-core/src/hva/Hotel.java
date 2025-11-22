package hva;

import hva.animal.Animal;
import hva.animal.AnimalSatisfactionCalculator;
import hva.employee.*;
import hva.exceptions.*;
import hva.habitat.*;
import hva.season.Season;
import hva.tree.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//FIXME import other Java classes


public class Hotel implements Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /**
     * Used to control if a hotel has changed
     * since the last save
     */
    private boolean _changed = false;

    private final Season _season = new Season();

    /**
     * Stores the hotel species
     */
    private final Map<String,Specie> _species =  new TreeMap();

    /**
     * Stores the hotel habitats
     */
    private final Map<String,Habitat> _habitats = new TreeMap();

    /**
     * Stores the hotel trees
     */
    private final Map<String,Tree> _trees = new TreeMap();

    /**
     * Stores the hotel animals
     */
    private final Map<String,Animal> _animals = new TreeMap();

    /**
     * Stores the hotel employees
     */
    private final Map<String,Employee> _employees = new TreeMap();

    /**
     * Stores the hotel vaccines
     */
    private final Map<String,Vaccine> _vaccines = new TreeMap();

    private final List<Vaccination> _vaccinationLog = new ArrayList();

    /**
     * Read text input file and create domain entities.
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    void importFile(String filename) throws ImportFileException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split("\\|");
                    registerEntry(fields);
                }
            } 
        catch (IOException | UnrecognizedEntryException e) {
            throw new ImportFileException(filename, e);
        }
    }

    /** 
     * Receives a line of the input, checks the first element and based on that
     * decides which object to register
     * 
     * @param fields line of input
     * @throws UnrecognizedEntryException if the first element isn´t a known entry type
    */
    public void registerEntry(String[] fields) throws UnrecognizedEntryException{
        try { 
            switch (fields[0]) {
            case "ESPÉCIE" -> registerSpecie(fields);
            case "ÁRVORE" -> registerTree(fields);
            case "HABITAT" -> registerHabitat(fields);
            case "ANIMAL" -> registerAnimal(fields);
            case "TRATADOR", "VETERINÁRIO" -> registerEmployee(fields);
            case "VACINA" -> registerVaccine(fields);
            default -> throw new UnrecognizedEntryException(fields[0]);
            }
        }
        catch(DupAnimalKeyException | DupTreeKeyException|
        DupHabitatKeyException | DupEmployeeKeyException | DupVaccineKeyException |
        WrongHabitatKeyException | WrongSpecieKeyException | WrongTreeKeyException e){
            e.printStackTrace();
        }
    }

    /**
     * Creates and saves the register of a new Specie
     * 
     * @param fields input to create a specie
     */
    public void registerSpecie(String[] fields){
        Specie specie = new Specie(fields[1], fields[2]);
        addSpecie(specie);
        changed();
    }


    /**
     * Creates and saves the register of a new tree that is one of two specific types
     * 
     * @param fields input to create a tree
     * @throws UnrecognizedEntryException if it isn´t one of the known tree types
     * @throws DupTreeKeyException if the tree key is already in use
     */
    public void registerTree(String[] fields) throws  UnrecognizedEntryException, DupTreeKeyException{
        try {
            getTree(fields[1]);
            throw new DupTreeKeyException(fields[1]);
        } 
        catch (WrongTreeKeyException e) {  
            int age = Integer.parseInt(fields[3]);
            int cleaningDifficulty = Integer.parseInt(fields[4]);
            Tree tree = switch(fields[5]){
            case "PERENE" -> {
                Tree evergreen = new EvergreenTree(fields[1], fields[2], age, cleaningDifficulty);
                evergreen.setState(_season.getState().evergreenState(evergreen));
                yield evergreen;
            }
            case "CADUCA" -> {
                Tree deciduous = new DeciduousTree(fields[1], fields[2], age, cleaningDifficulty);
                deciduous.setState(_season.getState().deciduousState(deciduous));
                yield deciduous;
            }
            default -> throw new UnrecognizedEntryException(fields[5]);
            };
            addTree(tree);
            changed();
        }
    }


    /**
     * Creates and saves the register of a new habitat, that can have a register of trees or not
     * 
     * @param fields input to create a habitat
     * @throws DupHabitatKeyException if the habitat key is already in use
     * @throws WrongTreeKeyException if one of the trees in the habitat doesn´t exist
     */
    public void registerHabitat(String[] fields) throws DupHabitatKeyException, WrongTreeKeyException{
        try {
            getHabitat(fields[1]);
            throw new DupHabitatKeyException(fields[1]);
        } 
        catch (WrongHabitatKeyException e) {
            int area = Integer.parseInt(fields[3]);
            Habitat habitat;
            if(fields.length == 5){
                Tree[] _habitatTrees = getHabitatTrees(fields[4]);
                habitat = new Habitat(fields[1], fields[2], area, _habitatTrees);
            }
            else{
                habitat = new Habitat(fields[1], fields[2], area);
            }
            addHabitat(habitat);
            changed();
        }
    }

    /**
     * Creates and saves the register of a new Animal
     * 
     * @param fields input to create an animal
     * @throws DupAnimalKeyException if the animal key is already in use
     * @throws WrongHabitatKeyException if the habitat doesn´t exist
     * @throws WrongSpecieKeyException if the specie doesn´t exist
     */
    public void registerAnimal(String[] fields) throws DupAnimalKeyException, WrongHabitatKeyException, WrongSpecieKeyException{
        try {
            getAnimal(fields[1]);
            throw new DupAnimalKeyException(fields[1]);
        } 
        catch (WrongAnimalKeyException e) {
            Habitat habitat = getHabitat(fields[4]);
            Specie specie = getSpecie(fields[3]);
            Animal animal = new Animal(fields[1], fields[2], specie, habitat, new AnimalSatisfactionCalculator());
            addAnimal(animal);
            specie.insertAnimal(animal);
            habitat.insertSpecieCount(animal);
            changed();
        }
    }

    /**
     * Creates and saves the register of a new employee that is one of two types
     * 
     * @param fields input to create an employee
     * @throws UnrecognizedEntryException if it isn´t one of the known employee types
     * @throws DupEmployeeKeyException if the employee key is already in use
     * @throws WrongHabitatKeyException if one of the habitats doesn´t exist
     * @throws WrongSpecieKeyException if one of the species doesn´t exist
     */
    public void registerEmployee(String[] fields) throws UnrecognizedEntryException, DupEmployeeKeyException, WrongHabitatKeyException,
                                                WrongSpecieKeyException{
        try {
            getEmployee(fields[1]);
            throw new DupEmployeeKeyException(fields[1]);
        } 
        catch (WrongEmployeeKeyException e) {
            Employee employee = switch(fields[0]){
            case "TRATADOR" -> registerKeeper(fields);
            case "VETERINÁRIO" -> registerVeterinarian(fields);
            default -> throw new UnrecognizedEntryException(fields[0]);
            };
            addEmployee(employee);
            changed();
        }
    }

    /**
     * Creates the register of a new Keeper
     * 
     * @param fields input to create a keeper
     * @return returns the keeper created
     * @throws WrongHabitatKeyException if one of the habitats doesn´t exist
     */
    public Keeper registerKeeper(String[] fields) throws WrongHabitatKeyException{
        Keeper keeper;
        if(fields.length == 4){
            Habitat[] _responsabilities = getHabitats(fields[3]);
            keeper = new Keeper(fields[1],fields[2], new KeeperSatisfactionCalculator(),_responsabilities);
            for(Habitat habitat : _responsabilities){
                habitat.addKeeper();
            }
        }
        else{
            keeper = new Keeper(fields[1],fields[2], new KeeperSatisfactionCalculator());
        }
        return keeper;
    }

    /**
     * Creates the register of a new Veterinarian
     * 
     * @param fields input to create a veterinarian
     * @return returns the veterinarian created
     * @throws WrongSpecieKeyException if one of the species doesn´t exist
     */
    public Veterinarian registerVeterinarian(String[] fields) throws WrongSpecieKeyException{
        Veterinarian veterinarian;
        if(fields.length == 4){
            Specie[] _responsabilities = getSpecies(fields[3]);
            veterinarian = new Veterinarian(fields[1],fields[2], new VeterinarianSatisfactionCalculator(), _responsabilities);
            for(Specie specie : _responsabilities){
                specie.addVeterinarian();
            }
        }
        else{
            veterinarian = new Veterinarian(fields[1],fields[2], new VeterinarianSatisfactionCalculator());
        }
        return veterinarian;
    }

    /**
     * Creates and saves the register of a new vaccine
     * 
     * @param fields input to create a vaccine
     * @throws DupVaccineKeyException if the vaccine key is already in use
     * @throws WrongSpecieKeyException if one of the species doesn´t exist
     */
    public void registerVaccine(String[] fields) throws DupVaccineKeyException, WrongSpecieKeyException{
        try {
            getVaccine(fields[1]);
            throw new DupVaccineKeyException(fields[1]);
        }
        catch (WrongVaccineKeyException e) {
            Vaccine vaccine;
            if(fields.length == 4){
                Specie[] _vaccineSpecies = getSpecies(fields[3]);
                vaccine = new Vaccine(fields[1],fields[2],_vaccineSpecies);
            }
            else{
                vaccine = new Vaccine(fields[1],fields[2]);
            }
            addVaccine(vaccine);
            changed();
        }
    }

    public boolean newSpecieName(String name) throws DupSpecieNameException{
        for(Specie specie : _species.values()){
            if(name.equals(specie.getName())){
                throw new DupSpecieNameException();
            }
        }
        return true;
    }

    /**
     * Adds a new specie to the register
     * 
     * @param specie receives the specie to be saved
     */
    public void addSpecie(Specie specie){
        String key = specie.getKey();
        _species.put(key.toLowerCase(), specie);
    }

    /**
     * Adds a new tree to the register
     * 
     * @param specie receives the tree to be saved
     */
    public void addTree(Tree tree){
        String key = tree.getKey();
        _trees.put(key.toLowerCase(), tree);
    }

    /**
     * Adds a new habitat to the register
     * 
     * @param specie receives the habitat to be saved
     */
    public void addHabitat(Habitat habitat){
        String key = habitat.getKey();
        _habitats.put(key.toLowerCase(), habitat);
    }

    /**
     * Adds a new animal to the register
     * 
     * @param specie receives the animal to be saved
     */
    public void addAnimal(Animal animal){
        String key = animal.getKey();
        _animals.put(key.toLowerCase(), animal);
    }

    /**
     * Adds a new employee to the register
     * 
     * @param specie receives the employee to be saved
     */
    public void addEmployee(Employee employee){
        String key = employee.getKey();
        _employees.put(key.toLowerCase(), employee);
    }

    /**
     * Adds a new vaccine to the register
     * 
     * @param specie receives the vaccine to be saved
     */
    public void addVaccine(Vaccine vaccine){
        String key = vaccine.getKey();
        _vaccines.put(key.toLowerCase(), vaccine);
    }

    /**
     * Receives the keys of the trees in a habitat and returns the 
     * list of the corresponding Tree objects
     * 
     * @param keys keys of the trees in the habitat
     * @return array of the tree objects
     * @throws WrongTreeKeyException if one of the trees in the habitat doesn´t exist
     */
    public Tree[] getHabitatTrees(String keys) throws WrongTreeKeyException{
        String[] keysList = keys.split(",");
        Tree[] _habitatTrees = new Tree[keysList.length];

        for(int i = 0; i < keysList.length; i++){
            Tree tree = getTree(keysList[i].toLowerCase());
            _habitatTrees[i] = tree;
        }
        return _habitatTrees;
    }

    /**
     * Receives the keys of the habitats that a keeper is responsible for
     * and returns the list of the corresponding Habitat objects
     * 
     * @param keys keys of the habitats
     * @return array of the Habitat objects
     * @throws WrongHabitatKeyException if one of the habitats doesn´t exist
     */
    public Habitat[] getHabitats(String keys) throws WrongHabitatKeyException{
        String[] keysList = keys.split(",");
        Habitat[] _responsabilities = new Habitat[keysList.length];

        for(int i = 0; i < keysList.length; i++){
            Habitat habitat = getHabitat(keysList[i].toLowerCase());
            _responsabilities[i] = habitat;
        }
        return _responsabilities;
    }

    /**
     * Receives the keys of the species that a veterinarian is responsible for
     * or the species that can receive a certain vaccine and returns the list of the 
     * corresponding Specie objects
     * 
     * @param keys keys of the species
     * @return array of the Specie objects
     * @throws WrongSpecieKeyException if one of the species doesn´t exist
     */
    public Specie[] getSpecies(String keys) throws WrongSpecieKeyException{
        String[] keysList = keys.split(",");
        Specie[] _specieList = new Specie[keysList.length];

        for(int i = 0; i < keysList.length; i++){
            Specie specie = getSpecie(keysList[i].toLowerCase());
            _specieList[i] = specie;
        }
        return _specieList;
    }

    public Specie getSpecie(String key) throws WrongSpecieKeyException{
        Specie specie = _species.get(key.toLowerCase());
        if(specie == null){
            throw new WrongSpecieKeyException(key);
        }
        return specie;
    }

    public Habitat getHabitat(String key) throws WrongHabitatKeyException{
        Habitat habitat = _habitats.get(key.toLowerCase());
        if(habitat == null){
            throw new WrongHabitatKeyException(key);
        }
        return habitat;
    }

    public Tree getTree(String key) throws WrongTreeKeyException{
        Tree tree = _trees.get(key.toLowerCase());
        if(tree == null){
            throw new WrongTreeKeyException();
        }
        return tree;
    }

    public Animal getAnimal(String key) throws WrongAnimalKeyException{
        Animal animal = _animals.get(key.toLowerCase());
        if(animal == null){
            throw new WrongAnimalKeyException(key);
        }
        return animal;
    }

    public Employee getEmployee(String key) throws WrongEmployeeKeyException{
        Employee employee = _employees.get(key.toLowerCase());
        if(employee == null){
            throw new WrongEmployeeKeyException(key);
        }
        return employee;
    }

    public Employee getVeterinarian(String key) throws WrongVeterinarianKeyException, WrongEmployeeKeyException{
        Employee employee = getEmployee(key);
        if(employee instanceof Veterinarian){
            return employee;
        }
        else{
            throw new WrongVeterinarianKeyException(key);
        }
    }

    public Vaccine getVaccine(String key) throws WrongVaccineKeyException{
        Vaccine vaccine = _vaccines.get(key.toLowerCase());
        if(vaccine == null){
            throw new WrongVaccineKeyException(key);
        }
        return vaccine;
    }

    public Season getSeason(){
        return _season;
    }

    public void advanceSeason(){
        _season.getState().changeState();
        for(Tree tree: _trees.values()){
            tree.getState().changeState();
        }
        changed();
    }

    /**
     * Gets all the Habitats that are stored in the hotel
     * 
     * @return A sorted {@link Collection} of Habitats
     */
    public Collection<Habitat> getAllHabitats(){
        return Collections.unmodifiableCollection(_habitats.values());
    }

    /**
     * Gets all the Animals that are stored in the hotel
     * 
     * @return A sorted {@link Collection} of Animals
     */
    public Collection<Animal> getAllAnimals(){
        return Collections.unmodifiableCollection(_animals.values());
    }

    /**
     * Gets all the employee that are stored in the hotel
     * 
     * @return A sorted {@link Collection} of Employees
     */
    public Collection<Employee> getAllEmployees(){
        return Collections.unmodifiableCollection(_employees.values());
    }

    /**
     * Gets all the Vaccine that are stored in the hotel
     * 
     * @return A sorted {@link Collection} of Vaccines
     */
    public Collection<Vaccine> getAllVaccines(){
        return Collections.unmodifiableCollection(_vaccines.values());
    }

    public void changeAnimalHabitat(String animalKey, String habitatKey) throws WrongAnimalKeyException, WrongHabitatKeyException{
        Animal animal = getAnimal(animalKey);
        Habitat habitat = getHabitat(habitatKey);
        animal.getHabitat().removeSpecieCount(animal);
        animal.setHabitat(habitat);
        habitat.insertSpecieCount(animal);
    }

    public Long getAnimalSatisfaction(String key) throws WrongAnimalKeyException{
        Animal animal = getAnimal(key);
        return animal.calculateSatisfaction();
    }

    public Long getEmployeeSatisfaction(String key) throws WrongEmployeeKeyException{
        Employee employee = getEmployee(key);
        if(employee instanceof Keeper){
            return ((Keeper)employee).getSatisfaction();
        }
        else{
            return ((Veterinarian)employee).getSatisfaction();
        }

    } 

    public Long getAllSatisfactions(){
        long allAnimalSatisfaction = 0;
        long allEmployeeSatisfaction = 0;
        try{
            for(String animalKey : _animals.keySet()){
                allAnimalSatisfaction += getAnimalSatisfaction(animalKey);
            }
            for(String employeeKey : _employees.keySet()){
                allEmployeeSatisfaction += getEmployeeSatisfaction(employeeKey);
            }
        }
        catch(WrongAnimalKeyException | WrongEmployeeKeyException e){
            e.printStackTrace();
        }
        return allAnimalSatisfaction + allEmployeeSatisfaction;
    }

    public List<Vaccination> getVaccinationLog(){
        return Collections.unmodifiableList(_vaccinationLog);
    }

    public List<Vaccination> getAnimalVaccinationLog(String animalKey) throws WrongAnimalKeyException{
        Animal animal = getAnimal(animalKey);
        return animal.getVaccinationLog();
    }

    public List<Vaccination> getVeterinarianVaccinationLog(String employeeKey) throws WrongEmployeeKeyException, WrongVeterinarianKeyException{
        Employee employee = getVeterinarian(employeeKey);
        return ((Veterinarian) employee).getVaccinationLog();
    }

    public List<Vaccination> getWrongVaccinations(){
        List<Vaccination> wrongVaccinations = new ArrayList<>();
        for(Vaccination vaccination : _vaccinationLog){
            if(!vaccination.getAnimalHealth().equals("NORMAL")){
                wrongVaccinations.add(vaccination);
            }
        }
        return Collections.unmodifiableList(wrongVaccinations);
    }

    public void updateHabitatArea(String key, int area) throws WrongHabitatKeyException{
        Habitat habitat = getHabitat(key);
        habitat.setArea(area);
        changed();
    }

    public void changeHabitatInfluence(String habitatKey, String specieKey, String influence) throws WrongHabitatKeyException, WrongSpecieKeyException{
        Habitat habitat = getHabitat(habitatKey);
        getSpecie(specieKey);
        switch(influence){
            case "POS" -> habitat.changeImpactLog(specieKey, new PositiveImpact());
            case "NEU" -> habitat.changeImpactLog(specieKey, new NeutralImpact());
            case "NEG" -> habitat.changeImpactLog(specieKey, new NegativeImpact());
        }
        changed();
    }

    public Collection<Tree> addTreeToHabitat(String habitatKey, String[] treeFields) throws WrongHabitatKeyException, WrongTreeKeyException, UnrecognizedEntryException, DupTreeKeyException{
        Habitat habitat = getHabitat(habitatKey);
        registerTree(treeFields);
        Tree tree = getTree(treeFields[1]);
        habitat.insertTree(tree);
        changed();
        List<Tree> aux = new ArrayList<>();
        aux.add(tree);
        return Collections.unmodifiableList(aux);
    }

    public Collection<Tree> getAllHabitatTrees(String key) throws WrongHabitatKeyException{
        Habitat habitat = getHabitat(key);
        return habitat.getAllTrees();
    }

    public Collection<Animal> getAllHabitatAnimals(String habitatKey) throws WrongHabitatKeyException{
        Habitat habitat = getHabitat(habitatKey);
        return habitat.getAllAnimals();
    }

    public void addResponsability(String employeeKey, String responsabilityKey) throws WrongEmployeeKeyException, WrongHabitatKeyException, WrongSpecieKeyException{
        Employee employee = getEmployee(employeeKey);
        if (employee instanceof Keeper){
            Habitat habitat = getHabitat(responsabilityKey);
            if(((Keeper) employee).insertResponsability(habitat)){
                habitat.addKeeper();
                changed();
            }
        }
        else if (employee instanceof Veterinarian){
            Specie specie = getSpecie(responsabilityKey);
            if(((Veterinarian) employee).insertResponsability(specie)){
                specie.addVeterinarian();
                changed();
            }
        }
    }

    public void removeResponsability(String employeeKey, String responsabilityKey) throws WrongEmployeeKeyException, WrongHabitatKeyException, WrongSpecieKeyException, ResponsabilityUnassignedException{
        Employee employee = getEmployee(employeeKey);
        if (employee instanceof Keeper){
            Habitat habitat = getHabitat(responsabilityKey);
            if(((Keeper) employee).removeResponsability(habitat)){
                habitat.removeKeeper();
                changed();
            }
            else{
                throw new ResponsabilityUnassignedException(responsabilityKey);
            }
        }
        else if (employee instanceof Veterinarian){
            Specie specie = getSpecie(responsabilityKey);
            if(((Veterinarian) employee).removeResponsability(specie)){
                specie.removeVeterinarian();
                changed();
            }
            else{
                throw new ResponsabilityUnassignedException(responsabilityKey);
            }
        }
    }

    public void applyVaccine(String vaccineKey, String veterinarianKey, String animalKey) throws WrongVaccineKeyException, WrongEmployeeKeyException, WrongAnimalKeyException, WrongVeterinarianKeyException, ResponsabilityUnassignedException, InadequateVaccineException{
        Vaccine vaccine = getVaccine(vaccineKey);
        Employee veterinarian = getVeterinarian(veterinarianKey);
        Animal animal = getAnimal(animalKey);
        if (!((Veterinarian)veterinarian).findResponsability(animal.getSpecie().getKey())){
            throw new ResponsabilityUnassignedException(animal.getSpecie().getKey());
        }
        Vaccination vaccination = new Vaccination(veterinarian, animal, vaccine);
        vaccination.damage();
        _vaccinationLog.add(vaccination);
        vaccine.insertVaccination(vaccination);
        animal.insertHealthState(vaccination);
        ((Veterinarian)veterinarian).insertVaccination(vaccination);
        changed();
        if (!(vaccination.getAnimalHealth().equals("NORMAL"))){
            throw new InadequateVaccineException();
        }
    }

    /**
     * When there is a new registry or something is deleted
     * marks that changes occured in the hotel
     * 
     */
    public void changed(){
        setChanged(true);
    }

    /**
     * Checks if there was any changes in the hotel
     * 
     */
    public boolean hasChanged(){
        return _changed;
    }

    public void setChanged(boolean v){
        _changed = v;
    }
}
