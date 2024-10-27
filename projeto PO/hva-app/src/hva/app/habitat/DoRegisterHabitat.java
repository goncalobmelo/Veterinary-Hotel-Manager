package hva.app.habitat;

import hva.exceptions.DupHabitatKeyException;
import hva.exceptions.WrongTreeKeyException;
import hva.Hotel;
import hva.app.exceptions.DuplicateHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoRegisterHabitat extends Command<Hotel> {

    DoRegisterHabitat(Hotel receiver) {
        super(Label.REGISTER_HABITAT, receiver);
        addStringField("id",Prompt.habitatKey());
        addStringField("name", Prompt.habitatName());
        addIntegerField("area", Prompt.habitatArea());
    }

    @Override
    protected void execute() throws CommandException {
        String area = Integer.toString(integerField("area"));
        String[] fields = {"HABITAT", stringField("id"), stringField("name"), area};
        try {
            _receiver.registerHabitat(fields);
        }
        catch (DupHabitatKeyException e) {
            throw new DuplicateHabitatKeyException(e.getKey());
        }
        catch(WrongTreeKeyException e){
            e.printStackTrace();
        }
    }

}
