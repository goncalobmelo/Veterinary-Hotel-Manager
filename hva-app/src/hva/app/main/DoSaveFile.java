package hva.app.main;

import hva.HotelManager;
import hva.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import java.io.IOException;
import java.io.FileNotFoundException;
//FIXME import other classes if needed

class DoSaveFile extends Command<HotelManager> {
    DoSaveFile(HotelManager receiver) {
        super(Label.SAVE_FILE, receiver, r -> r.getHotel() != null);
    }

    @Override
    protected final void execute() {
        try{
            try { _receiver.save();} 
            catch (MissingFileAssociationException e) {
                boolean saved = false;
                while(!saved){
                    try {
                        _receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
                        saved = true;
                    } 
                    catch (MissingFileAssociationException e1){
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
