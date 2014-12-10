package api.rest;

import api.constants.Roles;
import api.data.Repository.ProductRepository;
import api.model.Product;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import restx.WebException;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.RolesAllowed;

@RestxResource
@Component
public class PostProductResource {

    private final ProductRepository productRepository;

    public PostProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RolesAllowed(Roles.BUYER_SELLER)
    @POST("/product/post")
    public Optional<Product> post(Product product) {
        if(!Strings.isNullOrEmpty(product.getTitle()) &&
           !Strings.isNullOrEmpty(product.getDescription()) &&
           product.getHashTagList() != null &&
           !product.getHashTagList().isEmpty()) {
            return Optional.of(productRepository.createProduct(product));
        }

        throw new WebException(HttpStatus.FAILED_DEPENDENCY,
                String.format("{ Not enough info in Product: %.}", product.toString()));
    }

}
