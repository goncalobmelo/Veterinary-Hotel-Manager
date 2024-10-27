package hva.app.vaccine;

import java.util.Collection;
import hva.Vaccine;
import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowAllVaccines extends Command<Hotel> {

    DoShowAllVaccines(Hotel receiver) {
        super(Label.SHOW_ALL_VACCINES, receiver);
	//FIXME add command fields if needed
    }

    @Override
    protected final void execute() {
        Collection<Vaccine> vaccines = _receiver.getAllVaccines();

        if(!vaccines.isEmpty()){
            _display.popup(vaccines);
        }
    }
}
