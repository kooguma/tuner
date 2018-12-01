package top.koguma.tuner.model;

import com.laputapp.model.BaseModel;

public class Product extends BaseModel {

    public String name;
    public String imgUri;

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, String imgUri) {
        this.name = name;
        this.imgUri = imgUri;
    }
}
