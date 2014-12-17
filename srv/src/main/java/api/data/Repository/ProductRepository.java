package api.data.Repository;

import api.model.Product;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.mongodb.DB;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import restx.factory.Component;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<Product> userProducts(String userKey) {
        Iterable<Product> productList = products.find("{userKey:#", new ObjectId(userKey)).as(Product.class);
        return Lists.newArrayList(productList);
    }

    public List<Product.Display> userProductsDisplay(String userKey) {
        Iterable<Product> productList = products.find("{ userKey : # }", new ObjectId(userKey)).as(Product.class);
        List<Product.Display> productDisplay = new ArrayList<>();
        for (Product product : productList) {
            productDisplay.add(product.getDisplay());
        }
        return productDisplay;
    }

    public List<Product.Display> all() {
        Iterable<Product> productList = products.find().limit(25).as(Product.class);
        List<Product.Display> productDisplay = new ArrayList<>();
        for (Product product : productList) {
            productDisplay.add(product.getDisplay());
        }
        return productDisplay;
    }
}
