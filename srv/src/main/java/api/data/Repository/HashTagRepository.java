package api.data.Repository;

import api.model.HashTag;
import com.mongodb.DB;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import restx.factory.Component;

import javax.inject.Named;
import java.util.Date;

@Component
public class HashTagRepository {

    private DB hazittDb;
    private Jongo jongo;
    private MongoCollection hashtags;

    public HashTagRepository(@Named("hazittDb") DB hazittDb) {
        this.hazittDb = hazittDb;
        this.jongo = new Jongo(this.hazittDb);
        hashtags = this.jongo.getCollection("hashtags");
    }

    public HashTag create(HashTag hashTag) {
        Date date = new Date();
        hashTag.setCreated(date);
        hashTag.setLastUpdated(date);

        hashtags.update("{ tag : # }", hashTag.getTag()).upsert().with(hashTag);
        hashTag = hashtags.findOne("{ tag : # }", hashTag.getTag()).as(HashTag.class);

        return hashTag;
    }
}