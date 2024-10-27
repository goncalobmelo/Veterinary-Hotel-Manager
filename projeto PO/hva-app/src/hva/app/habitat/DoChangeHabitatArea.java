package hva.app.habitat;

import hva.exceptions.WrongHabitatKeyException;
import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoChangeHabitatArea extends Command<Hotel> {

    DoChangeHabitatArea(Hotel receiver) {
        super(Label.CHANGE_HABITAT_AREA, receiver);
        addStringField("id",Prompt.habitatKey());
        addIntegerField("area", Prompt.habitatArea());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _receiver.updateHabitatArea(stringField("id"), integerField("area"));
        }
        catch (WrongHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }

}
