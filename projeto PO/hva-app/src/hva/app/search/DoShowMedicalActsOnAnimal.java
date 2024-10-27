package hva.app.search;

import hva.Vaccination;
import hva.exceptions.WrongAnimalKeyException;
import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.List;
//FIXME import other classes if needed

class DoShowMedicalActsOnAnimal extends Command<Hotel> {

    DoShowMedicalActsOnAnimal(Hotel receiver) {
        super(Label.MEDICAL_ACTS_ON_ANIMAL, receiver);
        addStringField("id", hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            List<Vaccination> log = _receiver.getAnimalVaccinationLog(stringField("id"));
            if(!log.isEmpty()){
                _display.popup(log);
            }
        }
        catch (WrongAnimalKeyException e) {
            throw new UnknownAnimalKeyException(e.getKey());
        }
    }

}
