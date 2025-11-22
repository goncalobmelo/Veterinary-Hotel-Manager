package hva.app.habitat;

import hva.exceptions.WrongHabitatKeyException;
import hva.exceptions.WrongSpecieKeyException;
import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoChangeHabitatInfluence extends Command<Hotel> {

    DoChangeHabitatInfluence(Hotel receiver) {
        super(Label.CHANGE_HABITAT_INFLUENCE, receiver);
        addStringField("habitatId",Prompt.habitatKey());
        addStringField("specieId", hva.app.animal.Prompt.speciesKey());
        addOptionField("influence", Prompt.habitatInfluence(), "POS", "NEG", "NEU");
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _receiver.changeHabitatInfluence(stringField("habitatId"), stringField("specieId"), optionField("influence"));
        } 
        catch (WrongHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
        catch(WrongSpecieKeyException e){
            throw new UnknownSpeciesKeyException(e.getKey());
        }
    }

}
