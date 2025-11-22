package hva.app.animal;

import java.util.Collection;
import hva.animal.Animal;
import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
//FIXME import other classes if needed

class DoShowAllAnimals extends Command<Hotel> {

    DoShowAllAnimals(Hotel receiver) {
        super(Label.SHOW_ALL_ANIMALS, receiver);
    }

    @Override
    protected final void execute() {
        // fix//
        Collection<Animal> animals = _receiver.getAllAnimals();

        if(!animals.isEmpty()){
            _display.popup(animals);
        }
    }

}
