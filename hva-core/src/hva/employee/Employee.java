package hva.employee;

import java.io.Serializable;

public abstract class Employee implements Serializable{

    private static final long serialVersionUID = 202410071755L;

    private final String _key;
    private final String _name;

    public Employee(String key, String name){
        _key = key;
        _name = name;
    }

    public String getKey(){
        return _key;
    }

    public String getName(){
        return _name;
    }

    @Override
    public abstract String toString();
}