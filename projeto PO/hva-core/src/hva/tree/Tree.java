package hva.tree;

import java.io.Serializable;

public class Tree implements Serializable{

    private static final long serialVersionUID = 202410071815L;

    private String _key;
    private String _name;
    private double _age;
    private int _cleaningDifficulty;
    private TreeState _state;

    public Tree(String key, String name, int age, int cleaningDifficulty){
        _key = key;
        _name = name;
        _age = age;
        _cleaningDifficulty = cleaningDifficulty;
    }

//-----------------------------getters&setters-----------------------------
    
    public String getKey(){
        return _key;
    }

    public String getName(){
        return _name;
    }

    public int getAge(){
        return (int)_age;
    }

    public int getCleaningDifficulty(){
        return _cleaningDifficulty;
    }

    public TreeState getState(){
        return _state;
    }

    public void setKey(String key){
        _key = key;
    }

    public void setName(String name){
        _name = name;
    }

    public void setAge(int age){
        _age = age;
    }

    public void setCleaningDifficulty(int cleaningDifficulty){
        _cleaningDifficulty = cleaningDifficulty;
    }

    public void setState(TreeState state){
        _state = state;
    }

//-------------------------------------------------------------------------

    public void incrementAge(){
        _age += 0.25;
    }

    public double cleaningEffort(){
        double effort = getCleaningDifficulty();
        effort *= getState().getSeasonCleaningEffort();
        effort *= Math.log(getAge() + 1);
        return effort;
    }

    @Override
    public String toString(){
        return "ÁRVORE|" + _key + "|" + _name + "|" + getAge() + "|" + _cleaningDifficulty + "|" + _state.toString();
    }
}