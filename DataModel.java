package th.ac.kmutnb.project;


public class DataModel {

    private int id;
    private String brand;
    private String model;
    private String img;

//    public DataModel(int id, String title, String description, String img) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.img = img;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
