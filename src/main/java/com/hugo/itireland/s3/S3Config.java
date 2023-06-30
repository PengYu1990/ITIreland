package com.hugo.itireland.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.s3.mock}")
    private boolean mock;

    @Bean
    public S3Client s3Client() {
//        System.out.println("=============================== S3 ===============================");
//        System.out.println("access key"+accessKeyId);
//        System.out.println("secretAccessKey"+secretAccessKey);
//        System.out.println("==================================================================");
        if (mock) {
            return new FakeS3();
        }
        return S3Client.builder()
                .credentialsProvider(new AwsCredentialsProvider() {
                    @Override
                    public AwsCredentials resolveCredentials() {
                        return AwsBasicCredentials.create(accessKeyId, secretAccessKey);
                    }
                })
                .region(Region.of(awsRegion))
                .build();
    }

}
