package hva.app.animal;

import hva.app.exceptions.UnknownAnimalKeyException;
import hva.exceptions.WrongAnimalKeyException;
import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowSatisfactionOfAnimal extends Command<Hotel> {

    DoShowSatisfactionOfAnimal(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_ANIMAL, receiver);
        addStringField("id",Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            Long satisfaction = _receiver.getAnimalSatisfaction(stringField("id"));
            _display.popup(satisfaction);
        }
        catch(WrongAnimalKeyException e){
            throw new UnknownAnimalKeyException(e.getKey());
        }
    }

}
