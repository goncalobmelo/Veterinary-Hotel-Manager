package hva.season;

import java.io.Serializable;

public class Season implements Serializable{

    private static final long serialVersionUID = 202410211120L;

    private int _code = 0;
    private SeasonState _state = new SpringSeasonState(this);

    public int getCode(){
        return _code;
    }

    public SeasonState getState(){
        return _state;
    }

    public void setCode(int code){
        _code = code;
    }

    public void setState(SeasonState state){
        _state = state;
    }
}