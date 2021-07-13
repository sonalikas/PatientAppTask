package com.aws.Patient.APIs;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.Patient.model.Patient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetPatientList {

	ObjectMapper objectMapper = new ObjectMapper();

	
	public APIGatewayProxyResponseEvent getPatient(APIGatewayProxyRequestEvent request)
			throws JsonProcessingException, JsonProcessingException {

		AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
		ScanResult scanResult = dynamoDB.scan(new ScanRequest().withTableName(System.getenv("PATIENT_TABLE")));
	List<Patient> patient	= scanResult
			.getItems().stream().map(item ->new Patient(Integer.parseInt(item.get("PatientId").getN()),
					item.get("PatientName").getS(),
					item.get("PatientStatus").getS(),
					item.get("PatientDescription").getS()))
					.collect(Collectors.toList());
			
	String jsonOutput = objectMapper.writeValueAsString(patient);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonOutput);

	}
}
