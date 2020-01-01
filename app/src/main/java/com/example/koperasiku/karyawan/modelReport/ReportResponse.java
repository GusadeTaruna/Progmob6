package com.example.koperasiku.karyawan.modelReport;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ReportResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("error")
	private boolean error;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"ReportResponse{" + 
			"data = '" + data + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}