package com.aws.Patient.APIs;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.Patient.model.Patient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddPatient {

	ObjectMapper objectMapper = new ObjectMapper();

	public APIGatewayProxyResponseEvent addPatient(APIGatewayProxyRequestEvent request)
			throws JsonMappingException, JsonProcessingException {
		Patient patient = objectMapper.readValue(request.getBody(), Patient.class);

		DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
		Table table = dynamoDB.getTable(System.getenv("PATIENT_TABLE"));
		Item item = new Item().withPrimaryKey("PatientId", patient.PatientId)
				.withString("PatientName", patient.PatientName).withString("PatientStatus", patient.PatientStatus)
				.withString("PatientDescription", patient.PatientDescription);
		table.putItem(item);
		return new APIGatewayProxyResponseEvent().withStatusCode(200)
				.withBody("patient Added Succsesfully");

	}
}
