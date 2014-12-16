package api.data.Repository;

import api.model.Product;
import com.google.common.base.Optional;
import com.mongodb.DB;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import restx.factory.Component;

import javax.inject.Named;
import java.util.Date;

@Component
public class ProductRepository {

    private DB hazittDb;
    private Jongo jongo;
    private MongoCollection products;

    public ProductRepository(@Named("hazittDb") DB hazittDb) {
        this.hazittDb = hazittDb;
        this.jongo = new Jongo(this.hazittDb);
        products = this.jongo.getCollection("products");
    }

    public Product create(Product product) {
        Date date = new Date();
        product.setCreated(date);
        product.setLastUpdated(date);

        products.insert(product);

        return product;
    }

    public Optional<Product> exists(String key) {
        Product product = products.findOne(new ObjectId(key)).as(Product.class);
        if (product != null)
            return Optional.fromNullable(product);
        return Optional.absent();
    }

    public Product save(Product product) {
        Date date = new Date();
        product.setLastUpdated(date);

        products.save(product);

        return product;
    }
}
