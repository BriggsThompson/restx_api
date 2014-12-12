package api.rest;

import api.constants.Roles;
import api.data.Repository.HashTagRepository;
import api.data.Repository.ProductRepository;
import api.model.HashTag;
import api.model.Product;
import api.model.User;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import restx.WebException;
import restx.annotations.PUT;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.RolesAllowed;

@RestxResource
@Component
public class PostProductResource {

    private final ProductRepository productRepository;
    private final HashTagRepository hashTagRepository;

    public PostProductResource(ProductRepository productRepository, HashTagRepository hashTagRepository) {
        this.productRepository = productRepository;
        this.hashTagRepository = hashTagRepository;
    }

    /***
     *  Example Post:  {
     * "title":"Test Title",
     * "description": "Test Description",
     * "rentalCost": {
     * "costInterval": {
     * "DAY": "10"
     * },
     * "totalCost": 100
     * },
     * "hashTagList": [
     * {"tag":"test"}
     * ]
     * }
     *
     * http://www.labouisse.com/how-to/2014/06/03/handling-videos-with-restx/
     * @param product
     * @return
     */
    @RolesAllowed(Roles.BUYER_SELLER)
    @PUT("/products/upload")
    public Optional<Product.Display> post(Product product) {
        Optional<User> user = UserResourceHelper.authorizationCheck();

        if(!Strings.isNullOrEmpty(product.getTitle()) &&
                !Strings.isNullOrEmpty(product.getDescription()) &&
                product.getHashTagList() != null &&
                !product.getHashTagList().isEmpty() &&
                product.getRentalCost() != null &&
                product.getRentalCost().getTotalCost() != null &&
                product.getRentalCost().getCostInterval() != null &&
                product.getRentalCost().getCostInterval().size() > 0
                ) {
            product.setUserKey(user.get().getKey());

            for(HashTag hashTag : product.getHashTagList()) {
                hashTag.setUserKey(user.get().getKey());
                hashTagRepository.create(hashTag);
            }

            return Optional.of(productRepository.create(product).getDisplay());
        }

        throw new WebException(HttpStatus.FAILED_DEPENDENCY,
                String.format("{ Not enough info in Product: %.}", product.toString()));
    }

}
