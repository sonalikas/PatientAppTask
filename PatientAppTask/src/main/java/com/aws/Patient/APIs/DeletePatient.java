package com.aws.Patient.APIs;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.Patient.model.Patient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeletePatient {

	public APIGatewayProxyResponseEvent deletePatient(APIGatewayProxyRequestEvent request, Context context)
			throws JsonMappingException, JsonProcessingException {
					String id = request.getPathParameters().get("id");

					AmazonDynamoDB patients = AmazonDynamoDBClientBuilder.defaultClient();
		        DynamoDB dynamoDB = new DynamoDB(patients);
		        Table table = dynamoDB.getTable(System.getenv("PATIENT_TABLE"));
		        ObjectMapper objectMapper = new ObjectMapper();
		      Patient patient = objectMapper.readValue(request.getBody(), Patient.class);

		        int getid = Integer.parseUnsignedInt(id);
		        try {
					 if( getid == patient.PatientId) {

	  DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
			  .withPrimaryKey(id, patient.PatientId);
	            System.out.println(" deleting in process...");
	            table.deleteItem(deleteItemSpec);
	            System.out.println("DeleteItem succeeded");
	        }else {
					 }
					 }
	        catch (Exception e) {
	            System.err.println("Unable to delete item: " );
	            System.err.println(e.getMessage());
	        }
				 return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("deleted seccesfully");
	    }
}