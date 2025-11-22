package hva.employee;

import hva.Specie;
import hva.Vaccination;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Veterinarian extends Employee{

    private static final long serialVersionUID = 202410071810L;
    private final Map<String,Specie> _responsability = new TreeMap<>();
    private List<Vaccination> _vaccinationLog = new ArrayList<>();
    private VeterinarianCalculator _calculator;

    public Veterinarian(String key, String name, VeterinarianCalculator calculator){
        super(key, name);
        _calculator = calculator;
    }

    public Veterinarian(String key, String name, VeterinarianCalculator calculator, Specie[] species){
        this(key, name, calculator);
        for(Specie specie: species){
            _responsability.put(specie.getKey().toLowerCase(), specie);
        }
    }

    public boolean insertResponsability(Specie specie){
        if(!_responsability.containsKey(specie.getKey().toLowerCase())){
            _responsability.put(specie.getKey().toLowerCase(), specie);
            return true;
        }
        return false;
    }

    public boolean removeResponsability(Specie specie){
        if(_responsability.containsKey(specie.getKey().toLowerCase())){
            _responsability.remove(specie.getKey().toLowerCase());
            return true;
        }
        return false;
    }
        
    public String getReponsabilityKeys(){
        String keys = "";
        for(Specie specie : _responsability.values()){
            if(keys.equals("")){
                keys = specie.getKey();
            }
            else{
                keys += "," + specie.getKey();
            }
        }
        return keys;
    }

    public List<Vaccination> getVaccinationLog(){
        return Collections.unmodifiableList(_vaccinationLog);
    }

    public boolean findResponsability(String specieKey){
        return _responsability.containsKey(specieKey.toLowerCase());
    }

    public long getSatisfaction(){
        return _calculator.calculate(this);
    }

    public Collection<Specie> getResponsabilities(){
        return _responsability.values();
    }

    public void insertVaccination(Vaccination vaccination){
        _vaccinationLog.add(vaccination);
    }

    @Override
    public String toString(){
        if(_responsability.isEmpty()){
            return "VET|" + getKey() + "|" + getName();
        }
        String responsabilityKeys = getReponsabilityKeys();
        return "VET|" + getKey() + "|" + getName() + "|" + responsabilityKeys;
    }
}