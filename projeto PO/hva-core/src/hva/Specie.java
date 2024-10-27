package hva;

import hva.animal.Animal;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Specie implements Serializable{

    private static final long serialVersionUID = 202410071740L;

    private String _key;
    private String _name;
    private int _veterinarianCount = 0;
    private final Map<String,Animal> _log = new TreeMap();

    public Specie(String key, String name){
        _key = key;
        _name = name;
    }
//-----------------------------getters&setters-----------------------------
    public String getKey(){
        return _key;
    }
    
    public String getName(){
        return _name;
    }
    
    public int getVeterinarianCount(){
        return _veterinarianCount;
    }

    public int getAnimalCount(){
        return _log.size();
    }


    public void setKey(String key){
        _key = key;
    }

    public void setName(String name){
        _name = name;
    }
    
//-------------------------------------------------------------------------
    public void insertAnimal(Animal animal){
        _log.put(animal.getKey().toLowerCase(), animal);
    }

    public Animal findAnimal(String key){
        return _log.get(key.toLowerCase());
    }

    public void removeAnimal(String key){
        _log.remove(key.toLowerCase());
    }

    public void addVeterinarian(){
        _veterinarianCount++;
    }

    public void removeVeterinarian(){
        _veterinarianCount--;
    }
}