package hva.employee;

import java.io.Serializable;

public interface VeterinarianCalculator extends Serializable{
    public long calculate(Veterinarian veterinarian);
}
