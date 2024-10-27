package hva.employee;

import hva.habitat.Habitat;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Keeper extends Employee{

    private static final long serialVersionUID = 202410071800L;
    private final Map<String,Habitat> _responsability = new TreeMap<>();
    private KeeperCalculator _calculator;

    public Keeper(String key, String name, KeeperCalculator calculator){
        super(key, name);
        _calculator = calculator;
    }

    public Keeper(String key, String name, KeeperCalculator calculator, Habitat[] habitats){
        this (key, name, calculator);
        for(Habitat habitat: habitats){
            _responsability.put(habitat.getKey().toLowerCase(), habitat);
        }
    }

    public boolean insertResponsability(Habitat habitat){
        if(!_responsability.containsKey(habitat.getKey().toLowerCase())){
            _responsability.put(habitat.getKey().toLowerCase(), habitat);
            return true;   
        }
        return false;
    }
    
    public boolean removeResponsability(Habitat habitat){
        if(_responsability.containsKey(habitat.getKey().toLowerCase())){
            _responsability.remove(habitat.getKey().toLowerCase());
            return true;
        }
        return false;
    }

    public String getReponsabilityKeys(){
        String keys = "";
        for(Habitat habitat : _responsability.values()){
            if(keys.equals("")){
                keys = habitat.getKey();
            }
            else{
                keys += "," + habitat.getKey();
            }
        }
        return keys;
    }

    public Long getSatisfaction(){
        return _calculator.calculate(this);
    }

    public Collection<Habitat> getResponsabilities(){
        return _responsability.values();
    }

    @Override
    public String toString(){
        if(_responsability.isEmpty()){
            return "TRT|" + getKey() + "|" + getName();
        }
        String responsabilityKeys = getReponsabilityKeys();
        return "TRT|" + getKey() + "|" + getName() + "|" + responsabilityKeys;
    }
}