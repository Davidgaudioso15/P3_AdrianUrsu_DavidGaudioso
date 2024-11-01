import java.util.ArrayList;

public class Students {

    private class Node {
        protected BinaryTree info;
        protected Node seguent;

        public Node(BinaryTree info, Node seguent) {
            this.info = info;
            this.seguent = seguent;
        }
    }

    private Node first;

    public Students() {
        first = null;
    }

    public void addStudent(BinaryTree nouEstudiant) throws Exception {
        int comparador;
        if (first == null) {
            first = new Node(nouEstudiant, null);
        } else {
            comparador = first.info.getName().compareToIgnoreCase(nouEstudiant.getName());
            if (comparador == 0) {
                throw new Exception("ERR 1 - Ja hi ha un alumne amb el mateix nom que : " + first.info.getName());
            } else if (comparador > 0) { //Si el primer nom de la seqüència enllaçada és més gran que el que intento afegir
                first = new Node(nouEstudiant, first);
            } else {
                Node aux = first;
                boolean seg = true;
                while (aux.seguent != null && seg) {
                    comparador = aux.seguent.info.getName().compareToIgnoreCase(nouEstudiant.getName());
                    if (comparador == 0) {
                        throw new Exception("ERR 2 - Ja hi ha un alumne amb el mateix nom que : " + aux.seguent.info.getName());
                    } else if (comparador < 0) { // Si el nom seguent és més petit que el que li paso tindrà que avançar un lloc
                        aux = aux.seguent;
                    } else { // Si el nom seguent és més gran l'afegeixo
                        aux.seguent = new Node(nouEstudiant, aux.seguent);
                        seg = false; //No segueixo afegint
                    }
                }
                aux.seguent = new Node(nouEstudiant, null); // Si es el més gran de tots
            }

        }
        System.out.println("Alumne carregat des del fitxer: "+nouEstudiant.getName()+".txt");
        nouEstudiant.displayTree();

    }

    public void removeStudent(String name) {
        if (first == null) {
            return;
        }
        Node aux = first;
        boolean trobat = false;
        while (aux.seguent != null && !trobat) { // Si el siguiente es null o lo ha encontrado y borrado
            if (aux.seguent.info.getName().equals(name)) {
                aux.seguent = aux.seguent.seguent;
                trobat = true;
            } else { // Si no la encontrado
                aux = aux.seguent;
            }
            //Tratamos el primero element al final al no tener cabecera.
            if (first.info.getName().equals(name))
                first = first.seguent;
        }
    }

    public BinaryTree getStudent(String name){
        if(first == null){
            return null;
        }
        Node aux = first;
        while (aux != null){
            if(aux.info.getName().equals(name)){
                return aux.info;
            }
            aux = aux.seguent;
        }
        return null;
    }

    public ArrayList<String> getAllStudentsName(){
        ArrayList<String> list = new ArrayList<>();
        if(first == null){
            return null;
        }
        Node aux = first;
        while (aux != null){
            list.add(aux.info.getName());
            aux = aux.seguent;
        }
        return list;
    }
}
