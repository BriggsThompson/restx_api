package api;

import api.data.Repository.UserCredentialsRepository;
import com.google.common.base.Charsets;
import restx.config.ConfigLoader;
import restx.config.ConfigSupplier;
import restx.factory.Module;
import restx.factory.Provides;
import restx.security.*;

import javax.inject.Named;

@Module
public class AppModule {

    @Provides
    public SignatureKey signatureKey() {
         return new SignatureKey("api -1393545414026345943 bb8c21bd-6be1-40f6-9831-2ac7594adbda api".getBytes(Charsets.UTF_8));
    }

    @Provides
    @Named("restx.admin.password")
    public String restxAdminPassword() {
        return "admin";
    }

    @Provides
    public ConfigSupplier appConfigSupplier(ConfigLoader configLoader) {
        return configLoader.fromResource("api/settings");
    }

    @Provides
    public CredentialsStrategy credentialsStrategy() {
        return new BCryptCredentialsStrategy();
    }

    @Provides
    public BasicPrincipalAuthenticator basicPrincipalAuthenticator(
            UserCredentialsRepository userCredentialsRepository, SecuritySettings securitySettings,
            CredentialsStrategy credentialsStrategy,
            @Named("restx.admin.passwordHash") String adminPasswordHash) {

        return new StdBasicPrincipalAuthenticator(
                new StdUserService<>(userCredentialsRepository, credentialsStrategy, adminPasswordHash), securitySettings);
    }
}
