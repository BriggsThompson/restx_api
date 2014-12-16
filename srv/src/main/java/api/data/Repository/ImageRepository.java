package api.data.Repository;


import api.model.Image;
import com.mongodb.DB;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import restx.factory.Component;

import javax.inject.Named;
import java.util.Date;

@Component
public class ImageRepository {

    private DB hazittDb;
    private Jongo jongo;
    private MongoCollection images;

    public ImageRepository(@Named("hazittDb") DB hazittDb) {
        this.hazittDb = hazittDb;
        this.jongo = new Jongo(this.hazittDb);
        images = this.jongo.getCollection("images");
    }

    public Image create(Image image) {
        Date date = new Date();
        image.setCreated(date);
        image.setLastUpdated(date);

        images.insert(image);

        return image;
    }

    public void delete(Image image) {

    }
}