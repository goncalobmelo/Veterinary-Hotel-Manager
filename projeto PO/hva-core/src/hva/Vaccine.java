package hva;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Vaccine implements Serializable{

    private static final long serialVersionUID = 202410071750L;

    private String _key;
    private String _name;
    private final Map<String, Specie> _species = new TreeMap();
    private final List<Vaccination> _log = new ArrayList();

    public Vaccine(String key, String name){
        _key = key;
        _name = name;
    }

    public Vaccine(String key, String name, Specie[] species){
        this(key, name);
        for(Specie specie: species){
            _species.put(specie.getKey().toLowerCase(), specie);
        }
    }

    public String getKey(){
        return _key;
    }

    public String getName(){
        return _name;
    }

    public Vaccination getVaccination(int index){
        return _log.get(index);
    }

    public void insertSpecie(Specie specie){
        _species.put(specie.getKey().toLowerCase(), specie);
    }

    public boolean findSpecie(String key){
        return _species.containsKey(key.toLowerCase());
    }

    public void insertVaccination(Vaccination vaccination){
        _log.add(vaccination);
    }

    public Collection<Specie> getVaccineSpecies(){
        return _species.values();
    }

    public String getSpeciesKeys(){
        String keys = "";
        for(Specie specie : _species.values()){
            if(keys.equals("")){
                keys = specie.getKey();
            }
            else{
                keys += "," + specie.getKey();
            }
        }
        return keys;
    }

    @Override
    public String toString(){
        if(_species.isEmpty()){
            return "VACINA|" + _key + "|" + _name + "|" + _log.size();
        }
        String speciesKeys = getSpeciesKeys();
        return "VACINA|" + _key + "|" + _name + "|" + _log.size() + "|" + speciesKeys;
    }
}