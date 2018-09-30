package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Hi");
		
		
		 //String clientRegion = "*** Client region ***";
	        String bucketName = "rakqesh";
	        String key = "AWS/100 Sales Records.csv";

	        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
	        try {
	            AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
	            
	            // Get an object and print its contents.
	            System.out.println("Downloading an object");
	            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
	            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
	            System.out.println("Content: ");
	            
	            displayTextInputStream(fullObject.getObjectContent());
	        } 
	            catch(AmazonServiceException e) {
	                // The call was transmitted successfully, but Amazon S3 couldn't process 
	                // it, so it returned an error response.
	                e.printStackTrace();
	                e.getErrorMessage();
	            }
	            catch(SdkClientException e) {
	                // Amazon S3 couldn't be contacted for a response, or the client
	                // couldn't parse the response from Amazon S3.
	                e.printStackTrace();
	            }
	            finally {
	                // To ensure that the network connection doesn't remain open, close any open input streams.
	                if(fullObject != null) {
	                    fullObject.close();
	                }
	                if(objectPortion != null) {
	                    objectPortion.close();
	                }
	                if(headerOverrideObject != null) {
	                    headerOverrideObject.close();
	                }
	            }
	        //System.exit(0);
	        
	}
	
	private static void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            String[] records = line.split(",");
            System.out.println("rec: "+records[1]);
        }
//      System.out.println();
   }
	
}
