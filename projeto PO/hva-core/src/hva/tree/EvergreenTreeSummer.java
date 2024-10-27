package hva.tree;

public class EvergreenTreeSummer extends EvergreenTreeState{

    private static final long serialVersionUID = 202410071830L;

    public EvergreenTreeSummer(Tree tree){
        super(tree, 1,"COMFOLHAS");
    }

    @Override
    public void changeState(){
        incrementAge();
        getTree().setState(new EvergreenTreeAutumn(getTree()));
    }
}