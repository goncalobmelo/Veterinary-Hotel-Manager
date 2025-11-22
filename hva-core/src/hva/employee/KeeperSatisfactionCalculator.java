package hva.employee;

import java.util.Collection;
import hva.habitat.Habitat;

public class KeeperSatisfactionCalculator implements KeeperCalculator {
    private static final long serialVersionUID = 202410231549L;
    
    @Override
    public long calculate(Keeper keeper){
        Collection<Habitat> responsabilities = keeper.getResponsabilities();
        double work = 0;
        for(Habitat habitat : responsabilities){ 
            double workHabitat = habitat.workInHabitat();
            work += workHabitat/habitat.getKeeperCount();
        }
        return Math.round(300 - work);
    }
}
