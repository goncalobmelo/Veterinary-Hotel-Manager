package hva.app.vaccine;

import hva.exceptions.DupVaccineKeyException;
import hva.exceptions.WrongSpecieKeyException;
import hva.app.exceptions.DuplicateVaccineKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoRegisterVaccine extends Command<Hotel> {

    DoRegisterVaccine(Hotel receiver) {
        super(Label.REGISTER_VACCINE, receiver);
        addStringField("id", Prompt.vaccineKey());
        addStringField("name", Prompt.vaccineName());
        addStringField("speciesId", Prompt.listOfSpeciesKeys());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            String[] fields = {"VACINA", stringField("id"), stringField("name"), stringField("speciesId")};
            _receiver.registerVaccine(fields);
        }
        catch (DupVaccineKeyException e) {
            throw new DuplicateVaccineKeyException(e.getKey());
        }
        catch (WrongSpecieKeyException e){
            throw new UnknownSpeciesKeyException(e.getKey());
        }
    }

}
