package hva.app.animal;

import hva.exceptions.DupAnimalKeyException;
import hva.exceptions.DupSpecieNameException;
import hva.exceptions.WrongHabitatKeyException;
import hva.exceptions.WrongSpecieKeyException;
import hva.Hotel;
import hva.app.exceptions.DuplicateAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoRegisterAnimal extends Command<Hotel> {

    DoRegisterAnimal(Hotel receiver) {
        super(Label.REGISTER_ANIMAL, receiver);
        addStringField("id",Prompt.animalKey());
        addStringField("name", Prompt.animalName());
        addStringField("specieId", Prompt.speciesKey());
        addStringField("habitatId", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String[] fields = {"ANIMAL", stringField("id"), stringField("name"), stringField("specieId"), stringField("habitatId")};
        boolean register = false;
        while(!register){
            try {
                _receiver.registerAnimal(fields);
                register = true;
            } 
            catch (DupAnimalKeyException e) {
                throw new DuplicateAnimalKeyException(e.getKey());
            }
            catch(WrongHabitatKeyException e){
                throw new UnknownHabitatKeyException(e.getKey());
            }
            catch (WrongSpecieKeyException e){
                try{
                    String name = Form.requestString(Prompt.speciesName());
                    if(_receiver.newSpecieName(name)){
                        String[] specieFields = {"SPECIE", fields[3], name};
                        _receiver.registerSpecie(specieFields);
                    }
                }
                catch(DupSpecieNameException e1){
                    e1.printStackTrace();
                }
            }
        }
    }

}
