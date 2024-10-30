import java.io.*;

public class BinaryTree {


    private class NodeA {
        protected NodeA right;
        protected NodeA left;
        protected Person info;

        protected NodeA(Person info) {
            this.info = info;
            this.left = null;
            this.right = null;
        }

        protected NodeA(Person info, NodeA left, NodeA right) {
            this.info = info;
            this.left = left;
            this.right = right;
        }

        private void preorderSaveRecursive(BufferedWriter buw) throws IOException {

            String linea;
            linea  = this.info.toString();

            if(linea == null){
                linea = " ";
            }

            if (left == null) {
                linea += " ;";
            }
            if (right == null) {
                linea += " ;";
            }

            buw.write(linea + "\n");

            if(this.left != null) {
                this.left.preorderSaveRecursive(buw);
            }
            if(this.right != null) {
                this.right.preorderSaveRecursive(buw);
            }
        }


        private boolean addNodeRecursive(Person unaPersona, String level) {

            if(level.length() == 1 ) {
                if (level.charAt(0) == 'L') {
                    if(this.left == null) this.left = new NodeA(unaPersona);
                    return true;
                } else if (this.right == null){
                    this.right = new NodeA(unaPersona);
                    return true;
                }
            } else {
                if (level.charAt(0) == 'L') {
                    if(this.left == null) this.left = new NodeA(null);
                    return this.left.addNodeRecursive(unaPersona, level.substring(1));
                } else {
                    if(this.right == null) this.right = new NodeA(null);
                    return this.right.addNodeRecursive(unaPersona, level.substring(1));
                }
            }
            return false;
        }

        private void displayTreeRecursive(int level) {

            if(level == 1) System.out.println("Arbre binari estructurat:");

            String tabs = "";

            for(int i = 0;i<level;i++)
                tabs = tabs + "      ";

            if(this.info != null)
                System.out.println(tabs + this.info.getName());
            else
                System.out.println(tabs + "*dead");


            if(this.left != null) {
                this.left.displayTreeRecursive(level+1);
            }

            if(this.right != null) {
                this.right.displayTreeRecursive(level+1);
            }

        }

        private void removePersonRecursive(String name) throws Exception {

            //Si volem eliminar a un pare i aquest té almenys un pare o mare, no s'elimina la referència completa, només la Persona
            if(this.left != null && this.left.info != null && this.left.info.getName().equals(name)) {
                if(this.left.left != null || this.left.right != null) {
                    this.left.info = null;
                } else {
                    this.left = null;
                }
                // En cualsevol cas, si s'elimina un pare o avi i la seva parella encara n'és viva,
                // la parella estat pasarà a estar Viudo/Viuda si no està divorciat/ada
                if(this.right != null && this.right.info != null && this.right.info.getMaritalStatus()!=Person.DIVORCED)
                    this.right.info.setMaritalStatus(Person.WIDOWED);

            } else if(this.right != null && this.right.info != null && this.right.info.getName().equals(name)) {
                if(this.right.left != null || this.right.right != null) {
                    this.right.info = null;
                } else {
                    this.right = null;
                }
                //El mateix per la dreta
                if(this.left != null && this.left.info != null && this.left.info.getMaritalStatus() != Person.DIVORCED)
                    this.left.info.setMaritalStatus(Person.WIDOWED);

            //Cas si n'és l'àvi o l'àvia
            } else {
                if (this.left != null)
                    this.left.removePersonRecursive(name);
                if(this.right != null)
                    this.right.removePersonRecursive(name);

            }

        }

        private boolean isDescentFromRecursive(String place) {
            //preodre
            if(this.info.getPlaceOfOrigin().equals(place)) {
                return true;
            }
            if(this.left != null) {
                return this.left.isDescentFromRecursive(place);
            }
            if(this.right != null) {
                return this.right.isDescentFromRecursive(place);
            }
            return false;
        }


        private int countNodesRecursive() {
            int count = 0;

            if(this.info != null){
                count = 1;
            }
            if (this.left != null) {
                count += this.left.countNodesRecursive();
            }
            if (this.right != null) {
                count += this.right.countNodesRecursive();
            }
            return count;
        }

    }



    private NodeA root;

    public BinaryTree(){
        root = null;
    }
    public BinaryTree(String filename) throws Exception {
        BufferedReader bur = new BufferedReader(new FileReader("src/Files/"+filename));
        this.root = preorderLoad(bur);
    }

    public String getName() {
        if (this.root == null || this.root.info == null) {
            return null;
        }
        return this.root.info.getName();
    }

    private NodeA preorderLoad(BufferedReader bur) throws Exception {

        String line = bur.readLine();
        if (line == null) {
            return null;
        }

        NodeA nod = null;
        int cont = countChar(';', line);
        line = esborrapunticoma(line);

        if (line.equals(" ")) {
            nod = new NodeA(null);
        } else {
            line = line.trim();
            Person person = new Person(line);
            nod = new NodeA(person);
        }

        if (cont == 0) {
            nod.left = preorderLoad(bur);
            nod.right = preorderLoad(bur);
        }
        else if (cont == 1) {
            nod.left = preorderLoad(bur);
            nod.right = null;
        } else if (cont == 2) {
            nod.left = null;
            nod.right = null;
        } else {
            System.out.println(line);
            throw new Exception("Format de línia no vàlid");
        }
        return nod;
    }

    private int countChar(char c, String on) {
        int a = 0;
        if(on != null) {
            for(int i = 0;i<on.length();i++) {
                if(on.charAt(i) == c)
                    a++;
            }
        }
        return a;

    }

    private String esborrapunticoma(String e) {
        return e.replace(";","");
    }

    public boolean addNode(Person unaPersona, String level) {

        if(this.root == null && level.isEmpty()) {
            this.root = new NodeA( unaPersona,null, null);
            return true;
        }

        if( !level.isEmpty() && level.length() < 3 && ( level.charAt(0) == 'L' || level.charAt(0) == 'R' )) {
            if(this.root != null)
                return this.root.addNodeRecursive(unaPersona,level);
        }
        return false;
    }

    public void displayTree() throws Exception {
        if(this.root != null) {
            this.root.displayTreeRecursive(1);
        } else
            throw new Exception("No hi ha estudiant per fer print!");
    }

    public void preorderSave() throws Exception {
        if(root == null) throw new Exception("Aquest arbre esta buit");
        FileWriter f = new FileWriter("src/Files/" + root.info.getName() + ".txt");
        BufferedWriter bur = new BufferedWriter(f);
        try{
            root.preorderSaveRecursive(bur);
            bur.close();
        }
        catch (Exception e){
            throw new Exception("No s'ha pogut fer el preorderSave() de la classe BinaryTree");
        }
    }

    public void removePerson(String name) throws Exception {
        if(!(this.root.info.getName().equals(name))) {

            this.root.removePersonRecursive(name);

        } else
            throw new Exception("No es pot eliminar l'estudiant amb el nom : "+name);
    }

    public boolean isFrom(String place) {
        return root != null && this.root.info.getPlaceOfOrigin().equals(place);
    }

    public boolean isDescent(String place) {
        if(root == null){
            return false;
        }
        return root.isDescentFromRecursive(place);
    }
    public int howManyParents() {
        int count = 0;
        if(root == null) {
            return 0;
        }
        else{
            if(root.left != null){
                if(root.left.info != null)
                    count += 1;
            }
            if(root.right != null){
                if(root.right.info != null)
                    count += 1;
            }
        }
        return count;
    }

    public int howManyGrandParents() {
        if(root == null){
            return 0;
        }
        return root.countNodesRecursive()-howManyParents()-1;
    }

    public boolean marriedParents() {
        //Aquest mètode comprova si ambdós
        // progenitors de l'estudiant (tant l'esquerra com la dreta) estan casats.
        if(root == null || (this.root.left == null || this.root.right == null) || (this.root.left.info == null || this.root.right.info == null)) {
            return false;
        }

        return root.left.info.getMaritalStatus() == Person.MARRIED && root.right.info.getMaritalStatus() == Person.MARRIED;
    }
}


