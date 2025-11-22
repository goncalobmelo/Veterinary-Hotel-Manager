package hva;

import hva.exceptions.*;
import hva.season.Season;
import java.io.*;
//FIXME import other Java classes
//FIXME import other project classes

/**
 * Class that represents the hotel application.
 */
public class HotelManager {

    /** The network manager. */
    private String _filename = "";

    /** This is the current hotel. */
    private Hotel _hotel = new Hotel();

    // FIXME maybe add more fields if needed

    public Integer advanceSeason(){
        _hotel.advanceSeason();
        Season season = _hotel.getSeason();
        return season.getCode();
    }

    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        if (_filename == null || _filename.equals(""))
            throw new MissingFileAssociationException();
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))) {
            oos.writeObject(_hotel);
            _hotel.setChanged(false);
        }
    }

    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
        _filename = filename;
        save();
    }

    /**
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     */
    public void load(String filename) throws UnavailableFileException {

        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            _hotel = (Hotel) ois.readObject();
            _hotel.setChanged(false);
            _filename = filename;
        }
        catch(IOException | ClassNotFoundException e){ throw new UnavailableFileException(filename);}
    }

    /**
     * Read text input file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        _hotel.importFile(filename);
    }

    public Hotel getHotel(){ return _hotel;}

    public Long getHotelSatisfaction(){
        return getHotel().getAllSatisfactions();
    }

    /**
     * @return filename
     */
    public String getFilename() {
        return _filename;
    }

    /**
     * @param filename
     */
    public void setFilename(String filename) {
        _filename = filename;
    }

    /**
     * @return changed?
     */
    public boolean changed() {
        return _hotel.hasChanged();
    }

    /**
    * Reset the network.
    */
    public void reset() {
        _hotel = new Hotel();
        _filename = null;
    }
}
