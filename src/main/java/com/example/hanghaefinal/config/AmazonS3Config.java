package com.example.hanghaefinal.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class AmazonS3Config {

//    @Value("${cloud.aws.credentials.access-key}")
    @Value("${{secrets.AWS_ACCESS_KEY_ID}}")
    private String accessKey;

    @Value("${{ secrets.AWS_SECRET_ACCESS_KEY }}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3Client amazonS3Client() {
        System.out.println("-----------s3-----");
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        System.out.println("-----------s3-----"+accessKey+secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}
