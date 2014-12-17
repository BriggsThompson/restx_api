package api.rest;

import api.constants.Reference;
import api.constants.Roles;
import api.data.Repository.HashTagRepository;
import api.data.Repository.ProductRepository;
import api.data.s3.ImageUploader;
import api.model.HashTag;
import api.model.Product;
import api.model.User;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.RestxRequest;
import restx.WebException;
import restx.annotations.*;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.RolesAllowed;

import java.io.IOException;
import java.util.List;

@RestxResource
@Component
public class ProductResource {

    private final ProductRepository productRepository;
    private final HashTagRepository hashTagRepository;
    private final ImageUploader imageUploader;

    private final static Logger logger = LoggerFactory.getLogger(ProductResource.class);

    public ProductResource(ProductRepository productRepository,
                           HashTagRepository hashTagRepository,
                           ImageUploader imageUploader) {
        this.productRepository = productRepository;
        this.hashTagRepository = hashTagRepository;
        this.imageUploader = imageUploader;
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
     * "test","test2"
     * ]
     * }
     *
     * http://www.labouisse.com/how-to/2014/06/03/handling-videos-with-restx/
     * @param product
     * @return
     */
    @RolesAllowed(Roles.BUYER_SELLER)
    @POST("/products/upload")
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
            for(String tag : product.getHashTagList()) {
                HashTag hashTag = new HashTag()
                        .setTag(tag.replaceAll("[^a-zA-Z0-9]", "").toLowerCase())
                        .setUserKey(user.get().getKey());

                hashTagRepository.create(hashTag);
            }

            return Optional.of(productRepository.create(product).getDisplay());
        }

        throw new WebException(HttpStatus.FAILED_DEPENDENCY,
                String.format("{ Not enough info in Product: %.}", product.toString()));
    }


    private void validateUpload(List<FileItem> items) {
        if(items == null || items.size() ==0 || items.size() > 5 ) {
            throw new WebException(HttpStatus.UNPROCESSABLE_ENTITY, "{ File upload failed: must have between one and five files.}");
        }

        for (FileItem item : items) {
            if (!Reference.validContentTypes.containsKey(item.getContentType()))
                throw new WebException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("{ File content type is invalid. Only .png and .jpg allowed. Submitted %}", item.getContentType()));
        }
    }


    @RolesAllowed(Roles.BUYER_SELLER)
    @PUT("/products/upload/{key}")
    public Optional<Product.Display> photo(@Param(value="request", kind= Param.Kind.CONTEXT) RestxRequest request, String key) {
        Optional<User> user = UserResourceHelper.authorizationCheck();
        Optional<Product> product = productRepository.exists(key);
        if(product.isPresent()){
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            List<FileItem> items = null;
            try {
                items = upload.parseRequest(new RestXRequestContext(request));
                validateUpload(items);
                try {
                    String result = this.imageUploader.fileUploader(items, product.get());
                } catch (IOException e) {
                    throw new WebException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
                }

            } catch (FileUploadException e) {
                throw new WebException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
            } finally {
                if (null != items) {
                    for (FileItem item : items) {
                        item.delete();
                    }
                }
            }
        }
        return Optional.of(product.get().getDisplay());
    }

    @RolesAllowed(Roles.BUYER_SELLER)
    @GET("/products/mine")
    public Optional<List<Product.Display>> mine() {
        Optional<User> user = UserResourceHelper.authorizationCheck();
        List<Product.Display> products = productRepository.userProductsDisplay(user.get().getKey());
        return Optional.of(products);
    }

    @RolesAllowed(Roles.BUYER_SELLER)
    @GET("/products/all")
    public Optional<List<Product.Display>> all() {
        Optional<User> user = UserResourceHelper.authorizationCheck();
        List<Product.Display> products = productRepository.all();
        return Optional.of(products);
    }
}
