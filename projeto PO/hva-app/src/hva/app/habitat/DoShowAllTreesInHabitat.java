package hva.app.habitat;

import hva.tree.Tree;
import hva.exceptions.WrongHabitatKeyException;
import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.Collection;

class DoShowAllTreesInHabitat extends Command<Hotel> {

    DoShowAllTreesInHabitat(Hotel receiver) {
        super(Label.SHOW_TREES_IN_HABITAT, receiver);
        addStringField("id",Prompt.habitatKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            Collection<Tree> trees = _receiver.getAllHabitatTrees(stringField("id"));
            if(trees.size() > 0){
                _display.popup(trees);
            }
        } 
        catch (WrongHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }

}
