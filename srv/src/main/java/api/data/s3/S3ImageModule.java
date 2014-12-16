package api.data.s3;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import restx.config.Settings;
import restx.config.SettingsKey;
import restx.factory.Module;
import restx.factory.Provides;

@Module
public class S3ImageModule {

    @Settings
    public static interface S3ImageConfig {

        @SettingsKey(key = "hazitt.s3config.accesskey")
        String hazzitS3AccessKey();

        @SettingsKey(key = "hazitt.s3config.secretkey")
        String hazzitS3SecretKey();

        @SettingsKey(key = "hazitt.s3config.buckename")
        String hazzitS3BucketName();
    }

    @Provides
    public AmazonS3 hazittImageS3(final S3ImageConfig s3ImageConfig) {
        return new AmazonS3Client(new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return s3ImageConfig.hazzitS3AccessKey();
            }

            @Override
            public String getAWSSecretKey() {
                return s3ImageConfig.hazzitS3SecretKey();
            }
        });
    }
}
