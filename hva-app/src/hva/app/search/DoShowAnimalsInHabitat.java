package hva.app.search;

import hva.animal.Animal;
import hva.exceptions.WrongHabitatKeyException;
import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.Collection;
//FIXME import other classes if needed

class DoShowAnimalsInHabitat extends Command<Hotel> {

    DoShowAnimalsInHabitat(Hotel receiver) {
        super(Label.ANIMALS_IN_HABITAT, receiver);
        addStringField("habitatId", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            Collection<Animal> animals = _receiver.getAllHabitatAnimals(stringField("habitatId"));
            if(animals.size() > 0){
                _display.popup(animals);
            }
        }
        catch (WrongHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }

}
