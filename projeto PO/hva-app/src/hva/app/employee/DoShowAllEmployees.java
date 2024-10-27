package hva.app.employee;

import java.util.Collection;
import hva.employee.Employee;
import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowAllEmployees extends Command<Hotel> {

    DoShowAllEmployees(Hotel receiver) {
        super(Label.SHOW_ALL_EMPLOYEES, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        Collection<Employee> employees = _receiver.getAllEmployees();

        if(!employees.isEmpty()){
            _display.popup(employees);
        }
    }

}
