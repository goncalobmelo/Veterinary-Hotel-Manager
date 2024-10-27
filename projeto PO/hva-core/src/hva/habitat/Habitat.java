package hva.habitat;

import hva.Specie;
import hva.animal.Animal;
import hva.tree.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Habitat implements Serializable{

    private static final long serialVersionUID = 202410071735L;

    private String _key;
    private String _name;
    private int _area;
    private int _treeCount = 0;
    private int _keeperCount = 0;
    private final Map<String, Animal> _animalLog = new TreeMap();
    private final Map<String,Integer> _specieCount = new TreeMap();
    private final Map<String,Tree> _treeLog = new TreeMap();
    private final Map<String,Impact> _impactLog = new TreeMap();

    public Habitat(String key, String name, int area){
        _key = key;
        _name = name;
        _area = area;
    }

    public Habitat(String key, String name, int area, Tree[] trees){
        this(key, name, area);
        for(Tree tree: trees){
            _treeCount++;
            _treeLog.put(tree.getKey().toLowerCase(), tree);
        }
    }

//-----------------------------getters&setters-----------------------------
    public String getKey(){
        return _key;
    }

    public String getName(){
        return _name;
    }

    public int getArea(){
        return _area;
    }

    public int getTreeCount(){
        return _treeCount;
    }

    public int getKeeperCount(){
        return _keeperCount;
    }

    public int getAnimalCount(){
        return _animalLog.size();
    }

    public Animal getAnimal(String animalKey){
        return _animalLog.get(animalKey.toLowerCase());
    }

    public int getSpecieCount(String specieKey){
        return _specieCount.get(specieKey.toLowerCase());
    }

    public void setKey(String key){
        _key = key;
    }
    
    public void setName(String name){
        _name = name;
    }

    public void setArea(int area){
        _area = area;
    }

    public void setTreeCount(int treeCount){
        _treeCount = treeCount;
    }

    public void setkeeperCount(int keeperCount){
        _keeperCount = keeperCount;
    }

    public int getImpact(String specieKey){
        Impact impact = _impactLog.get(specieKey.toLowerCase());
        return impact.adequacy();
    }

//-------------------------------------------------------------------------

    public void insertSpecieCount(Animal animal){
        String specieKey = (animal.getSpecie()).getKey().toLowerCase();
        if(_specieCount.containsKey(specieKey)){
            int count = _specieCount.get(specieKey);
            _specieCount.put(specieKey,++count);
        }
        else{
            _specieCount.put(specieKey, 1);
        }
        _animalLog.put(animal.getKey().toLowerCase(), animal);
        insertImpactLog(specieKey);
    }

    public void insertImpactLog(String specieKey){
        _impactLog.put(specieKey.toLowerCase(), new NeutralImpact());
    }

    public void changeImpactLog(String specieKey, Impact impact){
        _impactLog.put(specieKey.toLowerCase(),impact);
    }

    public void insertTree(Tree tree){
        _treeLog.put(tree.getKey().toLowerCase(), tree);
        _treeCount++;
    }

    public Tree findTree(String treeKey){
        return _treeLog.get(treeKey.toLowerCase());
    }

    public void removeTree(Tree tree){
        _treeLog.remove(tree.getKey().toLowerCase());
        _treeCount--;
    }

    public void removeSpecieCount(Animal animal){
        Specie specie = animal.getSpecie();
        if (getSpecieCount(specie.getKey().toLowerCase()) > 1){
            int count = _specieCount.get(specie.getKey().toLowerCase());
            _specieCount.put(specie.getKey().toLowerCase(),--count);
        }
        else{
            _specieCount.remove(specie.getKey().toLowerCase());
        }   
        _animalLog.remove(animal.getKey().toLowerCase());
    }

    public void addKeeper(){
        _keeperCount++;
    }

    public void removeKeeper(){
        _keeperCount--;
    }

    public double workInHabitat(){
        double work = 0;
        work += _area;
        work += (3*getAnimalCount());
        for(Tree tree: _treeLog.values()){
            work += tree.cleaningEffort();
        }
        return work;
    }

    public Collection<Tree> getAllTrees(){
        return Collections.unmodifiableCollection(_treeLog.values());
    }

    public Collection<Animal> getAllAnimals(){
        return Collections.unmodifiableCollection(_animalLog.values());
    }

    @Override
    public String toString(){
        return "HABITAT|" + _key + "|" + _name + "|" + _area + "|" + _treeCount;
    }
}
