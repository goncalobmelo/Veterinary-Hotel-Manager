package hva.app.habitat;

import hva.app.exceptions.DuplicateTreeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.DupTreeKeyException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.WrongHabitatKeyException;
import hva.exceptions.WrongTreeKeyException;
import hva.tree.Tree;
import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.Collection;
//FIXME import other classes if needed

class DoAddTreeToHabitat extends Command<Hotel> {

    DoAddTreeToHabitat(Hotel receiver) {
        super(Label.ADD_TREE_TO_HABITAT, receiver);
        addStringField("habitatId",Prompt.habitatKey());
        addStringField("treeId", Prompt.treeKey());
        addStringField("name", Prompt.treeName());
        addIntegerField("age", Prompt.treeAge());
        addIntegerField("difficulty", Prompt.treeDifficulty());
        addOptionField("type", Prompt.treeType(), "CADUCA", "PERENE");
    }

    @Override
    protected void execute() throws CommandException {
        String age = Integer.toString(integerField("age"));
        String difficulty = Integer.toString(integerField("difficulty"));
        String[] fields = {"ÁRVORE", stringField("treeId"), stringField("name"), age, difficulty, optionField("type")};
        try {
            Collection<Tree> tree = _receiver.addTreeToHabitat(stringField("habitatId"), fields);
            _display.popup(tree);
        } 
        catch (WrongHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
        catch(WrongTreeKeyException e){
            e.printStackTrace();
        }
        catch(UnrecognizedEntryException e){
            e.printStackTrace();
        }
        catch(DupTreeKeyException e){
            throw new DuplicateTreeKeyException(e.getKey());
        }
    }

}
