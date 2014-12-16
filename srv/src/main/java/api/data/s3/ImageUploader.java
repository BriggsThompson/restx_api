package api.data.s3;

import api.constants.Reference;
import api.data.Repository.ImageRepository;
import api.data.Repository.ProductRepository;
import api.model.Image;
import api.model.Product;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.factory.Component;

import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ImageUploader {

    private AmazonS3 hazittImageS3;
    private ImageRepository imageRepository;
    private ProductRepository productRepository;
    private String bucketName;
    private final static Logger logger = LoggerFactory.getLogger(ImageUploader.class);

    public ImageUploader(@Named("hazittImageS3") AmazonS3 hazittImageS3,
                         S3ImageModule.S3ImageConfig s3ImageConfig,
                         ImageRepository imageRepository,
                         ProductRepository productRepository) {

        this.hazittImageS3 = hazittImageS3;
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.bucketName = s3ImageConfig.hazzitS3BucketName();
    }

    public String fileUploader(List<FileItem> fileData, Product product) throws IOException {
        StringBuilder sb = new StringBuilder();
        Image image = null;
        try {
            for (FileItem fileItem : fileData) {
                S3Object s3Object = new S3Object();

                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(fileItem.getContentType());
                objectMetadata.setContentLength(fileItem.getSize());
                objectMetadata.setHeader("filename", fileItem.getName());

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileItem.get());
                s3Object.setObjectContent(byteArrayInputStream);


                image = new Image()
                        .setUserKey(product.getUserKey())
                        .setProductKey(product.getKey())
                        .setContentLength(fileItem.getSize())
                        .setContentType(Reference.validContentTypes.get(fileItem.getContentType()))
                        .setOriginalFileName(fileItem.getName());
                image = imageRepository.create(image);

                this.hazittImageS3.putObject(new PutObjectRequest(bucketName, image.getFileName(), byteArrayInputStream, objectMetadata));
                s3Object.close();

                product.getImageList().add(image.getFileName());
                this.productRepository.save(product);
            }
        } catch (AmazonServiceException ase) {
            StringBuilder errorBuilder = new StringBuilder("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was ")
                    .append("rejected with an error response for some reason.")
                    .append(" Message:    " + ase.getMessage())
                    .append("HTTP Status Code: " + ase.getStatusCode())
                    .append("AWS Error Code:   " + ase.getErrorCode())
                    .append("Error Type:       " + ase.getErrorType())
                    .append("Request ID:       " + ase.getRequestId());
            logger.error(errorBuilder.toString(), ase);

            sb.append(ase.getMessage());
            cleanInsertedImage(image, product);
        } catch (AmazonClientException ace) {
            StringBuilder errorBuilder = new StringBuilder("Caught an AmazonClientException, which means the client encountered an internal error while ")
                    .append("trying to communicate with S3, such as not being able to access the network.");

            sb.append(ace.getMessage());
            cleanInsertedImage(image, product);
        }catch (Exception e) {
            sb.append(e.getMessage());
            cleanInsertedImage(image, product);
        }

        return sb.toString();
    }

    private void cleanInsertedImage(Image image, Product product) {
        if (image != null && StringUtils.isNullOrEmpty(image.getKey()) ) {
            this.imageRepository.delete(image);
            product.getImageList().remove(image.getFileName());
            this.productRepository.save(product);
            this.hazittImageS3.deleteObject(new DeleteObjectRequest(this.bucketName, image.getFileName()));
        }
    }
}