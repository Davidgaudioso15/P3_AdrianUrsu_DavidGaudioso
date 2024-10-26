import java.io.*;

public class BinaryTree {


    private class NodeA {
        protected NodeA right;
        protected NodeA left;
        protected Person info;

        protected NodeA(Person info) {
            this.info = info;
        }

        protected NodeA(Person info, NodeA left, NodeA right) {
            this.info = info;
            this.left = left;
            this.right = right;
        }

        private void preorderSaveRecursive(BufferedWriter buw) {

        }

        private boolean addNodeRecursive(Person unaPersona, String level) {
            return false;
        }

        private void displayTreeRecursive(int level) {

        }

        private void removePersonRecursive(String name) {

        }

        private boolean isDescentFromRecursive(String place) {
            return false;
        }

        private int countNodesRecursive() {
            return 0;
        }
        private void preorderLoad(BufferedReader bur) throws IOException {
            //primeor raiz , despues izquierda y por ultimo derecha
            String line = bur.readLine(); //lee los datos del archivo
            if (line == null) {
                return;
            }
            this.info = new Person(line);
            this.left.preorderLoad(bur);
            this.right.preorderLoad(bur);
        }

    }

    private NodeA root;

    public BinaryTree(){
        root = null;
    }
    public BinaryTree(String filename) throws IOException { // hay que llmar al PreorderLoad
        BufferedReader bur = new BufferedReader(new FileReader(filename));
        preorderLoad(bur);
    }
    private void preorderLoad(BufferedReader bur){
        // hay que llamar al node aqui para buscar por dentro del arbol
        if(root == null) {return;}
        return root.preorderLoad(bur);
    }
    public String getName() {
        if (this.root == null || this.root.info == null) {
            return null; // o un valor predeterminado, como ""
        }
        return this.root.info.getName();
    }
}
