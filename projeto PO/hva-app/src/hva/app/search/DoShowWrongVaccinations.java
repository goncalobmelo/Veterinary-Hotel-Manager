package hva.app.search;

import hva.Vaccination;
import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.List;

class DoShowWrongVaccinations extends Command<Hotel> {

    DoShowWrongVaccinations(Hotel receiver) {
        super(Label.WRONG_VACCINATIONS, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        List<Vaccination> log = _receiver.getWrongVaccinations();
            if(!log.isEmpty()){
                _display.popup(log);
            }
    }

}
