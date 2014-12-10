package api.data.Repository;

import api.model.Product;
import com.mongodb.DB;
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

    public Product createProduct(Product product) {
        Date date = new Date();
        product.setCreated(date);
        product.setLastUpdated(date);

        products.insert(product);

        return product;
    }
}
