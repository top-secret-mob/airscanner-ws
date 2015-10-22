package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * Response from GCM server
 */
public class GcmResponse {
    private long multicast_id;
    private long success;
    private long failure;
    private long canonical_ids;
    private GcmResult[] results;

    public GcmResponse() {
    }

    public GcmResponse(long multicast_id, long success, long failure, long canonical_ids, GcmResult[] results) {
        this.multicast_id = multicast_id;
        this.success = success;
        this.failure = failure;
        this.canonical_ids = canonical_ids;
        this.results = results;
    }

    @JsonProperty("multicast_id")
    public long getMulticastId() {
        return multicast_id;
    }

    public void setMulticastId(long multicast_id) {
        this.multicast_id = multicast_id;
    }

    @JsonProperty("success")
    public long getSuccess() {
        return success;
    }

    public void setSuccess(long success) {
        this.success = success;
    }

    @JsonProperty("failure")
    public long getFailure() {
        return failure;
    }

    public void setFailure(long failure) {
        this.failure = failure;
    }

    @JsonProperty("canonical_ids")
    public long getCanonicalIds() {
        return canonical_ids;
    }

    public void setCanonicalIds(long canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    @JsonProperty("results")
    public GcmResult[] getResults() {
        return results;
    }

    public void setResults(GcmResult[] result) {
        this.results = result;
    }

    @Override
    public String toString() {
        return "GcmResponse{" +
                "multicast_id=" + multicast_id +
                ", success=" + success +
                ", failure=" + failure +
                ", canonical_ids=" + canonical_ids +
                ", results=" + Arrays.toString(results) +
                '}';
    }
}
