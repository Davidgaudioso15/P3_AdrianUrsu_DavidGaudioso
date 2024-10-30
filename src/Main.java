import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
       /*
        //System.out.println("Adrian".compareTo("David"));

        BinaryTree a = new BinaryTree();


        a.addNode(new Person(1, "Barcelona", "Adrian"), "");
        a.addNode(new Person(3, "Barcelona", "David"), "L");
        a.addNode(new Person(3, "Barcelona", "Rosa"), "R");
        a.addNode(null, "R");
        a.addNode(new Person(4, "Barcelona", "Pau"), "LL");
        // a.addNode(new Person(1, "Barcelona", "Maria"),"RL");
        // a.addNode(new Person(2, "Barcelona", "Laura"),"RL"); //addNode no permet sobreescriure avis
        // a.addNode(new Person(3, "Barcelona", "Marta"),"RR");
        // a.addNode(new Person(3, "Barcelona", "Aurelio"),"LR");

        a.displayTree();
        a.preorderSave();

        BinaryTree b = new BinaryTree("src/Files/Adrian.txt");
        */
        try {
            Main main = new Main();
            Students studentsList = main.readAllStudents("src/Files");
            main.mostrarMenu(studentsList);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    private void mostrarMenu(Students studentsList) {
        Scanner sc = new Scanner(System.in);
        int opc = 0;

        try {
            while (opc != 6) {
                System.out.println("Escull una opció:");
                System.out.println("1. Mostrar llistat d'estudiants");
                System.out.println("2. Mostrar família d'un estudiant");
                System.out.println("3. Afegir un estudiant");
                System.out.println("4. Modificar un estudiant");
                System.out.println("5. Mostrar el informe");
                System.out.println("6. Guardar i Sortir");


                if (sc.hasNextInt()) {
                    opc = sc.nextInt();

                    switch (opc) {
                        case 1:
                            displayAllStudentsNames(studentsList);
                            break;
                        case 2:
                            showStudentFamily(studentsList);
                            break;
                        case 3:
                            addNewStudent(studentsList);
                            break;
                        case 4:
                            modifyStudent(studentsList);
                            break;
                        case 5:
                            mostrarInforme(studentsList);
                            break;
                        case 6:
                            System.out.println("Guardando el archivo y saliendo");
                            saveAllStudents(studentsList);
                        default:
                            System.out.println("Opció no vàlida. Si us plau, tria una opció entre 1 i 3.");
                    }
                } else {
                    System.out.println("Si us plau, introdueix un número vàlid.");
                    sc.next();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Students readAllStudents(String folderPath) throws Exception {

        Students s = new Students();

        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    s.addStudent(new BinaryTree(listOfFile.getName()));
                }

            }
        }
       return s;
    }

    private void saveAllStudents(Students studentsList) throws Exception {

        ArrayList<String> a = null;

        a = studentsList.getAllStudentsName();

        for (String s : a) {
            BinaryTree b = studentsList.getStudent(s);
            if(b!=null)
                b.preorderSave();
            else
                System.out.println("No s'ha pogut guardar la llista d'estudiants perquè està buida");
        }


    }

    private void displayAllStudentsNames(Students studentsList) {
        ArrayList<String> a = studentsList.getAllStudentsName();
        for (String s : a) {
            System.out.println(s);
        }

    }


    private void showStudentFamily(Students studentsList) throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introdúceme el nombre del estudiante: ");
            String nombre = scanner.nextLine();
            BinaryTree students = studentsList.getStudent(nombre);
            students.displayTree();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void addNewStudent(Students studentsList) throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introdúceme el nombre del estudiante: ");
            String nombre = scanner.nextLine();
            System.out.println("Introdúceme el origen del estudiante: ");
            String origen = scanner.nextLine();
            System.out.println("Introdúceme el estado civil del estudiante: ");
            int estado = scanner.nextInt();
            Person person = new Person(estado, origen,nombre);
            BinaryTree arbol = new BinaryTree();
            arbol.addNode(person, "");
            studentsList.addStudent(arbol);
        }
        catch (Exception e) {
            System.out.println("Error"+e.getMessage());
        }
    }
    private void modifyStudent(Students studentsList) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introdúceme el nombre del estudiante: ");
        String nombre = scanner.nextLine();
        BinaryTree students = studentsList.getStudent(nombre);

        if (students != null) {
            System.out.println("Pon 1 si queires añadir: ");
            System.out.println("Pon 2 si quieres eliminar: ");
            int estado = scanner.nextInt();
            scanner.nextLine();
            if (estado == 1) {
                System.out.println("Introdúceme el nombre del familiar: ");
                String nombrefamiliarañadir = scanner.nextLine();
                System.out.println("Introdúceme el origen del familiar: ");
                String origenfamiliarañadir = scanner.nextLine();
                System.out.println("Introdúceme el estado civil del familiar: ");
                int estadofamiliarañadir = scanner.nextInt();
                Person person = new Person(estadofamiliarañadir, origenfamiliarañadir, nombrefamiliarañadir);

                System.out.println("Introduce la posición en el árbol (e.g., L, R, LL, LR): ");
                String posicion = scanner.nextLine();
                posicion = posicion.toUpperCase();
                try {
                    students.addNode(person, posicion);
                } catch (Exception e) {
                    System.out.println("Error ----- " + e.getMessage());
                }

            } else {
                System.out.println("Introdúceme el nombre del familiar: ");
                String nombrefamiliareliminar = scanner.nextLine();

                try {
                    students.removePerson(nombrefamiliareliminar);
                } catch (Exception e) {
                    System.out.println("Error" + e.getMessage());
                }
            }
        } else System.out.println("No se encontró ningún estudiante con ese nombre.");

    }

    private void mostrarInforme(Students studentsList){

        /*

        Tria una opció: 5
        Indica la ciutat de naixement a buscar:
        Barcelona
        Indica la ciutat de procedència a buscar:
        Girona
        Nombre d'alumnes totals: 6
        Hi ha 2 alumnes de Barcelona
        Hi ha 3 alumnes descendents de Girona
        Hi ha 2 alumnes amb un únic progenitor.
        Hi ha 3 alumnes amb progenitors no casats.
        Hi ha 5 alumnes amb dos o més avis o àvies.

         */

        /*
        ArrayList<String> a = null;

        a = studentsList.getAllStudentsName();

        for (String s : a) {
            BinaryTree b = studentsList.getStudent(s);
        }

         */

    }





}

