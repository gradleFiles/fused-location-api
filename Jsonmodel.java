package proveb.gk.com.sqlite.modelclass;

/**
 * Created by Nehru on 02-07-2016.
 */
public class Jsonmodel {
    String doc_id="",fullname="",image_url="";

    public Jsonmodel(String doc_id, String fullname, String image_url) {
        this.doc_id = doc_id;
        this.fullname = fullname;
        this.image_url = image_url;
    }
    public Jsonmodel() {
        this.doc_id = new String();
        this.fullname = new String();
        this.image_url = new String();
    }

    public String getDoc_id() {
        return doc_id;
    }

    public Jsonmodel setDoc_id(String doc_id) {
        this.doc_id = doc_id;
        return this;
    }

    public String getFullname() {
        return fullname;
    }

    public Jsonmodel setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getImage_url() {
        return image_url;
    }

    public Jsonmodel setImage_url(String image_url) {
        this.image_url = image_url;
        return this;
    }
}
