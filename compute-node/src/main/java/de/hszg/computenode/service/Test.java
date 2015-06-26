package de.hszg.computenode.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Vector;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.Bigquery.Jobs.Insert;
import com.google.api.services.bigquery.model.GetQueryResultsResponse;
import com.google.api.services.bigquery.model.Job;
import com.google.api.services.bigquery.model.JobConfiguration;
import com.google.api.services.bigquery.model.JobConfigurationQuery;
import com.google.api.services.bigquery.model.JobReference;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableRow;

import de.hszg.computenode.bigquery.BigQueryAuthenticator;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test= new Test();
		Vector<String> macs = MacHelper.getInstance().getMacs("1010101010");
		String query = test.createQuery(macs, "research");
		BigQueryAuthenticator bigQueryAuthenticator = new BigQueryAuthenticator();
		Bigquery bigquery = null;
		try {
			bigquery = bigQueryAuthenticator.getBigquery();
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			test.executeQuery(bigquery, query);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String createQuery(Vector<String> macs, String customerProject){
		String query = "SELECT COUNT(ClientMacAddr) AS macCount"+
						"FORM " + BigQueryAuthenticator.PROJECT_ID + ":" + customerProject + ".observationsperstore"+
						"WHERE ";
		int macsSize = macs.size();
		for(int i = 0; i < macs.size(); i++){
			query = query.concat("RIGHT(ClientMacAddr, " + macs.get(i).length() +") == " + macs.get(i));
			macsSize--;
			if(macsSize > 1){
				query = query.concat(" OR ");
			}
		}
		query = query.concat(" GROUP BY ClientMacAddr");
		return query;
	}

	private void executeQuery(Bigquery bigquery, String query) throws IOException {
		Job job = new Job();
		JobConfiguration config = new JobConfiguration();
		JobConfigurationQuery queryConfig = new JobConfigurationQuery();
		config.setQuery(queryConfig);

	  job.setConfiguration(config);
	  queryConfig.setQuery(query);
	
	  Insert insert = bigquery.jobs().insert(BigQueryAuthenticator.PROJECT_ID, job);
	  insert.setProjectId(BigQueryAuthenticator.PROJECT_ID);
	  Job jobDone = waitForJob(bigquery, BigQueryAuthenticator.PROJECT_ID, insert.execute().getJobReference());
	  
	  GetQueryResultsResponse queryResultsResponse = bigquery.jobs().getQueryResults(BigQueryAuthenticator.PROJECT_ID, jobDone.getJobReference().getJobId()).execute();
	  
	  List<TableRow> rows = queryResultsResponse.getRows();
	  for(TableRow row : rows){
		  for(TableCell cell : row.getF()){
			  System.out.print(cell.getV());
		  }
		  System.out.println();
	  }
}
	
	private static Job waitForJob(Bigquery bigquery, String projectId, JobReference jobRef)
	      throws IOException {
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// ignore
			}
			Job pollJob = bigquery.jobs().get(projectId, jobRef.getJobId()).execute();
//			println("Waiting on job %s ... Current status: %s", jobRef.getJobId(),
//					pollJob.getStatus().getState());
			if (!isJobRunning(pollJob)) {
				return pollJob;
			}
		}
	}
	
	private static boolean isJobRunning(Job job) {
//println("job status: " + job.getStatus().getState());
		return job.getStatus().getState().equals("RUNNING") ||
				job.getStatus().getState().equals("PENDING");
	}
	
}
