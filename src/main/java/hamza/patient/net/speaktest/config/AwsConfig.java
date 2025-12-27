package hamza.patient.net.speaktest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.transcribe.TranscribeClient;

import java.net.URI;

@Configuration
public class AwsConfig {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.endpoint:}")
    private String s3Endpoint;

    @Value("${aws.transcribe.endpoint:}")
    private String transcribeEndpoint;

    @Value("${aws.cognito.endpoint:}")
    private String cognitoEndpoint;

    @Bean
    public S3Client s3Client() {
        var builder = S3Client.builder()
                .region(Region.of(region));

        if (s3Endpoint != null && !s3Endpoint.isEmpty()) {
            builder.endpointOverride(URI.create(s3Endpoint));
            builder.credentialsProvider(localStackCredentials());
        }

        return builder.build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        var builder = S3Presigner.builder()
                .region(Region.of(region));

        if (s3Endpoint != null && !s3Endpoint.isEmpty()) {
            builder.endpointOverride(URI.create(s3Endpoint));
            builder.credentialsProvider(localStackCredentials());
        }

        return builder.build();
    }

    @Bean
    public TranscribeClient transcribeClient() {
        var builder = TranscribeClient.builder()
                .region(Region.of(region));

        if (transcribeEndpoint != null && !transcribeEndpoint.isEmpty()) {
            builder.endpointOverride(URI.create(transcribeEndpoint));
            builder.credentialsProvider(localStackCredentials());
        }

        return builder.build();
    }

    @Bean
    public CognitoIdentityProviderClient cognitoClient() {
        var builder = CognitoIdentityProviderClient.builder()
                .region(Region.of(region));

        if (cognitoEndpoint != null && !cognitoEndpoint.isEmpty()) {
            builder.endpointOverride(URI.create(cognitoEndpoint));
            builder.credentialsProvider(localStackCredentials());
        }

        return builder.build();
    }

    private AwsCredentialsProvider localStackCredentials() {
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create("test", "test"));
    }
}
