package hva.animal;

import hva.Specie;
import hva.Vaccination;
import hva.habitat.Habitat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Animal implements Serializable {

    private static final long serialVersionUID = 202410071730L;

    private String _key;
    private String _name;
    private Specie _specie;
    private List<Vaccination> _healthState = new ArrayList<>();
    private Habitat _habitat;
    private AnimalCalculator _calculator;

    public Animal(String key, String name, Specie specie, Habitat habitat, AnimalCalculator calculator){
        _key = key;
        _name = name;
        _specie = specie;
        _habitat = habitat;
        _calculator = calculator;
    }
//-----------------------------getters&setters-----------------------------
    public String getKey(){
        return _key;
    }
    
    public String getName(){
        return _name;
    }
    
    public Specie getSpecie(){
        return _specie;
    }

    public String getHealthState(){
        String healthState = "";
        if(_healthState.isEmpty()){
            healthState = "VOID";
        }
        else{
            for(Vaccination vaccination: _healthState){
                if(healthState.equals("")){
                    healthState = vaccination.getAnimalHealth();
                }
                else{
                    healthState += "," + vaccination.getAnimalHealth();
                }
            }
        }
        return healthState;
    }

    public Habitat getHabitat(){
        return _habitat;
    }

    public List<Vaccination> getVaccinationLog(){
        return Collections.unmodifiableList(_healthState);
    }

    public void setKey(String key){
        _key = key;
    }
    
    public void setName(String name){
        _name = name;
    }
    
    public void setSpecie(Specie specie){
        _specie = specie;
    }
    
    public void setHabitat(Habitat habitat){
        _habitat = habitat;
    }
    
//-------------------------------------------------------------------------
    public int equals(){
        return _habitat.getSpecieCount(_specie.getKey()) - 1;
    }

    public int differents(){
        return _habitat.getAnimalCount() - _habitat.getSpecieCount(_specie.getKey());
    }

    public long calculateSatisfaction(){
        return _calculator.calculate(this);
    }

    public void insertHealthState(Vaccination vaccination){
        _healthState.add(vaccination);
    }

    @Override
    public String toString(){
        String healthState = getHealthState();
        return "ANIMAL|" + _key + "|" + _name + "|" + _specie.getKey() + "|" + healthState + "|" + _habitat.getKey();
    }
}