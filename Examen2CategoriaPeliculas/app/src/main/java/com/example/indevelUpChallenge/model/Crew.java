package com.example.indevelUpChallenge.model;


import com.google.gson.annotations.SerializedName;

public class Crew {

    @SerializedName("job")
    private String job;
    @SerializedName("name")
    private String jobName;

    public Crew() {
    }

    public Crew(String job, String jobName) {
        this.job = job;
        this.jobName = jobName;
    }



    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }


}
