package hva.employee;

import hva.Specie;
import java.util.Collection;

public class VeterinarianSatisfactionCalculator implements VeterinarianCalculator{
    private static final long serialVersionUID = 202410231714L;
    
    @Override
    public long calculate(Veterinarian veterinarian){
        Collection<Specie> responsabilites = veterinarian.getResponsabilities();
        double work = 0;
        for(Specie specie : responsabilites){
            int animalCount = specie.getAnimalCount();
            work += animalCount/(double)specie.getVeterinarianCount();
        }
        return Math.round(20 - work);
    }
}