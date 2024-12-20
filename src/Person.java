public class Person {

    public static final int WIDOWED = 1;
    public static final int DIVORCED = 2;
    public static final int MARRIED = 3;
    public static final int SINGLE = 4;

    private int maritalStatus;
    private String placeOfOrigin;
    private String name;

    public Person(int maritalStatus, String placeOfOrigin, String name) throws IllegalArgumentException {

        if(maritalStatus > 0 && maritalStatus < 5 && placeOfOrigin != null && name !=null) {
            this.maritalStatus = maritalStatus;
            this.placeOfOrigin = placeOfOrigin;
            this.name = name;
        }
        else throw new IllegalArgumentException("ERROR al crear una persona amb: "+maritalStatus+", "+placeOfOrigin+", "+name);
    }

    public Person(String formattedString) throws IllegalArgumentException {

        if(        !formattedString.contains("Name: ")
                || !formattedString.contains("place of Origin: ")
                || !formattedString.contains("marital status: ")) {
            throw new IllegalArgumentException("ERROR al crear una Persona a partir del string formatejat");
        }

        String[] parts = formattedString.split(", ");
        this.name = parts[0].split(": ")[1];
        this.placeOfOrigin = parts[1].split(": ")[1];
        String status = parts[2].split(": ")[1];

        switch (status) {
            case "Married" -> this.maritalStatus = MARRIED;
            case "Divorced" -> this.maritalStatus = DIVORCED;
            case "Single" -> this.maritalStatus = SINGLE;
            default -> this.maritalStatus = WIDOWED;
        }
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

    private String getMartialStatusString(int status) {
        if(status > 0 && status < 5) {
            return switch (status) {
                case Person.WIDOWED -> "Widowed";
                case Person.DIVORCED -> "Divorced";
                case Person.MARRIED -> "Married";
                default -> "Single";
            };
        }
        return null;
    }

    public int getMaritalStatus() {
        return this.maritalStatus;
    }

    public String getPlaceOfOrigin() {
        return this.placeOfOrigin;
    }

    public String getName() {
        return this.name;
    }

    protected void setMaritalStatus(int maritalStatuss) throws Exception {
        if(maritalStatuss > 0 && maritalStatuss < 5) {
            this.maritalStatus = maritalStatuss;
        } else
            throw new Exception("No es pot setejar l'estat civil: "+maritalStatuss);
    }

    @Override
    public String toString() {
        return "Name: "+this.getName()+
                ", place of Origin: "+this.getPlaceOfOrigin()+
                ", marital status: "+this.getMartialStatusString(this.getMaritalStatus());
    }
}
