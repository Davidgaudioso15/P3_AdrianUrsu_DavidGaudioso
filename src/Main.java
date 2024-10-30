public class Main {
    public static void main(String[] args) throws Exception {
        //System.out.println("Adrian".compareTo("David"));

        BinaryTree a = new BinaryTree();
        a.addNode(new Person(1, "Barcelona", "Adrian"),"");
        a.addNode(new Person(2, "Barcelona", "David"),"L");
        a.addNode(new Person(3, "Barcelona", "Rosa"),"R");
        a.addNode(new Person(4, "Barcelona", "Pau"),"LL");
        a.addNode(new Person(1, "Barcelona", "Maria"),"RL");
        a.addNode(new Person(2, "Barcelona", "Laura"),"RL");
        a.addNode(new Person(3, "Barcelona", "Marta"),"RR");

        a.displayTree();

    }
}
