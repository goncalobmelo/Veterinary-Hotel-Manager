package hva.tree;

public class EvergreenTreeAutumn extends EvergreenTreeState{

    private static final long serialVersionUID = 202410071845L;

    public EvergreenTreeAutumn(Tree tree){
        super(tree, 1,"COMFOLHAS");
    }

    @Override
    public void changeState(){
        incrementAge();
        getTree().setState(new EvergreenTreeWinter(getTree()));
    }
}