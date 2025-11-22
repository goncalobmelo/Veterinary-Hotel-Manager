package hva.animal;

import hva.habitat.Habitat;

public class AnimalSatisfactionCalculator implements AnimalCalculator{
    private static final long serialVersionUID = 202410211212L;

    @Override
    public long calculate(Animal animal){
        Habitat habitat = animal.getHabitat();
        int equals = animal.equals();
        int differents = animal.differents();
        int population = habitat.getAnimalCount();
        int area = habitat.getArea();
        int adequacy = habitat.getImpact(animal.getSpecie().getKey());
        return Math.round(20 + (3 * equals) - (2 * differents) + (area / (double) population) + adequacy);
    }
}