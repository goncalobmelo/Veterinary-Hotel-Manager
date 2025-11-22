package hva.app.search;

import hva.Vaccination;
import hva.exceptions.WrongEmployeeKeyException;
import hva.exceptions.WrongVeterinarianKeyException;
import hva.Hotel;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.List;

//FIXME import other classes if needed

class DoShowMedicalActsByVeterinarian extends Command<Hotel> {

    DoShowMedicalActsByVeterinarian(Hotel receiver) {
        super(Label.MEDICAL_ACTS_BY_VET, receiver);
	    addStringField("id", hva.app.employee.Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            List<Vaccination> log = _receiver.getVeterinarianVaccinationLog(stringField("id"));
            if(!log.isEmpty()){
                _display.popup(log);
            }
        } 
        catch (WrongVeterinarianKeyException e){
            throw new UnknownVeterinarianKeyException(e.getKey());
        }
        
        catch(WrongEmployeeKeyException e){
            throw new UnknownVeterinarianKeyException(e.getKey());
        }
    }
}
