package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.NoResponsibilityException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.ResponsabilityUnassignedException;
import hva.exceptions.WrongEmployeeKeyException;
import hva.exceptions.WrongHabitatKeyException;
import hva.exceptions.WrongSpecieKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRemoveResponsibility extends Command<Hotel> {

    DoRemoveResponsibility(Hotel receiver) {
        super(Label.REMOVE_RESPONSABILITY, receiver);
        addStringField("employeeId",Prompt.employeeKey());
        addStringField("responsabilityId",Prompt.responsibilityKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _receiver.removeResponsability(stringField("employeeId"),stringField("responsabilityId"));
        }
        catch (WrongEmployeeKeyException e){
            throw new UnknownEmployeeKeyException(e.getKey());
        }
        catch (WrongHabitatKeyException e){
            throw new NoResponsibilityException(stringField("employeeId"), e.getKey());
        }
        catch(WrongSpecieKeyException e){
            throw new NoResponsibilityException(stringField("employeeId"), e.getKey());
        }
        catch(ResponsabilityUnassignedException e){
            throw new NoResponsibilityException(stringField("employeeId"), e.getKey());
        }
    }

}
