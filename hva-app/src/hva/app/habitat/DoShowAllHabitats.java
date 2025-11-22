package hva.app.habitat;

import java.util.Collection;
import hva.tree.Tree;
import hva.habitat.Habitat;
import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowAllHabitats extends Command<Hotel> {

    DoShowAllHabitats(Hotel receiver) {
        super(Label.SHOW_ALL_HABITATS, receiver);
    }

    @Override
    protected void execute() {
        Collection<Habitat> habitats = _receiver.getAllHabitats();
        Collection<Tree> trees;

        if(!habitats.isEmpty()){
            for(Habitat habitat: habitats){
                if(habitat.getTreeCount() > 0){
                    trees = habitat.getAllTrees();
                    _display.popup(habitat);
                    _display.popup(trees);
                }
                else{
                    _display.popup(habitat);
                }
            }
        }
    }
}
