package hva.habitat;

import java.io.Serializable;

public abstract class Impact implements Serializable{

    private static final long serialVersionUID = 202410211127L;

    public abstract int adequacy();
}