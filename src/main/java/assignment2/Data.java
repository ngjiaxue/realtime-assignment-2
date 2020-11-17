package assignment2;

/**
 * This class is for object Data.
 *
 * @author Ng Jia Xue
 */
public class Data {
    private final String matric;
    private final String name;

    /**
     * This method is to store matric and name to Data class.
     *
     * @param m Matric no.
     * @param n name.
     */
    public Data(String m, String n) {
        this.matric = m;
        this.name = n;
    }

    /**
     * This method is to return matric no.
     *
     * @return Matric no.
     */
    public String getMatric() {
        return this.matric;
    }

    /**
     * This method is to return student name.
     *
     * @return Student name.
     */
    public String getName() {
        return this.name;
    }

}
