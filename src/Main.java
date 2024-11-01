import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        try {
            Main main = new Main();
            Students studentsList = main.readAllStudents("src/Files");
            main.mostrarMenu(studentsList);
        }
        catch (Exception e) {
            System.out.println("No s'ha pogut llegir els fitxers dels estudiants o mostrar el menú del main");
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
                            System.out.println("Sortint del programa");
                            saveAllStudents(studentsList);
                            break;
                        default:
                            System.out.println("Opció no vàlida. Si us plau, tria una opció entre 1 i 6.");
                    }
                } else {
                    System.out.println("Si us plau, introdueix un número vàlid.");
                    sc.next();
                }
            }

        } catch (Exception e) {
            System.out.println("Error en el mostrarmenu() del main: " + e.getMessage());
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

        ArrayList<String> a;

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


    private void showStudentFamily(Students studentsList) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introdueix el nom de l'estudiant: ");
            String nombre = scanner.nextLine();
            BinaryTree students = studentsList.getStudent(nombre);
            students.displayTree();
        }
        catch (Exception e) {
            System.out.println("No es pot mostrar la família perquè no existeix l'estudiant");
        }
    }
    private void addNewStudent(Students studentsList) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introdueix el nom l'estudiant: ");
            String nombre = scanner.nextLine();
            System.out.println("Introdueix l'origen de l'estudiant: ");
            String origen = scanner.nextLine();
            System.out.println("Introdueix l'estat civil l'estudiant: ");
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
    private void modifyStudent(Students studentsList) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introdueix el nom de l'estudiant: ");
        String nombre = scanner.nextLine();
        BinaryTree students = studentsList.getStudent(nombre);

        if (students != null) {
            System.out.println("Posa el número 1 si vols afegir: ");
            System.out.println("Posa el número 2 si vols eliminar: ");
            int estado = scanner.nextInt();
            scanner.nextLine();
            if (estado == 1) {

                System.out.println("Introdueix la posicio en l'arbre (e.g., L, R, LL, LR): ");
                String posicion = scanner.nextLine();
                posicion = posicion.toUpperCase();

                System.out.println("Introdueix el nom del familiar: ");
                String nombrefamiliarañadir = scanner.nextLine();
                System.out.println("Introdueix l'origen del familiar: ");
                String origenfamiliarañadir = scanner.nextLine();
                System.out.println("Introdueix l'estat civil del familiar: ");
                int estadofamiliarañadir = scanner.nextInt();
                Person person = new Person(estadofamiliarañadir, origenfamiliarañadir, nombrefamiliarañadir);


                try {
                    students.addNode(person, posicion);
                } catch (Exception e) {
                    System.out.println("Error al afegir una persona en una posició de l'arbre familiar");
                }

            } else {
                System.out.println("Introdueix el nom del familiar: ");
                String nombrefamiliareliminar = scanner.nextLine();

                try {
                    students.removePerson(nombrefamiliareliminar);
                } catch (Exception e) {
                    System.out.println("Error" + e.getMessage());
                }
            }
        } else System.out.println("No se s'ha trobat cap estuidant amb aquest nom: "+nombre);

    }

    private void mostrarInforme(Students studentsList){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Indica la ciutat de naixement a buscar:");
        String ciutatNaixement = scanner.nextLine();

        System.out.println("Indica la ciutat de procedència a buscar:");
        String ciutatProcedencia = scanner.nextLine();

        int totalEstudiants = 0;
        int countCiutatNaixement = 0;
        int countDescendentsProcedencia = 0;
        int countUnicoProgenitor = 0;
        int countNoCasats = 0;
        int countAmbAvis = 0;

        ArrayList<String> allStudentNames = studentsList.getAllStudentsName();

        if (allStudentNames == null) {
            System.out.println("No hay estudiants registrats.");
            return;
        }

        for (String studentName : allStudentNames) {
            BinaryTree estudiantTree = studentsList.getStudent(studentName);
            totalEstudiants++;

            if (estudiantTree.isFrom(ciutatNaixement)) {
                countCiutatNaixement++;
            }

            if (estudiantTree.isDescent(ciutatProcedencia)) {
                countDescendentsProcedencia++;
            }

            if (estudiantTree.howManyParents() == 1) {
                countUnicoProgenitor++;
            }

            if (!estudiantTree.marriedParents()) {
                countNoCasats++;
            }

            if (estudiantTree.howManyGrandParents() >= 2) {
                countAmbAvis++;
            }
        }

        System.out.println("Nombre d'alumnes totals: " + totalEstudiants);
        System.out.println("Hi ha " + countCiutatNaixement + " alumnes de " + ciutatNaixement);
        System.out.println("Hi ha " + countDescendentsProcedencia + " alumnes descendents de " + ciutatProcedencia);
        System.out.println("Hi ha " + countUnicoProgenitor + " alumnes amb un únic progenitor.");
        System.out.println("Hi ha " + countNoCasats + " alumnes amb progenitors no casats.");
        System.out.println("Hi ha " + countAmbAvis + " alumnes amb dos o més avis o àvies.");
    }





}

