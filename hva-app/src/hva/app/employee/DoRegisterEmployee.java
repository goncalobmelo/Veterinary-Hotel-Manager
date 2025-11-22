package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.DuplicateEmployeeKeyException;
import hva.exceptions.DupEmployeeKeyException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.WrongHabitatKeyException;
import hva.exceptions.WrongSpecieKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoRegisterEmployee extends Command<Hotel> {

    DoRegisterEmployee(Hotel receiver) {
        super(Label.REGISTER_EMPLOYEE, receiver);
        addStringField("id",Prompt.employeeKey());
        addStringField("name",Prompt.employeeName());
        addOptionField("type",Prompt.employeeType(), "VET", "TRT");

    }

    @Override
    protected void execute() throws CommandException {
        try{
            switch(optionField("type")){
                case "VET" -> {
                    String[] fields = {"VETERINÁRIO",stringField("id"), stringField("name")};
                    _receiver.registerEmployee(fields);
                }
                case "TRT" -> {
                    String[] fields = {"TRATADOR",stringField("id"), stringField("name")};
                    _receiver.registerEmployee(fields);
                }
            }
        }
        catch(UnrecognizedEntryException e){
            e.printStackTrace();
        }
        catch(DupEmployeeKeyException e){
            throw new DuplicateEmployeeKeyException(e.getKey());
        }
        catch(WrongHabitatKeyException e){
            e.printStackTrace();
        }
        catch(WrongSpecieKeyException e){
            e.printStackTrace();
        }
    }

}
