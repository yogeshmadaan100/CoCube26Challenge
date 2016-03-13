package com.cube26.cube26.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yogeshmadaan on 13/03/16.
 */
public class PaymentGateway {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("branding")
    @Expose
    private String branding;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("currencies")
    @Expose
    private String currencies;
    @SerializedName("setup_fee")
    @Expose
    private String setupFee;
    @SerializedName("transaction_fees")
    @Expose
    private String transactionFees;
    @SerializedName("how_to_document")
    @Expose
    private String howToDocument;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The branding
     */
    public String getBranding() {
        return branding;
    }

    /**
     *
     * @param branding
     * The branding
     */
    public void setBranding(String branding) {
        this.branding = branding;
    }

    /**
     *
     * @return
     * The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The currencies
     */
    public String getCurrencies() {
        return currencies;
    }

    /**
     *
     * @param currencies
     * The currencies
     */
    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    /**
     *
     * @return
     * The setupFee
     */
    public String getSetupFee() {
        return setupFee;
    }

    /**
     *
     * @param setupFee
     * The setup_fee
     */
    public void setSetupFee(String setupFee) {
        this.setupFee = setupFee;
    }

    /**
     *
     * @return
     * The transactionFees
     */
    public String getTransactionFees() {
        return transactionFees;
    }

    /**
     *
     * @param transactionFees
     * The transaction_fees
     */
    public void setTransactionFees(String transactionFees) {
        this.transactionFees = transactionFees;
    }

    /**
     *
     * @return
     * The howToDocument
     */
    public String getHowToDocument() {
        return howToDocument;
    }

    /**
     *
     * @param howToDocument
     * The how_to_document
     */
    public void setHowToDocument(String howToDocument) {
        this.howToDocument = howToDocument;
    }

}