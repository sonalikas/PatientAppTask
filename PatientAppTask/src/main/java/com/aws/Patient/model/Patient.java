package com.aws.Patient.model;


public class Patient {

	public int PatientId;
	public String PatientName;
	public String PatientStatus;
	public String PatientDescription;
	
	public Patient() {
		
	}
	public Patient( int PatientId, String PatientName,String PatientStatus , String PatientDescription ) {
		 this.PatientId = PatientId;
		 this.PatientName =PatientName;
		 this.PatientStatus =PatientStatus;
		 this.PatientDescription =PatientDescription;
	}

}

