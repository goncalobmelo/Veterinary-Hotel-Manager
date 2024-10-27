package hva.app.vaccine;

import hva.exceptions.WrongVaccineKeyException;
import hva.exceptions.WrongEmployeeKeyException;
import hva.exceptions.WrongAnimalKeyException;
import hva.exceptions.WrongVeterinarianKeyException;
import hva.exceptions.ResponsabilityUnassignedException;
import hva.exceptions.InadequateVaccineException;
import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.app.exceptions.UnknownVaccineKeyException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.app.exceptions.VeterinarianNotAuthorizedException;
import hva.exceptions.InadequateVaccineException;
import hva.exceptions.ResponsabilityUnassignedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoVaccinateAnimal extends Command<Hotel> {

    DoVaccinateAnimal(Hotel receiver) {
        super(Label.VACCINATE_ANIMAL, receiver);
        addStringField("vaccineId", Prompt.vaccineKey());
        addStringField("veterinarianId", Prompt.veterinarianKey());
        addStringField("animalId", hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.applyVaccine(stringField("vaccineId"), stringField("veterinarianId"), stringField("animalId"));
        }
        catch(WrongVaccineKeyException e){
            throw new UnknownVaccineKeyException(e.getKey());
        }
        catch(WrongEmployeeKeyException e){
            throw new UnknownEmployeeKeyException(e.getKey());
        }
        catch(WrongAnimalKeyException e){
            throw new UnknownAnimalKeyException(e.getKey());
        }
        catch (WrongVeterinarianKeyException e) {
            throw new UnknownVeterinarianKeyException(e.getKey());
        }
        catch(ResponsabilityUnassignedException e){
            throw new VeterinarianNotAuthorizedException(stringField("veterinarianId"), e.getKey());
        }
        catch(InadequateVaccineException e){
            _display.popup(Message.wrongVaccine(stringField("vaccineId"), stringField("animalId")));
        }
    }

}
