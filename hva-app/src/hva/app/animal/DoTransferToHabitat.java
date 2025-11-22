package hva.app.animal;

import hva.Hotel;
import hva.exceptions.WrongAnimalKeyException;
import hva.exceptions.WrongHabitatKeyException;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoTransferToHabitat extends Command<Hotel> {

    DoTransferToHabitat(Hotel hotel) {
        super(Label.TRANSFER_ANIMAL_TO_HABITAT, hotel);
        addStringField("id",Prompt.animalKey());
        addStringField("habitatId", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            _receiver.changeAnimalHabitat(stringField("id"), stringField("habitatId"));
        }
        catch(WrongAnimalKeyException e){
            throw new UnknownAnimalKeyException(e.getKey());
        }   
        catch(WrongHabitatKeyException e){
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }

}
