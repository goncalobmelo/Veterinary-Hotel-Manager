package hva.animal;

import java.io.Serializable;

public interface AnimalCalculator extends Serializable{
    public long calculate(Animal animal);
}