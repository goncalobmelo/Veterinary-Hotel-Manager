package hva.employee;

import java.io.Serializable;

public interface KeeperCalculator extends Serializable{
    public long calculate(Keeper keeper);
}
