package sample.model;

public class Specialty {
    private String title;
    private String facult;
    private int id;

    public Specialty(String title, String facult) {
        this.title = title;
        this.facult = facult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFacult() {
        return facult;
    }

    public void setFacult(String facult) {
        this.facult = facult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
