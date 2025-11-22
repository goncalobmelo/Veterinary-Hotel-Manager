package hva.tree;

public class EvergreenTreeWinter extends EvergreenTreeState{

    private static final long serialVersionUID = 202410071825L;

    public EvergreenTreeWinter(Tree tree){
        super(tree, 2,"LARGARFOLHAS");
    }

    @Override
    public void changeState(){
        incrementAge();
        getTree().setState(new EvergreenTreeSpring(getTree()));
    }
}