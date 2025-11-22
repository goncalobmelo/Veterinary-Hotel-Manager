package hva.app.vaccine;

import hva.Hotel;
import hva.Vaccination;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.List;

class DoShowVaccinations extends Command<Hotel> {

    DoShowVaccinations(Hotel receiver) {
        super(Label.SHOW_VACCINATIONS, receiver);
    }

    @Override
    protected final void execute() {
        List<Vaccination> log = _receiver.getVaccinationLog();
        if(!log.isEmpty()){
            _display.popup(log);
        }
    }
}
