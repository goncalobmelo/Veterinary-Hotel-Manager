package hva;

import hva.animal.Animal;
import hva.employee.Employee;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Vaccination implements Serializable{

    private static final long serialVersionUID = 202410071745L;

    private final Employee _veterinarian;
    private final Animal _animal;
    private final Vaccine _vaccine;
    private String _animalHealth;

    public Vaccination(Employee veterinarian, Animal animal, Vaccine vaccine){
        _veterinarian = veterinarian;
        _animal = animal;
        _vaccine = vaccine;
    }

    public Employee getVeterinarian(){
        return _veterinarian;
    }

    public Animal getAnimal(){
        return _animal;
    }

    public Vaccine getVaccine(){
        return _vaccine;
    }

    public String getAnimalHealth(){
        return _animalHealth;
    }

    public int commonCharacters(String specie1, String specie2){
        List<Character> aux = new ArrayList<>();
        int common = 0;
        
        for(char c : specie1.toLowerCase().toCharArray()){
            aux.add(c);
        }
        
        for(char c : specie2.toLowerCase().toCharArray()){
            if(!aux.isEmpty()){
                for(int i = 0; i < aux.size(); i++){
                    if(c == aux.get(i)){
                        common++;
                        aux.remove(i);
                        break;
                    }
                }
            }
            else{
                break;
            }
        }
        return common;
    }

    public void damage(){
        if(getVaccine().findSpecie(_animal.getSpecie().getKey())){
            _animalHealth = "NORMAL";
            return;
        }

        Collection<Specie> species = getVaccine().getVaccineSpecies();
        String specieName = _animal.getSpecie().getName();
        int specieSize = specieName.length();
        int nameSize;
        int damage = 0;

        for(Specie specie : species){
            nameSize = specieSize;
            if(specie.getName().length() > specieSize){
                nameSize = specie.getName().length();
            }

            int commonCharacters = commonCharacters(specieName,specie.getName());
            int damageAux = nameSize - commonCharacters;
            
            if(damageAux > damage){
                damage = damageAux;
            }
        }

        if(damage == 0){
            _animalHealth = "CONFUSÃO";
        }

        else if(damage >= 1 && damage <= 4){
            _animalHealth = "ACIDENTE";
        }

        else{
            _animalHealth = "ERRO";
        }

    }

    @Override
    public String toString(){
        return "REGISTO-VACINA|" + _vaccine.getKey() + "|" + _veterinarian.getKey() + "|" + _animal.getSpecie().getKey();
    }
}